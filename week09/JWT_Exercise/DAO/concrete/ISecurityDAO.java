package week09.JWT_Exercise.DAO.concrete;

import jakarta.validation.ValidationException;
import week09.JWT_Exercise.model.Role;
import week09.JWT_Exercise.model.User;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}
