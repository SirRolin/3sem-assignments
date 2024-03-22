package week09.JWT_Exercise.config;

import jakarta.persistence.EntityManagerFactory;
import week09.JWT_Exercise.model.Hotel;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.Room;
import week09.JWT_Exercise.model.User;

import java.util.Set;

public class hibernate {
    //// I am not Going to repeat ALL the code for an 8th time, I made it dynamic.
    private static final Set<Class<?>> classes = Set.of(Hotel.class, Room.class, User.class, Role.class);
    public static boolean isDevState = false;

    public static EntityManagerFactory getEntityManagerFactoryConfig(){
        String db = "hoteldb";
        String schema = "public";
        if(isDevState)
        {
            schema = "dev";
        }
        return myLibrary.hibernate.factory.getEntityManagerFactory(classes, 5432, db, schema, isDevState);
    }
}
