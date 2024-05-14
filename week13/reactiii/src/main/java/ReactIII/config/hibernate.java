package ReactIII.config;

import jakarta.persistence.EntityManagerFactory;

import java.util.Set;

import ReactIII.model.Role;
import ReactIII.model.User;
import sir.rolin.my_library.hibernate.factory;

public class hibernate {
    //// I am not Going to repeat ALL the code for an 8th time, I made it dynamic.
    private static final Set<Class<?>> classes = Set.of(User.class, Role.class);
    public static boolean isDevState = false;

    public static EntityManagerFactory getEntityManagerFactoryConfig(){
        String connection =  System.getenv("CONNECTION_STR") != null ? System.getenv("CONNECTION_STR") :"jdbc:postgresql://localhost:5432";
        String db = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") :"reactIII";
        String schema = System.getenv("DB_SCHEMA") != null ? System.getenv("DB_SCHEMA") :"public";
        if(isDevState)
        {
            schema = "dev";
        }
        return factory.getRemoteEntityManagerFactory(classes, connection, db, schema, isDevState);
    }
}
