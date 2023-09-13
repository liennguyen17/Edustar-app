package com.example.ttcn2etest.email;

import com.example.ttcn2etest.constant.ErrorCodeDefs;
import com.example.ttcn2etest.exception.JwtTokenInvalid;
import com.example.ttcn2etest.model.etity.Role;
import com.example.ttcn2etest.model.etity.User;
import com.example.ttcn2etest.repository.role.RoleRepository;
import com.example.ttcn2etest.repository.user.UserRepository;
import com.example.ttcn2etest.request.auth.RegisterRequest;
import com.example.ttcn2etest.service.auth.JwtTokenProvider;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class RegisterAuthenService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmailService emailService;
    @Value("${jwt.jwtSecret}")
    private String jwtSecret;
    @Value("${jwt.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public RegisterAuthenService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void userRegisterAuthen(RegisterRequest signUpRequest){
        if (userRepository.existsAllByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Tên tài khoản đã tồn tại!");
        }
        if (userRepository.existsAllByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trong hệ thống!");
        }
        Role customerRole = roleRepository.findByRoleId("CUSTOMER");
        if(customerRole == null){
            customerRole = new Role();
            customerRole.setRoleId("CUSTOMER");
            customerRole = roleRepository.save(customerRole);
        }
        User user = User.builder()
                .name(signUpRequest.getName())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .isSuperAdmin(false)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .role(customerRole)
                .isVerified(false)
                .build();

        userRepository.saveAndFlush(user);

        String verificationToken = generateVerificationToken(user);

        String emailContent = "Email <"+signUpRequest.getEmail()+"> đã đăng ký tài khoản tại EduStar, \nClick link để xác thực tài khoản đăng ký : "+signUpRequest.getLink()+"?token=" + verificationToken;

        emailService.sendEmail(user.getEmail(), "Xác thực tài khoản!", emailContent);
    }

    private String generateVerificationToken(User user){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(user.getId().toString())
//                .claim("username", user.getUsername())
//                .claim("email", user.getEmail())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

//        return verificationToken;
    }

    public boolean verifyUser(String token) {
//        if (validateJwtToken(token)) {
            User user = decodeToken(token);
            if (user != null) {
                if (!user.isVerified()) {
                    user.setVerified(true);
                    userRepository.saveAndFlush(user);
                    return true;
                }
            }
            return false;
        }
//        return false;
//    }



    public User decodeToken(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            Long userId = Long.parseLong(claims.getSubject());
            Optional<User> userOptional = userRepository.findById(userId);
            return userOptional.orElse(null);
        }catch (Exception e){
            return null;
        }
    }

    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}");
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        throw new JwtTokenInvalid(ErrorCodeDefs.getErrMsg(ErrorCodeDefs.TOKEN_INVALID));
    }
}
