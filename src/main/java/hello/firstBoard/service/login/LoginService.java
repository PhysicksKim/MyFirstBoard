package hello.firstBoard.service.login;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.repository.MemberRepositoryPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepositoryPrototype memberRepositoryPrototype;

    public Member login(MemberLoginForm memberLoginForm) {

        Member findMember
                = findByUserId(memberLoginForm.getUserId());

        // 로그인 성공 검사
        // 1. 해당 아이디 멤버가 있는지 검사
        if (findMember == null) {
            log.info("login error 1");
            // 아이디 잘못 입력
            return null;
        }

        // 2. password 일치 검사
        // 2-1. password가 null이면 String 이 null 인데
        // 이러면 nullpointerexception 나니까 null 검사 먼저 해야함
        if (memberLoginForm.getUserPassword() == null) {
            log.info("login error 2-1");
            return null;
        }
        // 2-2. password가 일치하지 않으면 return null
        if (!findMember.getUserPassword().equals(memberLoginForm.getUserPassword())) {
            log.info("login error 2-2");
            return null;
        }

        // 최종적으로 검사 통과하면 member 반환
        log.info("login passed");
        log.info("findMember = {}", findMember);
        return findMember;
    }

    public Member findByUserId(String userId) {
        Optional<Member> loginMember = memberRepositoryPrototype.findByUserId(userId);

        if (loginMember.isEmpty()) {
            return null;
        }

        return loginMember.get();
    }

}
