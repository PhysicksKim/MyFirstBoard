package hello.firstBoard.validator;

import hello.firstBoard.domain.member.MemberLoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class MemberLoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("login validator ; supports()");
        return MemberLoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        log.debug("login validator ; validate()");
        MemberLoginForm memberSignupForm = (MemberLoginForm) target;

        log.debug("MemberLoginForm validate()");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required", "디폴트 메세지, 필수 값입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "required", "디폴트 메세지, 필수 값입니다");

        errors.getAllErrors().stream().forEach(objectError -> log.debug(objectError.toString()));

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
