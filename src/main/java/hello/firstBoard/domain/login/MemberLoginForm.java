package hello.firstBoard.domain.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginForm {

    private String userId;
    private String userPassword;
}
