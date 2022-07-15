package hello.firstBoard.service.login;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.member.Member;
import hello.firstBoard.repository.MemberRepositoryPrototype;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepositoryPrototype memberRepositoryPrototype;

    public Member findByUserId(String userId) {
        Optional<Member> loginMember = memberRepositoryPrototype.findByUserId(userId);

        if (loginMember.isEmpty()) {
            return null;
        }

        return loginMember.get();
    }

}
