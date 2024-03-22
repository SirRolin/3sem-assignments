package week09.JWT_Exercise.DTO;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserDTO {
    private String username;
    private Set<String> roles = new HashSet<>();
    public UserDTO(User user){
        username = user.getUsername();
        roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
    }
}
