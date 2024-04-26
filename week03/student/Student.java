package week03.student;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "students")
@Entity
public class Student{
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private int age;

    @PrePersist
    @PreUpdate
    private void validateEmail() throws ValidationException {
        if(getEmail() == null || getEmail().isEmpty()){
            throw new ValidationException("Email's Empty");
        } else if (!getEmail().contains("@")) {
            throw new ValidationException("Email doesn't contain @");
        }
    }

}
