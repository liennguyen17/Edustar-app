package com.example.ttcn2etest.request.user;


import com.example.ttcn2etest.validator.DateValidateAnnotation;
import com.example.ttcn2etest.validator.EmailAnnotation;
import com.example.ttcn2etest.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    @NotBlank(message = "Tên nguời dùng không được để trống!")
    private String name;
    @NotBlank(message = "Ngày sinh không được để trống!")
    @DateValidateAnnotation(message = "Định dạng ngày tháng phải là dd/mm/yyyy")
    private String dateOfBirth;
    @NotBlank(message = "Số điện thoại không được để trống!")
    @PhoneNumber
    private String phone;
    @NotBlank(message = "Email không được để trống!")
    @EmailAnnotation
    private String email;
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;

}
