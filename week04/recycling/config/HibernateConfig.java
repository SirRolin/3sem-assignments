package week04.recycling.config;

import jakarta.persistence.EntityManagerFactory;
import week04.recycling.model.Driver;
import week04.recycling.model.WasteTruck;

import java.util.Set;

public class HibernateConfig {
    //// I am not Going to repeat ALL the code for a 5th time, I made it dynamic.
    private static Set<Class> classes = Set.of(Driver.class, WasteTruck.class);
    public static boolean isDevState = false;
    public static EntityManagerFactory getEntityManagerFactoryConfig(){
        return week04.hibernate.HibernateConfig.getEntityManagerFactoryConfig(isDevState, classes);
    }
}
