package hello.firstBoard.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginSessionManager {

    public static final String LOGIN_SESSION_COOKIE = "loginSessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        // session manager에 로그인 정보 등록
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie loginCookie = new Cookie(LOGIN_SESSION_COOKIE, sessionId);
        response.addCookie(loginCookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie loginSessionCookie = findCookie(request, LOGIN_SESSION_COOKIE);
        if (loginSessionCookie == null) {
            return null;
        }
        return sessionStore.get(loginSessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie cookie = findCookie(request, LOGIN_SESSION_COOKIE);
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        // 만약 request에 쿠키가 하나도 없으면
        // 그냥 null을 리턴하도록 함
        if (request.getCookies() == null) {
            return null;
        }

        // 쿠키가 있으면 아래 로직따라
        // 쿠키 이름이 매칭되는 것을 찾고, 찾은 쿠키를 반환
        // 이때 찾은 쿠키 중에서 순서와 무관하게 아무 한 개만 반환함 (findAny)
        return Arrays.stream(request.getCookies())
                .filter( cookie -> cookie.getName() == cookieName)
                .findAny()
                .orElse(null);
    }

}
