package ReactIII.DAO.concrete;

import ReactIII.model.Role;
import ReactIII.model.User;
import jakarta.validation.ValidationException;

public interface ISecurityDAO {
    User getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    Role createRole(String role);
    User addUserRole(String username, String role);
}
