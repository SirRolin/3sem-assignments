package week09.JWT_Exercise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import myLibrary.DAO.SuperEntity;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room implements SuperEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    public Hotel hotelid;
    private Integer number;
    private double price;

    @Override
    public Long getID() {
        return id;
    }

    @Override
    public String toString() {
        return "\tid: " + id + " of hotel: " + hotelid.getName() + " #" + number + " for " + price + "$";
    }
}
