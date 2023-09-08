package com.example.ttcn2etest.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailAnnotation, String> {
    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@(.+)$";
//    "^[A-Za-z0-9+_.-]+@[^-][A-Za-z0-9.-]+\\.[A-Za-z]{2,}$\n";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(EmailAnnotation constraintAnnotation) {
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return true; // Allow null values
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
