package com.example.ttcn2etest.model.etity;

import com.example.ttcn2etest.constant.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String password;
    private String phone;
    private String email;
    private boolean isVerified; // Trạng thái xác thực

    //    @Column(name = "password_no_encode", length = 100)
//    private String passwordNoEncode;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private String address;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "update_date")
    private Timestamp updateDate;
    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;
    @Size(max = 2000)
    private String avatar;

    @ManyToMany
    @JoinTable(name = "user_service", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Collection<Service> services;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (role != null) {
            role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermissionId())));
            authorities.add(new SimpleGrantedAuthority(role.getRoleId()));
        }
//        if (isSuperAdmin) {
//            authorities.add(new SimpleGrantedAuthority(RoleEnum.ADMIN.name()));
//        }
//        if (!isSuperAdmin && authorities.isEmpty()) {
//            authorities.add(new SimpleGrantedAuthority(RoleEnum.CUSTOMER.name()));
//        }
        return authorities;
    }

}
