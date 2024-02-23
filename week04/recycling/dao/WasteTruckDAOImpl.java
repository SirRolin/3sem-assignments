package week04.recycling.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import week04.recycling.config.HibernateConfig;
import week04.recycling.model.Driver;
import week04.recycling.model.WasteTruck;

import java.util.List;

public class WasteTruckDAOImpl implements IWasteTruckDAO {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    @Override
    public int saveWasteTruck(String brand, String registrationNumber, int capacity) {
        try(EntityManager em = emf.createEntityManager()){
            WasteTruck wt = new WasteTruck(brand, capacity, registrationNumber);
            em.getTransaction().begin();
            em.persist(wt);
            em.getTransaction().commit();
            return wt.getId();
        }
    }

    @Override
    public WasteTruck getWasteTruckById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(WasteTruck.class, id);
        }
    }

    @Override
    public void setWasteTruckAvailable(WasteTruck wasteTruck, boolean available) {
        wasteTruck.set_available(available);
        updateObject(wasteTruck);
    }

    @Override
    public void deleteWasteTruck(int id) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            WasteTruck wt = em.find(WasteTruck.class, id);
            if(wt != null){
                em.remove(wt);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public void addDriverToWasteTruck(WasteTruck wasteTruck, Driver driver) {
        if(wasteTruck.is_available()){
            driver.setTruck(wasteTruck);
            wasteTruck.set_available(false);
            try(EntityManager em = emf.createEntityManager()) {
                updateObject(em, wasteTruck);
                updateObject(em, driver);
            }
        }
    }

    @Override
    public void removeDriverFromWasteTruck(WasteTruck wasteTruck, String id) {
        try(EntityManager em = emf.createEntityManager()) {
            if (!wasteTruck.is_available()) {
                Driver driver = em.find(Driver.class, id);
                if (driver != null && driver.getTruck().equals(wasteTruck)){
                    driver.setTruck(null);
                    wasteTruck.set_available(true);
                    updateObject(em, wasteTruck);
                }
            }
        }
    }

    @Override
    public List<WasteTruck> getAllAvailableTrucks() {
        try(EntityManager em = emf.createEntityManager()) {
            return em.createQuery("select wt from truck wt where wt.is_available = true", WasteTruck.class).getResultList();
        }
    }

    private static void updateObject(Object object) {
        try(EntityManager em = emf.createEntityManager()) {
            updateObject(em, object);
        }
    }

    private static void updateObject(EntityManager em, Object object) {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }
    
}
