package hello.firstBoard.domain.signUp;

import lombok.Data;

@Data
public class MemberSignupForm {
    private String userId;
    private String userPassword;
    private String name;
    private int age;
}
