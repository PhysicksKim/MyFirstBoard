package hello.firstBoard.domain.member;

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
    private Long age;

    public Member() {}

    public Member(String userId, String userPassword, String name, Long age) {
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
        int age_int = age.intValue();

        return userId_int * name_int * age_int;
    }
}
