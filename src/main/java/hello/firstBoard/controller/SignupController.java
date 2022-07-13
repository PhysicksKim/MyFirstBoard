package hello.firstBoard.controller;

import hello.firstBoard.domain.member.Member;
import hello.firstBoard.domain.signUp.MemberSignupForm;
import hello.firstBoard.repository.MemberRepository;
import hello.firstBoard.validator.MemberSignupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignupController {

    private final MemberRepository memberRepository;
    private final MemberSignupValidator memberSignupValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {

        log.info("init binder {}", webDataBinder);
        webDataBinder.addValidators(memberSignupValidator);
    }


    @GetMapping("/signUp")
    public String signUpController() {
        log.info("Get /signUp request");

        return "signUp";
    }

    @PostMapping("/signUp")
    @ResponseBody
    public String singUpPostController(@Validated @ModelAttribute MemberSignupForm memberSignupForm,
                                       BindingResult bindingResult) {
        log.info("Post /singUp request");
        log.info("MemberSignupForm = {}", memberSignupForm);

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "signUp";
        }

        // 성공 로직
        Member member = new Member(memberSignupForm);
        memberRepository.save(member);
        return "OK";
    }
}
