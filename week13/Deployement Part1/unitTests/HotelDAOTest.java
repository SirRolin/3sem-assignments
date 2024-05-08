package week13.ReactIII.unitTests;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import week13.ReactIII.DAO.concrete.HotelDAO;
import week13.ReactIII.DAO.concrete.RoomDAO;
import week13.ReactIII.config.gsonFactory;
import week13.ReactIII.config.hibernate;
import week13.ReactIII.model.Hotel;
import week13.ReactIII.model.Room;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelDAOTest {
    private final Gson gson = gsonFactory.getGson();
    private static HotelDAO hotelDAO;
    private static RoomDAO roomsDAO;
    private static final Hotel selectHotel = new Hotel("select", "created");
    private static final Hotel createHotel = new Hotel("create", "created");
    private static final Hotel updateHotel = new Hotel("update", "created");
    private static final Hotel deleteHotel = new Hotel("delete", "created");
    @BeforeAll
    static void setUpAll() {
        hibernate.isDevState = true;
        hotelDAO = new HotelDAO();
        roomsDAO = new RoomDAO();
        selectHotel.addRoom(24,2);
        hotelDAO.create(selectHotel);
        hotelDAO.create(updateHotel);
        hotelDAO.create(deleteHotel);
    }

    @Test
    @DisplayName("Get All Hotels")
    void getAll() {
        List<Hotel> hotels = hotelDAO.getAll();
        assertTrue(hotels.stream().anyMatch(x ->
                x.getID().equals(selectHotel.getID())
                && x.getName().equals(selectHotel.getName())
        ));
    }

    @Test
    @DisplayName("Get specific Hotel")
    void getById() {
        assertNotNull(hotelDAO.getById(selectHotel.getID()));
    }

    @Test
    @DisplayName("Persist Hotel")
    void create() {
        hotelDAO.create(createHotel);
        assertNotNull(hotelDAO.getById(createHotel.getID()));
    }

    @Test
    void update() {
        updateHotel.setAddress("updated");
        hotelDAO.update(updateHotel);
        Hotel hotel = hotelDAO.getById(updateHotel.getID());
        assertEquals(hotel.getAddress(), "updated");
    }

    @Test
    void delete() {
        deleteHotel.setAddress("deleted");
        hotelDAO.delete(deleteHotel);
        Hotel hotel = hotelDAO.getById(deleteHotel.getID());
        assertNull(hotel);
    }

    @Test
    void getAllRoomsInHotel() {
        List<Room> rooms = hotelDAO.getAllRoomsInHotel(selectHotel);
        assertTrue(rooms.stream().anyMatch(x -> x.getNumber().equals(24)));
    }
}