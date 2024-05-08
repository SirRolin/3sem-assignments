package week13.ReactIII.DAO.concrete;

import jakarta.validation.ValidationException;
import week13.ReactIII.model.Role;
import week13.ReactIII.model.User;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}
