package week09.JWT_Exercise.DAO.concrete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.ValidationException;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.Nullable;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.User;

import static week09.security_wed_thur.config.hibernate.getEntityManagerFactoryConfig;

public class UserDAO implements ISecurityDAO {
    private static EntityManagerFactory emf;

    public UserDAO() {
        emf = getEntityManagerFactoryConfig();
    }

    @Override
    public User getVerifiedUser(String username, String password) throws ValidationException {
        try(EntityManager em = emf.createEntityManager()){
            User user = em.find(User.class, username);
            if(user != null && user.verifyPassword(password)) {
                Hibernate.initialize(user.getRoles());
                return user;
            }

            throw new ValidationException("username or password is incorrect");
        }
    }

    /**
     * creates a user if it doesn't already exist
     * @return returns null if user is already taken otherwise returns the user
     */
    @Override
    @Nullable
    public User createUser(String username, String password) {
        try(EntityManager em = emf.createEntityManager()){
            //// if there's already a user with that username return null
            if(em.find(User.class, username) != null){
                return null;
            }
            User user = new User(username, password); //// Construct hashes the password
            em.persist(user);
            return user;
        }
    }

    @Override
    @Nullable
    public Role createRole(String role) {
        try(EntityManager em = emf.createEntityManager()){
            //// if there's no user with that username
            if(em.find(Role.class, role) != null){
                return null;
            }
            Role newRole = new Role();
            newRole.setRole(role);
            em.persist(newRole);
            return newRole;
        }
    }

    /**
     * Kinda insecure as if you have the username of someone and a role you can get their user without password.
     * @param username that exists in the DB
     * @param role that exists in the DB
     * @return user if they both exist in the DB otherwise null
     */
    @Override
    @Nullable
    public User addUserRole(String username, String role) {
        try(EntityManager em = emf.createEntityManager()){
            User user = em.find(User.class, username);
            Role theRole = em.find(Role.class, role);
            if(user != null && theRole != null){
                user.addRole(theRole);
            }
            return user;
        }
    }
}
