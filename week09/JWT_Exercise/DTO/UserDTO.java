package week09.JWT_Exercise.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.User;

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
