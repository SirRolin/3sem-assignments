package week04.dolphin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static week04.hibernate.HibernateConfig.getEntityManagerFactoryConfig;

public class DolphinDAO {

    EntityManagerFactory emf;

    public DolphinDAO(boolean testing) {
        this.emf = getEntityManagerFactoryConfig(testing, Set.of(Fee.class, Person.class, PersonDetail.class, Note.class));
    }

    public DolphinDAO() {
        this.emf = getEntityManagerFactoryConfig(false, Set.of(Fee.class, Person.class, PersonDetail.class, Note.class));
    }

    public void create(Person p) {
        // This method should create a new student and persist it to the database.
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                _create(p, em);
                em.getTransaction().commit();
            } catch (ValidationException e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    private void _create(Person p, EntityManager em) throws ValidationException {
        em.persist(p);
    }

    public Person findPerson(String name) {
        // This method should read a student from the database using the student's id.
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> query = em.createQuery("select distinct p from Person p where p.name = :name", week04.dolphin.Person.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }
    }

    public Person update(Person updatedPerson) {
        // This method should update an existing student in the database.
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person unitedPerson = em.merge(updatedPerson);
            if (unitedPerson != null) {
                em.getTransaction().commit();
                return unitedPerson;
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    public void delete(String name) {
        // This method should delete a student from the database using the student's id.
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Person person = findPerson(name);
            if (person != null) {
                em.remove(person);
            }
            em.getTransaction().commit();
        }
    }

    public List<Person> listAllPeople() {
        // This method should retrieve all students from the database and return them as a list. Use a TypedQuery to retrieve all students.
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Person> found = em.createQuery("select distinct p from Person p", Person.class).getResultList();
            em.getTransaction().commit();
            return found;
        }
    }

    //// US 2
    public int getTotalFeesAmountByPerson(Person person){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Fee> q = em.createQuery("select f from Fee f where f.person = :id", Fee.class);
            q.setParameter("id", person);
            List<Fee> fees = q.getResultList();
            em.getTransaction().commit();
            return fees.stream().mapToInt(Fee::getAmount).sum();
        }
    }

    //// US 3
    public Set<Note> getAllNotesOnPerson(Person person){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Note> q = em.createQuery("select distinct n from Note n where n.notee = :id", Note.class);
            q.setParameter("id", person);
            List<Note> fees = q.getResultList();
            em.getTransaction().commit();
            return new HashSet<>(fees);
        }
    }

    //// US 4
    public Set<Person> getAllNotesWithDetail(){
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            TypedQuery<Person> q = em.createQuery("select distinct p from Person p join Note n on n.notee = p join PersonDetail pd on pd.person = p", Person.class);
            List<Person> People = q.getResultList();
            em.getTransaction().commit();
            return new HashSet<>(People);
        }
    }
}
