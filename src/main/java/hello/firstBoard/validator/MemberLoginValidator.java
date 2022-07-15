package hello.firstBoard.validator;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.signUp.MemberSignupForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberLoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberLoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberLoginForm memberSignupForm = (MemberLoginForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "required");


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
    }
}
