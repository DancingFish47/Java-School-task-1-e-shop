package com.rychkov.eshop.dtos;

import lombok.Data;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Data
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
    @Constraint(validatedBy = {UserDtoValidator.class})
    public @interface UserDtoValidate {
        String message() default "Incorrect date!";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

    }


    //TODO ask why its not working
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
