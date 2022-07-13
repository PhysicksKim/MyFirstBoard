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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/")
    public String homeController() {
        return "home";
    }

    @GetMapping("/login")
    public String loginController() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String loginAttemptController() {
        log.info("Post /login request");

        return "Post /login request";
    }

}
