package week04.populate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Teacher.class)
    @JoinTable(name = "sem_assigned_teachers")
    private Set<Teacher> teachers;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Student.class, mappedBy = "semester")
    private Set<Student> students;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Semester other)
            return Objects.equals(other.getId(), getId()) &&
                    Objects.equals(other.getName(), this.getName()) &&
                    Objects.equals(other.getDescription(), getDescription());
        return false;
    }
    @Override
    public String toString(){
        return getId() + ", " + getName() + " - " + getDescription();
    }
}
