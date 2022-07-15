package hello.firstBoard.domain.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginForm {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String userPassword;
}
