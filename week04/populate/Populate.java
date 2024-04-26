package week04.populate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static week04.populate.config.HibernateConfig.getEntityManagerFactoryConfig;

public class Populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = getEntityManagerFactoryConfig(); // statically imported above.

        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            // populate the database with data
            em.createNativeQuery("""
                    INSERT INTO semester (id, name, description)
                    VALUES
                        (1, '1Sem Dat', '1Sem Datamatik Class'),
                        (2, '2Sem Dat', '2Sem Datamatik Class'),
                        (3, '3Sem Dat', '3Sem Datamatik Class'),
                        (4, '4Sem Dat', '4Sem Datamatik Class'),
                        (5, '5Sem Dat', '5Sem Datamatik Class')""").executeUpdate();
            em.createNativeQuery("""
                    INSERT INTO pop_student (id, firstname, lastname, semester_id)
                    VALUES
                        (1, 'Gustav', 'Jakobsen', 2),
                        (2, 'Stanley', 'Ford', 4),
                        (3, 'Ottis', 'Andrin', 3),
                        (4, 'Gustav', 'Malis', 5),
                        (5, 'Magnus', 'Jakobsen', 3)""").executeUpdate();

            //// I really wanted this to be a view, but using a query couldn't recognise it.
            em.createNativeQuery("""
                    INSERT INTO student_info (id)
                    VALUES
                        (1),
                        (2),
                        (3),
                        (4),
                        (5)""").executeUpdate();
            em.createNativeQuery("""
                    INSERT INTO teacher (id, firstname, lastname)
                    VALUES
                        (1, 'Gustav', 'Jakobsen'),
                        (2, 'Stanley', 'Ford'),
                        (3, 'Ottis', 'Andrin'),
                        (4, 'Gustav', 'Malis'),
                        (5, 'Magnus', 'Jakobsen')""").executeUpdate();
            em.createNativeQuery("""
                    INSERT INTO sem_assigned_teachers (semesters_id, teachers_id)
                    VALUES
                        (1, 2),
                        (2, 2),
                        (3, 2),
                        (4, 2),
                        (5, 3)""").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
