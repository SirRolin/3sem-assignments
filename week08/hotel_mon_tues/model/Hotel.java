package week08.hotel_mon_tues.model;

import jakarta.persistence.*;
import lombok.*;
import myLibrary.DAO.SuperEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Hotel implements SuperEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotelid", fetch = FetchType.EAGER)
    private List<Room> rooms = new ArrayList<>();
    @Override
    public Long getID() {
        return id;
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Hotel addRoom(Room room){
        rooms.add(room);
        room.setHotelid(this);
        return this;
    }
    public Hotel addRoom(int number, double price){
        Room room = new Room(null, this, number, price);
        return addRoom(room);
    }
}
