package hello.firstBoard.controller;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.service.login.LoginService;
import hello.firstBoard.validator.MemberLoginValidator;
import hello.firstBoard.web.session.LoginSessionConst;
import hello.firstBoard.web.session.LoginSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MemberLoginValidator memberLoginValidator;

    @InitBinder
    public void initMemberLoginValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberLoginValidator);
    }

    @GetMapping("/login")
    public String loginController(
            @SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false)
            Member loginMember,
            HttpServletRequest request,
            Model model) {

        // 이미 로그인 한 상태인지 검증
        // ; 로그인 세션 있으면 로그인홈으로 이동
        if (loginMember != null) {
            model.addAttribute("member", loginMember);
            return "loginHome";
        }

        model.addAttribute("memberLoginForm", new MemberLoginForm());
        // 로그인 하지 않은 상태이므로 login 으로 이동
        return "login";
    }

    @PostMapping("/login")
    public String loginAttemptController(@Validated @ModelAttribute MemberLoginForm memberLoginForm,
                                         BindingResult bindingResult,
                                         HttpServletRequest request) {
        log.debug("Post /login request");
        log.debug("memberLoginForm = {}", memberLoginForm);



        if (bindingResult.hasErrors()) {
            log.debug("login binding error");
            return "/login";
        }

        Member loginMember = loginService.login(memberLoginForm);

        if (loginMember == null) {
            log.debug("loginMember == null");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login";
        }

        // 로그인 성공 후 로직
        HttpSession session = request.getSession();
        session.setAttribute(LoginSessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
