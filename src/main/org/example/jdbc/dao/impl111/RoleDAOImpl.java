package main.org.example.jdbc.dao.impl111;

import main.org.example.jdbc.dao.abs.RoleDAO;
import main.org.example.model.Role;
import main.org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RoleDAOImpl implements RoleDAO {

//    private static List<Role> roles = Arrays.asList(new Role(123, "Admin", "Admin role", null, null),
//            new Role(123, "Manager", "Manager role", null, null),
//            new Role(123, "General user", "General user role", null, null));

    @Override
    public Role findByName(String name) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.roles WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return map(new Role(), rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean create(Role type) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `roles` (`name`, `details`, `created`) " +
                    "VALUES (?, ?, CURRENT_TIMESTAMP);");
            preparedStatement.setString(1, type.getName());
            preparedStatement.setString(2, type.getDetails());
            int count = preparedStatement.executeUpdate();
            if (count > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Role findById(Integer key) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.roles WHERE id = ?");
            preparedStatement.setInt(1, key);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return map(new Role(), rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer key) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users_db.roles WHERE `roles`.`id` = ?");
            preparedStatement.setInt(1, key);
            int count = preparedStatement.executeUpdate();
            if (count > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Role type) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE `roles` SET `name` = ?, `details` = ?, `updated` = CURRENT_TIMESTAMP WHERE `roles`.`id` = ?");
            preparedStatement.setString(1, type.getName());
            preparedStatement.setString(2, type.getDetails());
            preparedStatement.setInt(3, type.getId());
            int count = preparedStatement.executeUpdate();
            if (count > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<Role> all() {
        Set<Role> roles = new HashSet<>();
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.roles ORDER BY id;");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                roles.add(map(new Role(), rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return roles;
    }
}
