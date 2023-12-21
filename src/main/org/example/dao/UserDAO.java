package main.org.example.dao;

import main.org.example.model.User;

public class UserDAO extends AbstractJpaDAO<Integer, User>{
    public User findByEmail(String email){
        return findByCondition("where email = " + "'" + email + "'");
    }

    public void activated(User user){
        if (update(user).getIsActive()) System.out.println("Activated successfully");
    }
}
