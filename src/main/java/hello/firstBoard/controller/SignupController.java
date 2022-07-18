package hello.firstBoard.controller;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.domain.member.MemberSignupForm;
import hello.firstBoard.repository.MemberRepository;
import hello.firstBoard.validator.MemberSignupValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        webDataBinder.addValidators(memberSignupValidator);
    }

    @GetMapping("/signUp")
    public String signUpController(Model model) {
        log.debug("Get /signUp request");
        model.addAttribute("memberSignupForm", new MemberSignupForm());
        return ViewPathConst.SIGNUP_PAGE;
    }

    @PostMapping("/signUp")
    public String singUpPostController(@Validated @ModelAttribute MemberSignupForm memberSignupForm,
                                       BindingResult bindingResult) {

        log.debug("Post /singUp request");
        log.debug("MemberSignupForm = {}", memberSignupForm);

        if (bindingResult.hasErrors()) {
            log.debug("errors = {}", bindingResult);
            return ViewPathConst.SIGNUP_PAGE;
        }

        // 성공 로직
        Member member = new Member(memberSignupForm);
        Member savedMember = memberRepository.save(member);

        if (savedMember == null) {
            bindingResult.rejectValue("userId", "notUniqueId");
            return ViewPathConst.SIGNUP_PAGE;
        }

        return ViewPathConst.LOGIN_PAGE;
    }
}
