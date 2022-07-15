package hello.firstBoard.controller;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.service.login.LoginService;
import hello.firstBoard.validator.MemberLoginValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberLoginValidator memberLoginValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberLoginValidator);
    }

    @GetMapping("/login")
    public String loginController() {
        // 로그인 세션 있으면 리다이렉트

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginAttemptController(@Validated @ModelAttribute MemberLoginForm memberLoginForm,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {
        log.info("Post /login request");

        if (bindingResult.hasErrors()) {
            return "bindingResult.hasErrors!!!!";
        }

        Member loginMember
                = loginService.findByUserId(memberLoginForm.getUserId());

        // 로그인 성공 검사
        // 1. 해당 아이디 멤버가 있는지 검사
        if (loginMember == null) {
            // 아이디 잘못 입력
            return "null";
        }

        // 2. password 일치 검사
        if (loginMember.getUserPassword()
                != memberLoginForm.getUserPassword()) {
            return "not match password";
        }

        // 로그인 성공 후 로직
        redirectAttributes.addAttribute("userId", loginMember.getUserId());
        return "login OK!!";
    }

}
