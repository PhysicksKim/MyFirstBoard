package hello.firstBoard.domain.member;

import hello.firstBoard.domain.login.MemberLoginForm;
import hello.firstBoard.domain.signUp.MemberSignupForm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
public class Member {

    //  ID  USERID  USERPASSWORD  NAME	AGE
    //  pk

    private Long id;
    private String userId;
    private String userPassword;
    private String name;
    private int age;

    public Member() {}

    public Member(MemberSignupForm memberSignupForm) {
        this(memberSignupForm.getUserId(), memberSignupForm.getUserPassword(), memberSignupForm.getName(), memberSignupForm.getAge());
    }

    public Member(String userId, String userPassword, String name, int age) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Member)) {
            log.info("-----{} is Not instanceof Member -----", o);
            return false;
        }

        Member argMember = (Member) o;

        return (argMember.getUserId().equals(this.getUserId()))
                && (argMember.getName().equals(this.getName()))
                && (argMember.getAge() == this.getAge());
    }

    @Override
    public int hashCode() {
        int userId_int = Integer.parseInt(userId);
        int name_int = Integer.parseInt(name);

        return userId_int * name_int * age;
    }
}
