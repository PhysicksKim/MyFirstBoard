package hello.firstBoard.controller;

import hello.firstBoard.domain.member.MemberLoginForm;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.service.login.LoginService;
import hello.firstBoard.validator.MemberLoginValidator;
import hello.firstBoard.web.session.LoginSessionConst;
import hello.firstBoard.consts.ViewPathConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 이미 로그인 한 상태인지 검증
        // ; 로그인 세션 있으면 로그인홈으로 이동
        if (loginMember != null) {
            model.addAttribute("member", loginMember);
            return "loginHome";
        }

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        stopWatch.stop();
        log.info("stopWatch -------- : {}ms ----------", stopWatch.getTotalTimeMillis());
        // 로그인 하지 않은 상태이므로 login 으로 이동
        return ViewPathConst.LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String loginAttemptController(@Validated @ModelAttribute MemberLoginForm memberLoginForm,
                                         BindingResult bindingResult,
                                         HttpServletRequest request) {
        log.debug("Post /login request");
        log.debug("memberLoginForm = {}", memberLoginForm);

        // 로그인 데이터를 받아올 때 바인딩 에러가 발생하면
        // 다시 로그인 화면으로 이동
        // (redirect 하면 에러 메세지가 전달안됨)
        if (bindingResult.hasErrors()) {
            log.debug("login binding error");
            return ViewPathConst.LOGIN_PAGE;
        }

        // 로그인 폼 데이터를 보냄
        // -> 아이디 비밀번호 일치해서 로그인 성공하면 해당하는 멤버가 반환됨
        // -> 실패하면 null 반환됨
        Member loginMember = loginService.login(memberLoginForm);

        if (loginMember == null) {
            log.debug("loginMember == null");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return ViewPathConst.LOGIN_PAGE;
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
