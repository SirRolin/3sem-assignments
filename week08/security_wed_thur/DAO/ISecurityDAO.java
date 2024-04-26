package week08.security_wed_thur.DAO;

import jakarta.validation.ValidationException;
import week08.security_wed_thur.model.Role;
import week08.security_wed_thur.model.User;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}
