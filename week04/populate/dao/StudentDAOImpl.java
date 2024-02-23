package week04.populate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import week04.populate.config.HibernateConfig;
import week04.populate.model.Semester;
import week04.populate.model.Student;
import week04.populate.model.StudentInfo;
import week04.populate.model.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class StudentDAOImpl implements IStudentDAO {
    public static EntityManagerFactory emf;
    public StudentDAOImpl(){
        emf = HibernateConfig.getEntityManagerFactoryConfig();
    }

    private static void tryEm(Consumer<EntityManager> run) {
        try(EntityManager em = emf.createEntityManager()){
            run.accept(em);
        }
    }

    private static Object tryEm(Function<EntityManager, Object> run) {
        try(EntityManager em = emf.createEntityManager()){
            return run.apply(em);
        }
    }

    @Override
    public List<Student> findAllStudentsByFirstName(String firstName) {
        List<Student> list = new ArrayList<>();
        tryEm((em) -> {
            TypedQuery<Student> tq = em.createQuery("select s from pop_student s where s.firstName = :firstName", Student.class);
            tq.setParameter("firstName", firstName);
            list.addAll(tq.getResultList());
        });
        return list;
    }

    @Override
    public List<Student> findAllStudentsByLastName(String lastName) {
        List<Student> list = new ArrayList<>();
        tryEm((em) -> {
            TypedQuery<Student> tq = em.createQuery("select s from pop_student s where s.lastName = :lastName", Student.class);
            tq.setParameter("lastName", lastName);
            list.addAll(tq.getResultList());
        });
        return list;
    }

    @Override
    public long findTotalNumberOfStudentsBySemester(String semesterName) {
        return (Long) tryEm((em) -> {
            Query tq = em.createQuery("select count(*) from pop_student s where s.semester.name = :semesterName");
            tq.setParameter("semesterName", semesterName);
            return tq.getSingleResult();
        });
    }

    @Override
    public long findTotalNumberOfStudentsByTeacher(Teacher teacher) {
        return (Long) tryEm((em) -> {
            TypedQuery<Long> tq = em.createQuery("select count(*) from pop_student s where :teacher MEMBER OF s.semester.teachers", Long.class);
            tq.setParameter("teacher", teacher);
            return tq.getSingleResult();
        });
    }

    @Override
    public Teacher findTeacherWithMostSemesters() {
        return (Teacher) tryEm((em) -> {
            Query tq = em.createQuery("select t from Teacher t order by size(t.semesters) desc").setMaxResults(1);
            return tq.getSingleResult();
        });
    }

    @Override
    public Semester findSemesterWithFewestStudents() {
        return (Semester) tryEm((em) -> {
            TypedQuery<Semester> tq = em.createQuery("select s from Semester s order by size(s.students) asc", Semester.class).setMaxResults(1);
            return tq.getSingleResult();
        });
    }

    @Override
    public StudentInfo getAllStudentInfo(int id) {
        return (StudentInfo) tryEm((em) -> {
            TypedQuery<StudentInfo> tq = em.createQuery("select s from student_info s where s.id = :id", StudentInfo.class);
            tq.setParameter("id", id);
            return tq.getSingleResult();
        });
    }
}
