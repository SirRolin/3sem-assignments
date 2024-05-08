package sir.rolin.my_library.hibernate;

import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;


@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class factory {

    private static final HashMap<Set<Class<?>>, EntityManagerFactory> entityManagerFactories = new HashMap<>();

    private static EntityManagerFactory buildLocalhostEntityFactoryConfig(Set<Class<?>> classes, Integer port, String db, String schema, boolean resetDB) {
        return buildEntityFactoryConfig(classes, "jdbc:postgresql://localhost:" + port + "/", db, schema, resetDB);
    }

    private static EntityManagerFactory buildEntityFactoryConfig(Set<Class<?>> classes, String Connection, String db, String schema, boolean resetDB) {
        try {
            Configuration configuration = new Configuration();

            //// This is my own workaround to the configuration because I was tired of duplicate code.
            classes.forEach(configuration::addAnnotatedClass);

            Properties props = new Properties();

            props.put("hibernate.connection.url", Connection + "/" + db + "?currentSchema=" + schema);
            //// create creates a new every time you connect.
            //// update creates only new once and otherwise uses the data that's up
            props.put("hibernate.hbm2ddl.auto", resetDB ? "create" : "update");
            if(System.getenv("DB_USERNAME") == null || System.getenv("DB_PASSWORD") == null ) {
                System.out.println("Enviourment variables DB_USERNAME or DB_PASSWORD not set, using default (postgres) instead.");
            }
            props.put("hibernate.connection.username", System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "postgres");
            props.put("hibernate.connection.password", System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "postgres");
            props.put("hibernate.show_sql", "true"); // show sql in console
            props.put("hibernate.format_sql", "true"); // format sql in console
            props.put("hibernate.use_sql_comments", "true"); // show sql comments in console

            //props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // dialect for postgresql - already default
            props.put("hibernate.connection.driver_class", "org.postgresql.Driver"); // driver class for postgresql
            props.put("hibernate.archive.autodetection", "class"); // hibernate scans for annotated classes
            props.put("hibernate.current_session_context_class", "thread"); // hibernate current session context

            props.put("hibernate.metamodel.mapping.DiscriminatorType.getJdbcTypeCount()", "all");


            return getEntityManagerFactory(configuration, props);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            System.err.println("The database: " + db + " on localhost:" + port + " is probably not running or it doesn't have the schematic: " + schema);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static EntityManagerFactory getEntityManagerFactory(Configuration configuration, Properties props) {
        configuration.setProperties(props);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        System.out.println("Hibernate Java Config serviceRegistry created");

        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        return sf.unwrap(EntityManagerFactory.class);
    }

    public static EntityManagerFactory getEntityManagerFactory(Set<Class<?>> classes, Integer port, String db, String schema) {
        return getRemoteEntityManagerFactory(classes, "jdbc:postgresql://localhost:" + port, db, schema, false);
    }

    public static EntityManagerFactory getEntityManagerFactory(Set<Class<?>> classes, Integer port, String db, String schema, boolean resetDB) {
        return getRemoteEntityManagerFactory(classes, "jdbc:postgresql://localhost:" + port, db, schema, resetDB);
    }

    public static EntityManagerFactory getRemoteEntityManagerFactory(Set<Class<?>> classes, String connection, String db, String schema) {
        return getRemoteEntityManagerFactory(classes, connection, db, schema, false);
    }

    public static EntityManagerFactory getRemoteEntityManagerFactory(Set<Class<?>> classes, String connection, String db, String schema, boolean resetDB) {
        if (!entityManagerFactories.containsKey(classes) || !entityManagerFactories.get(classes).isOpen()) {
            EntityManagerFactory emf = buildEntityFactoryConfig(classes, connection, db, schema, resetDB);
            entityManagerFactories.remove(classes);
            entityManagerFactories.put(classes, emf);
        }
        return entityManagerFactories.get(classes);
    }
}
