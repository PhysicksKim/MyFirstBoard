package hello.firstBoard.repository;

import hello.firstBoard.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // CRUD 구현

    // CREATE ; save member

    /**
     * 회원가입시 사용하는 멤버 저장용. 아이디 중복 여부도 검증.
     * @param member
     * @return 회원가입 성공시 Member 객체 반환. 실패시(아이디중복) null 반환
     */
    Member save(Member member);

    // READ ; find member
    Optional<Member> findByUserId(String userId);
    List<Member> findALL();

    // UPDATE ; change password
    boolean updatePassword(Member member, String userPassword);

    // DELETE ; delete user
    boolean deleteUser(Member member);

}
