package hello.firstBoard.session;

import hello.firstBoard.domain.member.Member;
import hello.firstBoard.web.session.LoginSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
public class LoginSessionManagerTest {

    LoginSessionManager loginSessionManager = new LoginSessionManager();

    @Test
    void loginSessionTest() {

        // 1. 로그인 성공 후 세션 생성해서 클라이언트에게 줌
        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member("loginsession","loginsessionpassword","ses",10);
        loginSessionManager.createSession(member, response);

        // 2. 클라이언트가 세션을 갖고 서버에 로그인 요청을 함
        //  ; 클라이언트가 request에 세션 쿠키 담아서 보냄
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // 3. 서버는 클라이언트가 보낸 로그인 세션 쿠키를 받아서 유효한지 검사함
        Object sessionValue = loginSessionManager.getSession(request);
        assertThat(sessionValue).isEqualTo(member);

        // 4. 사용자의 로그아웃 요청등이 발생하면 세션을 만료시켜줌
        loginSessionManager.expire(request);
        Object expiredSession = loginSessionManager.getSession(request);
        assertThat(expiredSession).isNull();

    }
}
