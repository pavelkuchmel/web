package main.org.example.jdbc.dao.impl;

import main.org.example.jdbc.dao.abs.RoleDAO;
import main.org.example.model.Role;
import main.org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class RoleDAOImpl implements RoleDAO {
    @Override
    public boolean create(Role type) {
        return false;
    }

    @Override
    public Role findById(Integer key) {
        return null;
    }

    @Override
    public boolean deleteById(Integer key) {
        return false;
    }

    @Override
    public boolean update(Role type) {
        return false;
    }

    @Override
    public Set<Role> all() {
        return null;
    }

    @Override
    public Role findByName(String name) {
        try (Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users_db.roles WHERE name = ?");) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                Role role = new Role();
                role.setId(rs.getInt(1));
                role.setName(name);
                role.setDetails(rs.getString("details"));
                role.setUpdated(rs.getTimestamp("created_ts"));
                role.setUpdated(rs.getTimestamp("updated_ts"));
                return role;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
