package week09.JWT_Exercise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @Setter(AccessLevel.NONE)
    private Set<User> users;
    public Role addUser(User u){
        users.add(u);
        if(!u.getRoles().contains(this)){
            u.addRole(this);
        }
        return this;
    }
    public Role removeUser(User u){
        users.remove(u);
        if(u.getRoles().contains(this)){
            u.removeRole(this.getRole());
        }
        return this;
    }
}
