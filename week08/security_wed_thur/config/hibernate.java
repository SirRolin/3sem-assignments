package week08.security_wed_thur.config;

import jakarta.persistence.EntityManagerFactory;
import week08.security_wed_thur.model.Role;
import week08.security_wed_thur.model.User;

import java.util.Set;

public class hibernate {
    //// I am not Going to repeat ALL the code for a 7th time, I made it dynamic.
    private static final Set<Class<?>> classes = Set.of(User.class, Role.class);
    public static boolean isDevState = false;

    public static EntityManagerFactory getEntityManagerFactoryConfig() {
        String db = "hoteldb";
        String schema = "public";
        if (isDevState) {
            schema = "dev";
        }
        return myLibrary.hibernate.factory.getEntityManagerFactory(classes, 5432, db, schema, isDevState);
    }
}
