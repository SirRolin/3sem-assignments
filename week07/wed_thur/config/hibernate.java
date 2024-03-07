package week07.wed_thur.config;

import jakarta.persistence.EntityManagerFactory;
import week07.wed_thur.model.Hotel;
import week07.wed_thur.model.Room;

import java.util.Set;

public class hibernate {
    //// I am not Going to repeat ALL the code for a 5th time, I made it dynamic.
    private static final Set<Class> classes = Set.of(Hotel.class, Room.class);
    public static boolean isDevState = false;
    public static EntityManagerFactory getEntityManagerFactoryConfig(){
        String db = "hoteldb";
        String schema = "public";
        if(isDevState)
        {
            schema = "dev";
        }
        return myLibrary.hibernate.factory.getEntityManagerFactory(classes, 5432, db, schema);
    }
}
