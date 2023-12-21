package main.org.example.jdbc.dao.abs;

import main.org.example.model.Role;
import main.org.example.model.User;

import java.util.Set;

public interface UserDAO extends AbstractDAO<User, Integer> {
    User findByEmail(String email);
    Set<User> findByRole(Role role);
    void activated(User user);
}
