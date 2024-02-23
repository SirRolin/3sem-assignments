package week04.recycling.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import week04.recycling.config.HibernateConfig;
import week04.recycling.model.Driver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DriverDAOImpl implements IDriverDAO {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    @Override
    public String saveDriver(String name, String surname, BigDecimal salary) {
        try(EntityManager em = emf.createEntityManager()){
            Driver d = new Driver(name, surname, salary);
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
            return d.getId();
        }
    }

    @Override
    public Driver getDriverById(String id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Driver.class, id);
        }
    }

    @Override
    public Driver updateDriver(Driver driver) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Driver d = em.merge(driver);
            em.getTransaction().commit();
            return d;
        }
    }

    @Override
    public void deleteDriver(String id) {
        try(EntityManager em = emf.createEntityManager()){
            em.remove(id);
        }
    }

    @Override
    public List<Driver> findAllDriversEmployedAtTheSameYear(String year) {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Driver> tq = em.createQuery("select d from Driver d where year(d.employment_date) = :year", Driver.class);
            tq.setParameter("year", year);
            return tq.getResultList();
        }
    }

    @Override
    public List<Driver> fetchAllDriversWithSalaryGreaterThan10000() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("select d from Driver d where d.salary > 10000", Driver.class).getResultList();
        }
    }

    @Override
    public double fetchHighestSalary() {
        try(EntityManager em = emf.createEntityManager()){
            return (double) em.createQuery("select d.salary from Driver d order by d.salary limit 1").getFirstResult();
        }
    }

    @Override
    public List<String> fetchFirstNameOfAllDrivers() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("select d.name from Driver d").getResultList();
        }
    }

    @Override
    public long calculateNumberOfDrivers() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("select count(d) from Driver d").getFirstResult();
        }
    }

    @Override
    public Driver fetchDriverWithHighestSalary() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Driver> tq = em.createQuery("select d from driver d order by d.salary desc limit 1", Driver.class);
            if(tq.getFirstResult()>=0){
                return tq.getSingleResult();
            }
            return null;
        }
    }
}
