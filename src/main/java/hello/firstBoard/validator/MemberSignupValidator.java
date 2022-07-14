package hello.firstBoard.validator;

import hello.firstBoard.domain.signUp.MemberSignupForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.Validation;

@Component
public class MemberSignupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberSignupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberSignupForm memberSignupForm = (MemberSignupForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "required");


        // UserId       VARCHAR(20)
        // UserPassword VARCHAR(30)
        // Name         VARCHAR(30)
        // Age          TINYINT

        // UserId Validation
        // 1. character length max 20
        if (memberSignupForm.getUserId() == null || memberSignupForm.getUserId().length() > 20) {
            errors.rejectValue("userId","range", new Object[]{0,20}, null);
        }

        // UserPassword Validation
        // 1. character length max 30
        if (memberSignupForm.getUserPassword() == null || memberSignupForm.getUserPassword().length() > 30) {
            errors.rejectValue("userPassword", "range", new Object[]{0, 30}, null);
        }

        // name Validation
        // 1. character length max 30
        if (memberSignupForm.getName() == null || memberSignupForm.getName().length() > 30) {
            errors.rejectValue("name", "range", new Object[]{0, 30}, null);
        }

        // age Validation
        // 1. age range (1 ~ 200)
        if (memberSignupForm.getAge() > 200
                || memberSignupForm.getAge() < 1) {
            errors.rejectValue("age", "range", new Object[]{1, 200}, null);
        }

    }
}
