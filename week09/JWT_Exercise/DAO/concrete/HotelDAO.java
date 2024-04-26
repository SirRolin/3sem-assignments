package week09.JWT_Exercise.DAO.concrete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import week09.JWT_Exercise.DAO.abstracts.DAO;
import week09.JWT_Exercise.model.Hotel;
import week09.JWT_Exercise.model.Room;

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
