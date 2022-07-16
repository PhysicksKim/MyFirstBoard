package hello.firstBoard.controller;

import hello.firstBoard.domain.member.Member;
import hello.firstBoard.domain.signUp.MemberSignupForm;
import hello.firstBoard.repository.MemberRepository;
import hello.firstBoard.validator.MemberSignupValidator;
import hello.firstBoard.web.session.LoginSessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/*")
    @ResponseBody
    public String badUrl(){
        return "access bad url";
    }

    @GetMapping("/")
    public String homeController(
            @SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false)
            Member loginMember,
            Model model) {

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

}
