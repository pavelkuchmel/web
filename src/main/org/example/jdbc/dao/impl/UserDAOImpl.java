package main.org.example.jdbc.dao.impl;

import main.org.example.jdbc.dao.abs.RoleDAO;
import main.org.example.jdbc.dao.abs.UserDAO;
import main.org.example.model.Role;
import main.org.example.model.User;
import main.org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserDAOImpl implements UserDAO {

    RoleDAO roleDAO = new RoleDAOImpl();

    @Override
    public boolean create(User type) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (`name`, `email`, `pwd`, `details`, `role`, `is_active`, `created_ts`) VALUES (?, ?, ?, ?, 'General User', '0', CURRENT_TIMESTAMP)")){
            pstmt.setNString(1, type.getName());
            pstmt.setString(2, type.getEmail());
            pstmt.setString(3, type.getPwd());
            pstmt.setString(4, type.getDetails());
            if (pstmt.executeUpdate() > 0){
                System.out.println("User successfully created");
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findById(Integer key) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_db.users WHERE id = ?");
            preparedStatement.setInt(1, key);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setDetails(rs.getString("details"));
                user.setRole(roleDAO.findByName(rs.getString("role")));
                user.setIsActive(rs.getBoolean("is_active"));
                user.setCreated(rs.getTimestamp("created_ts"));
                user.setUpdated(rs.getTimestamp("updated_ts"));
                return user;
            }

        } catch (SQLException e) {
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
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement
                     ("UPDATE `users` SET `name` = ?, `email` = ?, `pwd` = ?, `details` = ?, `updated` = CURRENT_TIMESTAMP WHERE `users`.`id` = ?")){
            pstmt.setString(1, type.getName());
            pstmt.setString(2, type.getEmail());
            pstmt.setString(3, type.getPwd());
            pstmt.setString(4, type.getDetails());
            pstmt.setInt(5, type.getId());
            if (pstmt.executeUpdate() > 0){
                System.out.println("User successfully updated");
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
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM users_db.users");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setDetails(rs.getString("details"));
                user.setRole(roleDAO.findByName(rs.getString("role")));
                user.setIsActive(rs.getBoolean("is_active"));
                user.setCreated(rs.getTimestamp("created_ts"));
                user.setUpdated(rs.getTimestamp("updated_ts"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = DBUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT * FROM users_db.users WHERE email = ?");
            preparedStatement.setString(1, email);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setDetails(rs.getString("details"));
                user.setRole(roleDAO.findByName(rs.getString("role")));
                user.setIsActive(rs.getBoolean("is_active"));
                user.setCreated(rs.getTimestamp("created_ts"));
                user.setUpdated(rs.getTimestamp("updated_ts"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<User> findByRole(Role role) {
        return null;
    }

    @Override
    public void activated(User user) {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement
                     ("UPDATE users SET is_active = '1', UPDATED_TS = CURRENT_TIMESTAMP WHERE users.email = ?")){
            pstmt.setString(1, user.getEmail());
            if(pstmt.executeUpdate() > 0){
                System.out.println("User successfully activated!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
