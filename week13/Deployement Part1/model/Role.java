package week13.ReactIII.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private String role;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @Setter(AccessLevel.NONE)
    private Set<User> users = new HashSet<>();
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
