package week03.gls_tracking_system;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class PackageDAO {
        private final EntityManagerFactory myEMF = HibernateConfig.getEntityManagerFactoryConfig();

        public void create(Package pack) {
            // This method should create a new student and persist it to the database.
            try(EntityManager em = myEMF.createEntityManager()) {
                em.getTransaction().begin();
                em.persist(pack);
                em.getTransaction().commit();
            }
        }

        public Package findPackage(String number) {
            // This method should read a student from the database using the student's id.
            try(EntityManager em = myEMF.createEntityManager()) {
                TypedQuery<Package> query = em.createQuery("select distinct p from Package p where trackingNumber = :tracking", Package.class);
                query.setParameter("tracking", number);
                return query.getSingleResult();
            }
        }

        public Package update(Package updStd) {
            // This method should update an existing student in the database.
            try(EntityManager em = myEMF.createEntityManager()) {
                em.getTransaction().begin();
                Package unitedPack = em.merge(updStd);
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
            try(EntityManager em = myEMF.createEntityManager()) {
                em.getTransaction().begin();
                Package pack = findPackage(number);
                if (pack != null) {
                    em.remove(pack);
                }
                em.getTransaction().commit();
            }
        }

        public List<Package> listAllPackages() {
            // This method should retrieve all students from the database and return them as a list. Use a TypedQuery to retrieve all students.
            try(EntityManager em = myEMF.createEntityManager()) {
                em.getTransaction().begin();
                @SuppressWarnings("unchecked")
                List<Package> found = (List<Package>) em.createQuery("select distinct c from Package c").getResultList();
                em.getTransaction().commit();
                return found;
            }
        }
}
