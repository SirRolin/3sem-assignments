package week08.hotel_mon_tues.config;

import jakarta.persistence.EntityManagerFactory;
import week08.hotel_mon_tues.model.Hotel;
import week08.hotel_mon_tues.model.Room;

import java.util.Set;

public class hibernate {
    //// I am not Going to repeat ALL the code for a 5th time, I made it dynamic.
    private static final Set<Class<?>> classes = Set.of(Hotel.class, Room.class);
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
