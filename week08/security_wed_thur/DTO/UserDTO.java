package week08.security_wed_thur.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import week08.security_wed_thur.model.Role;
import week08.security_wed_thur.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private Set<String> roles = new HashSet<>();
    public UserDTO(User user){
        username = user.getUsername();
        password = user.getHashedPassword();
        roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
    }
}
