package main.org.example.dao;

import main.org.example.model.Role;
import main.org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends AbstractJpaDAO<Integer, Role>{

    public Role findByRoleName(String name) {
        return findByCondition("where name = " + "'" + name + "'");
    }

}
