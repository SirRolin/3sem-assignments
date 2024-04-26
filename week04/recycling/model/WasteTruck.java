package week04.recycling.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "truck")
@EqualsAndHashCode
public class WasteTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 255)
    private String brand;
    private Integer capacity;
    private boolean is_available = true;
    @Size(max = 255)
    private String registration_number;
    public WasteTruck(String brand, Integer capacity, String registration_number){
        setBrand(brand);
        setCapacity(capacity);
        setRegistration_number(registration_number);
    }
}
