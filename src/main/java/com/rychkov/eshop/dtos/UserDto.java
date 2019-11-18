package com.rychkov.eshop.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.validator.ValidateWith;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Getter
@Setter
@UserDto.UserDtoValidate
public class UserDto {
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String birthdate;

    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = { UserDtoValidator.class })
    public @interface UserDtoValidate {
        String message() default "Incorrect date!";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }


//TODO ASK WHY ITS NOT WORKING
    public static class UserDtoValidator implements ConstraintValidator<UserDtoValidate, UserDto> {
        @Override
        public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
//            try {
//                Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(userDto.getBirthdate());
//                Date date = new Date();
//                return date1.before(date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            return false;
        }
    }
}
