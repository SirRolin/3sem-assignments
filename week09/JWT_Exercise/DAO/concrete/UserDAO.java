package week09.JWT_Exercise.DAO.concrete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.ValidationException;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.Nullable;
import week09.JWT_Exercise.controller.UserC;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.User;

import static week09.JWT_Exercise.config.hibernate.getEntityManagerFactoryConfig;

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
        try (EntityManager em = emf.createEntityManager()) {
            //// if there's already a user with that username return null
            if (em.find(User.class, username) != null) {
                return null;
            }
            User user = new User(username, password); //// Construct hashes the password
            user.addRole(createRole(UserC.Role.USER.toString()));
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    @Override
    public Role createRole(String role) {
        try(EntityManager em = emf.createEntityManager()){
            Role result = em.find(Role.class, role);
            //// if there's no Role with that name make a new one
            if(result == null) {
                result = new Role();
                result.setRole(role);
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();
            }
            //// otherwise give that role
            return result;
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
                em.getTransaction().begin();
                em.merge(user);
                em.getTransaction().commit();
            }
            return user;
        }
    }
}
