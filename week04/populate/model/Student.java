package week04.populate.model;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "pop_student")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    @NotNull
    private String firstName;
    @Column(nullable = false)
    @NotNull
    private String lastName;

    @ManyToOne
    private Semester semester;

    @Override
    public String toString() {
        return id + ": " + firstName + " " + lastName + (semester != null ? " sem: " + semester : "");
    }
}
