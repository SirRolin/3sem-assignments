package week08.hotel_mon_tues.DAO.concrete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import week08.hotel_mon_tues.DAO.abstracts.DAO;
import week08.hotel_mon_tues.model.Hotel;
import week08.hotel_mon_tues.model.Room;

import java.util.List;

public class HotelDAO extends DAO<Hotel, Long> {
    public List<Room> getAllRoomsInHotel(Hotel hotel) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Room> tq = em.createQuery("select r from Room r where hotelid = :hotelid", Room.class);
            tq.setParameter("hotelid", hotel);
            return tq.getResultList();
        }
    }
}
