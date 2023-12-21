package main.org.example.jdbc.dao.impl111;

import main.org.example.jdbc.dao.abs.UserDAO;
import main.org.example.model.Role;
import main.org.example.model.User;
import main.org.example.util.DBUtils;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDAOImpl implements UserDAO {

    private static RoleDAOImpl roleDAO = new RoleDAOImpl();

    @Override
    public boolean create(User type) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO `users` (`name`, `email`, `pwd`, `details`, `isActive`, `role`, `created`, `updated`) " +
                    "VALUES (?, ?, ?, ?, '0', '4', CURRENT_TIMESTAMP, NULL)");
            preparedStatement.setString(1, type.getName());
            preparedStatement.setString(2, type.getEmail());
            preparedStatement.setString(3, type.getPwd());
            preparedStatement.setString(4, type.getDetails());
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
    public User findById(Integer key) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.users WHERE id = ?");
            preparedStatement.setInt(1, key);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return map(new User(), rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer key) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users_db.users WHERE `users`.`id` = ?");
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
    public boolean update(User type) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE `users` SET `name` = ?, `email` = ?, `pwd` = ?, `details` = ?, `isActive` = ?, `role` = ?, `updated` = CURRENT_TIMESTAMP WHERE `users`.`id` = ?");
            preparedStatement.setString(1, type.getName());
            preparedStatement.setString(2, type.getEmail());
            preparedStatement.setString(3, type.getPwd());
            preparedStatement.setString(4, type.getDetails());
            preparedStatement.setBoolean(5, type.getIsActive());
            //preparedStatement.setInt(6, type.getRole());
            preparedStatement.setInt(7, type.getId());
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
    public Set<User> all() {
        Set<User> users = new HashSet<>();
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.users ORDER BY id;");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                users.add(map(new User(), rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.users WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return map(new User(), rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<User> findByRole(Role role) {
        Set<User> users = new HashSet<>();
        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.users WHERE role = ? ORDER BY id");
            preparedStatement.setInt(1, role.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                users.add(map(new User(), rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void activated(User user) {
//        if (!user.getIsActive()){
//            user.setIsActive(true);
//            update(user);
//        }

        try(Connection connection = DBUtils.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE `users` SET `isActive` = ?, `updated` = CURRENT_TIMESTAMP WHERE `users`.`id` = ?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, user.getId());
            int count = preparedStatement.executeUpdate();
            if (count > 0){
                System.out.println("Successfully");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
