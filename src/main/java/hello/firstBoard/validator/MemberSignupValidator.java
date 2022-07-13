package hello.firstBoard.validator;

import hello.firstBoard.domain.signUp.MemberSignupForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.Validation;

public class MemberSignupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberSignupForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberSignupForm memberSignupForm = (MemberSignupForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "required");

    }
}
