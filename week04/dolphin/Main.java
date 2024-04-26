package week04.dolphin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello Dolphin!");

        EntityManagerFactory emf = week04.hibernate.HibernateConfig.getEntityManagerFactoryConfig(true, Set.of(Fee.class, Person.class, PersonDetail.class, Note.class));
        try(EntityManager em = emf.createEntityManager())
        {
            Person p1 = new Person("Hanzi");
            PersonDetail pd1 = new PersonDetail("Algade 3", 4300, "Holbæk", 45);
            p1.addPersonDetail(pd1);
            Fee f1 = new Fee(125, LocalDate.of(2023, 8, 25));
            Fee f2 = new Fee(150, LocalDate.of(2023, 7, 19));
            p1.addFee(f1);
            p1.addFee(f2);

            em.getTransaction().begin();
            em.persist(p1);
            em.getTransaction().commit();

            // US 1
            Person p2 = new Person("Mancy");
            PersonDetail pd2 = new PersonDetail("Algade 5", 4300, "Holbæk", 42);
            p1.addNote(p2, "Still owes me 20 Dollars.");
            p2.addNote(p1, "I owe them 20 Dollars.");

            em.getTransaction().begin();
            em.persist(p2);
            em.getTransaction().commit();

            // US 2



            // US 3
        }
    }
}