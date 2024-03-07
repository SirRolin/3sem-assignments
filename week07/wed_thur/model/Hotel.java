package week07.wed_thur.model;

import jakarta.persistence.*;
import lombok.*;
import myLibrary.DAO.SuperEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel implements SuperEntity<Long> {
    @Id
    private Long id;
    private String name;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotelid")
    private List<Room> rooms;
    @Override
    public Long getID() {
        return id;
    }
}
