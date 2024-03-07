package week07.wed_thur.DAO.concrete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import week07.wed_thur.DAO.abstracts.DAO;
import week07.wed_thur.model.Hotel;
import week07.wed_thur.model.Room;

import java.util.List;

public class HotelDAO extends DAO<Hotel, Long> {
    public List<Room> getAllRoomsInHotel(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Room> tq = em.createQuery("select r from Room r where hotelid = :hotelid", Room.class);
            tq.setParameter("hotelid", id);
            return tq.getResultList();
        }
    }
}
