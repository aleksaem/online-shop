package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
public class User {
    private String login;
    private String email;
    private String password;
    private String sole;
}
