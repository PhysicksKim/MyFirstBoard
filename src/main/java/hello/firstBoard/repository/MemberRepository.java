package hello.firstBoard.repository;

import hello.firstBoard.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    // CRUD 구현

    // CREATE ; save member
    Member save(Member member);

    // READ ; find member
    Optional<Member> findByUserId(String userId);
    List<Member> findALL();

    // UPDATE ; change password
    boolean updatePassword(Member member, String userPassword);

    // DELETE ; delete user
    boolean deleteUser(Member member);

}
