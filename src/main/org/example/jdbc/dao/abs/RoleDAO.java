package main.org.example.jdbc.dao.abs;

import main.org.example.model.Role;
import main.org.example.jdbc.dao.abs.AbstractDAO;

public interface RoleDAO extends AbstractDAO<Role, Integer> {
    Role findByName(String name);
}
