package hello.firstBoard.domain.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginForm {

    private String userId;
    private String userPassword;
}
