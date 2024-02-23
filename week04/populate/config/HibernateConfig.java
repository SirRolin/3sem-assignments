package week04.populate.config;

import jakarta.persistence.EntityManagerFactory;
import week04.populate.model.Semester;
import week04.populate.model.Student;
import week04.populate.model.StudentInfo;
import week04.populate.model.Teacher;
import week04.recycling.model.Driver;
import week04.recycling.model.WasteTruck;

import java.util.Set;

public class HibernateConfig {
    //// I am not Going to repeat ALL the code for a 5th time, I made it dynamic.
    private static Set<Class> classes = Set.of(Student.class, Semester.class, Teacher.class, StudentInfo.class);
    public static boolean isDevState = false;
    public static EntityManagerFactory getEntityManagerFactoryConfig(){
        return week04.hibernate.HibernateConfig.getEntityManagerFactoryConfig(isDevState, classes);
    }
}
