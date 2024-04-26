package week04.populate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.View;

@Getter
@Setter
@NoArgsConstructor
//@View(query = "CREATE VIEW student_info as SELECT * FROM pop_student")
@Entity(name = "student_info")
public class StudentInfo extends Student {
    @Column(name = "fullName")
    private String fullName(){
        return getFirstName() + " " + getLastName();
    };
    @Column(name = "thisSemesterName")
    private String thisSemesterName(){
        if(getSemester() == null)
            return "";
        return getSemester().getName();
    }
    @Column(name = "thisSemesterDescription")
    private String thisSemesterDescription(){
        if(getSemester() == null)
            return "";
        return getSemester().getDescription();
    }



}
