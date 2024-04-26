package week04.gls_tracking_system;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Set;

import jakarta.validation.ValidationException;

@SuppressWarnings("DuplicatedCode")
public class PackageDAO {
    private final EntityManagerFactory myEMF = week04.hibernate.HibernateConfig.getEntityManagerFactoryConfig(true, Set.of(Person.class, Status.class));

    public void create(Person pack) {
        // This method should create a new student and persist it to the database.
        try (EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            try {
                _create(pack, em);
                em.getTransaction().commit();
            } catch (ValidationException e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    private void _create(Person pack, EntityManager em) throws ValidationException {
        em.persist(pack);
    }

    public Person findPackage(String number) {
        // This method should read a student from the database using the student's id.
        try (EntityManager em = myEMF.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select distinct p from Person p where trackingNumber = :tracking", Person.class);
            query.setParameter("tracking", number);
            return query.getSingleResult();
        }
    }

    public Person update(Person updStd) {
        // This method should update an existing student in the database.
        try (EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            Person unitedPack = em.merge(updStd);
            if (unitedPack != null) {
                em.getTransaction().commit();
                return unitedPack;
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    public void delete(String number) {
        // This method should delete a student from the database using the student's id.
        try (EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            Person pack = findPackage(number);
            if (pack != null) {
                em.remove(pack);
            }
            em.getTransaction().commit();
        }
    }

    public List<Person> listAllPackages() {
        // This method should retrieve all students from the database and return them as a list. Use a TypedQuery to retrieve all students.
        try (EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Person> found = em.createQuery("select distinct c from Person c", Person.class).getResultList();
            em.getTransaction().commit();
            return found;
        }
    }
}
