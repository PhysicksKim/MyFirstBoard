package hello.firstBoard.repository;

import hello.firstBoard.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Slf4j
class MemberRepositoryPrototypeTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Member member = new Member("TestId","TestPassword", "TestName", 20L);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getId()).isNotNull();
        log.info(member.toString());
    }

    @Test
    void findByUserIdTest() {
        //given
        Member member = new Member("TestId", "TestPassword", "TestName", 20L);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByUserId(member.getUserId()).get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAllTest() {
        //given
        Member member1 = new Member("TestId1", "TestPassword1", "TestName1", 21L);
        Member member2 = new Member("TestId2", "TestPassword2", "TestName2", 22L);
        Member member3 = new Member("TestId3", "TestPassword3", "TestName3", 23L);

        List<Member> members = List.of(new Member[]{member1, member2, member3});

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> result = memberRepository.findALL();

        //then
        assertThat(result).contains(member1);
    }

    @Test
    void updatePasswordTest() {
        //given
        Member member = new Member("TestId", "TestPassword", "TestName", 20L);
        memberRepository.save(member);

        //when
        String newPassword = "newPassword";
        memberRepository.updatePassword(member, newPassword);

        //then
        Member findMember = memberRepository.findByUserId(member.getUserId()).get();
        assertThat(findMember.getUserPassword()).isEqualTo(newPassword);
    }

    @Test
    void deleteUserTest() {
        //given
        Member member = new Member("TestId", "TestPassword", "TestName", 20L);
        memberRepository.save(member);

        //when
        memberRepository.deleteUser(member);

        //then
        Optional<Member> opt_findMember = memberRepository.findByUserId(member.getUserId());
        assertThat(opt_findMember).isEmpty();
    }
}