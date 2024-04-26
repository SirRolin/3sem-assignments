package week04.recycling.model;

import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "driver")
public class Driver {
    @Id
    @Size(max = 14)
    private String id;
    @Temporal(TemporalType.DATE)
    private LocalDate employment_date;
    @Size(max = 255)
    private String name;
    @Digits(integer = 38, fraction = 2)
    private BigDecimal salary;
    private String surname;

    @ManyToOne(targetEntity = WasteTruck.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "truck_id")
    private WasteTruck truck;

    public Driver(String name, String surname, BigDecimal salary){
        setSalary(salary);
        setName(name);
        setSurname(surname);
        prePersist();
    }

    @PrePersist
    private void prePersist(){
        LocalDate today = LocalDate.now();
        setId(generateId(name, surname, today));
    }

    private static String generateId(String name, String surname, LocalDate today) throws ValidationException {
        if(name.isEmpty() || surname.isEmpty()){
            throw new ValidationException("name or surname is empty");
        }
        if(today==null){
            throw new ValidationException("date cannot be null");
        }
        Random rng = new Random();
        return String.format("%td%tm%ty-%s-%d%s",
                today,
                today,
                today,
                String.valueOf(name.toUpperCase().charAt(0)) + surname.toUpperCase().charAt(0),
                rng.nextInt(100,999),
                surname.toUpperCase().charAt(surname.length()-1));
    }
}
