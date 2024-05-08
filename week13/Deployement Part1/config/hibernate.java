package week13.ReactIII.config;

import jakarta.persistence.EntityManagerFactory;
import week13.ReactIII.model.Hotel;
import week13.ReactIII.model.Role;
import week13.ReactIII.model.Room;
import week13.ReactIII.model.User;

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
