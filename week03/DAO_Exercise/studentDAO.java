package week03.DAO_Exercise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import week03.student.HibernateConfig;
import week03.student.Student;

import java.util.List;

public class studentDAO {
    private final EntityManagerFactory myEMF = HibernateConfig.getEntityManagerFactoryConfig();

    public void create(Student student) {
        // This method should create a new student and persist it to the database.
        try(EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }
    }

    public Student read(int id) {
        // This method should read a student from the database using the student's id.
        try(EntityManager em = myEMF.createEntityManager()) {
            return em.find(Student.class, id);
        }
    }

    public Student update(Student updStd) {
        // This method should update an existing student in the database.
        try(EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            Student unitedStudent = em.merge(updStd);
            if (unitedStudent != null) {
                em.getTransaction().commit();
                return unitedStudent;
            }
            em.getTransaction().rollback();
            return null;
        }
    }

    public void delete(int id) {
        // This method should delete a student from the database using the student's id.
        try(EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            Student st = read(id);
            if (st != null) {
                em.remove(st);
            }
            em.getTransaction().commit();
        }
    }

    public List<Student> readAllStudents() {
        // This method should retrieve all students from the database and return them as a list. Use a TypedQuery to retrieve all students.
        try(EntityManager em = myEMF.createEntityManager()) {
            em.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Student> found = (List<Student>) em.createQuery("select distinct c from Student c").getResultList();
            em.getTransaction().commit();
            return found;
        }
    }
}
