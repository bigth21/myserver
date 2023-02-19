package bigth.myserver.web;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserSignInRequestDTO {

    private String username;
    private String password;
}
