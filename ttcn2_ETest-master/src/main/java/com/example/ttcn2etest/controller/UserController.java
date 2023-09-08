package com.example.ttcn2etest.controller;

import com.example.ttcn2etest.constant.DateTimeConstant;
import com.example.ttcn2etest.model.dto.UserDTO;
import com.example.ttcn2etest.model.etity.User;
import com.example.ttcn2etest.request.user.CreateUserRequest;
import com.example.ttcn2etest.request.user.FilterUserRequest;
import com.example.ttcn2etest.request.user.UpdateUserRequest;
import com.example.ttcn2etest.service.user.UserService;
import com.example.ttcn2etest.utils.MyUtils;
import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllUser() {
        try {
            List<UserDTO> response = userService.getAllUser();
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        UserDTO response = userService.getByIdUser(id);
        return buildItemResponse(response);
    }

    @PostMapping("")
    ResponseEntity<?> creatUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO response = userService.createUser(request);
        return buildItemResponse(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@Validated @RequestBody UpdateUserRequest request,
                                 @PathVariable("id") Long id) throws ParseException {
        UserDTO response = userService.updateUser(request, id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        UserDTO response = userService.deleteByIdUser(id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/delete/all")
    ResponseEntity<?> deleteAllId(@RequestBody List<Long> ids) {
        try {
            List<UserDTO> response = userService.deleteAllIdUser(ids);
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterUser(@Validated @RequestBody FilterUserRequest request) throws ParseException {
        Page<User> userPage = userService.filterUser(
                request,
                !Strings.isEmpty(request.getDateFrom()) ? MyUtils.convertDateFromString(request.getDateFrom(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateTo()) ? MyUtils.convertDateFromString(request.getDateTo(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateOfBirthFrom()) ? MyUtils.convertDateFromString(request.getDateOfBirthFrom(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateOfBirthTo()) ? MyUtils.convertDateFromString(request.getDateOfBirthTo(), DateTimeConstant.DATE_FORMAT) : null
        );
        List<UserDTO> userDTOS = userPage.getContent().stream().map(
                user -> modelMapper.map(user, UserDTO.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(userDTOS, userPage.getTotalElements());
    }
}
