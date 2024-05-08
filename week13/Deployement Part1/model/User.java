package week13.ReactIII.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User implements ISecurityUser {
    @Id
    private String username;
    private String hashedPassword;
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "username")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role")
            },
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public Set<String> getRolesAsStrings() {
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    /**
     * Verify a hashed password
     *
     * @param pw
     * @return
     */
    @Override
    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, hashedPassword);
    }

    @Override
    public void addRole(Role role) {
        roles.add(role);
        if (!role.getUsers().contains(this))
            role.addUser(this);
    }

    @Override
    public void removeRole(String role) {
        Optional<Role> roleOptional = roles.stream().filter(x -> x.getRole().equals(role)).findFirst();
        if (roleOptional.isPresent()) {
            Role theRole = roleOptional.get();
            roles.remove(theRole);
            if (theRole.getUsers().contains(this))
                theRole.removeUser(this);
        }
    }
}
