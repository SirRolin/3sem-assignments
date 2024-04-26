package week04.populate.jUnit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import week04.populate.config.HibernateConfig;
import week04.populate.dao.IStudentDAO;
import week04.populate.dao.StudentDAOImpl;
import week04.populate.model.Student;
import week04.populate.model.Teacher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOImplTest {

    private static IStudentDAO dao;

    @BeforeAll
    static void beforeAll(){
        HibernateConfig.isDevState = true;
        dao = new StudentDAOImpl();
        EntityManagerFactory emf = StudentDAOImpl.emf;
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
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
            em.createNativeQuery("""
                    INSERT INTO teacher (id, firstname, lastname)
                    VALUES
                        (1, 'Gustav', 'Jakobsen'),
                        (2, 'Stanley', 'Ford'),
                        (3, 'Ottis', 'Andrin'),
                        (4, 'Gustav', 'Malis'),
                        (5, 'Magnus', 'Jakobsen')""").executeUpdate();
            em.createNativeQuery("""
                    INSERT INTO dev.sem_assigned_teachers (semesters_id, teachers_id)
                    VALUES
                        (1, 2),
                        (2, 2),
                        (3, 2),
                        (4, 2),
                        (5, 3)""").executeUpdate();
            em.getTransaction().commit();
        }

    }

    @Test
    void findAllStudentsByFirstName() {
        List<Student> sts = dao.findAllStudentsByFirstName("Gustav");
        System.out.println(sts);
        assertFalse(sts.isEmpty());
        assertTrue(sts.stream().allMatch((s) -> s.getFirstName().equals("Gustav")));
    }

    @Test
    void findAllStudentsByLastName() {
        List<Student> sts = dao.findAllStudentsByLastName("Jakobsen");
        System.out.println(sts);
        assertFalse(sts.isEmpty());
        assertTrue(sts.stream().allMatch((s) -> s.getLastName().equals("Jakobsen")));
    }

    @Test
    void findTotalNumberOfStudentsBySemester() {
        Long lng = dao.findTotalNumberOfStudentsBySemester("3Sem Dat");
        assertEquals(2, lng);
    }

    @Test
    void findTotalNumberOfStudentsByTeacher() {
        Teacher t;
        try(EntityManager em = StudentDAOImpl.emf.createEntityManager()){
            t = em.find(Teacher.class, 2);
        }
        Long lng = dao.findTotalNumberOfStudentsByTeacher(t);
        assertEquals(4, lng);
    }

    @Test
    void findTeacherWithMostSemesters() {
        assertEquals(2, dao.findTeacherWithMostSemesters().getId());
    }

    @Test
    void findSemesterWithFewestStudents() {
        assertEquals(1, dao.findSemesterWithFewestStudents().getId());
    }

    @Test
    void getAllStudentInfo() {
        dao.getAllStudentInfo(1);
    }
}