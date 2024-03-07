package week07.wed_thur.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue
    private Long id;
    @ManyToOne
    public Hotel hotelid;
    private Integer number;
    private double price;

    @Override
    public Long getID() {
        return id;
    }
}
