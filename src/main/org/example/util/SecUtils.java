package main.org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import main.org.example.model.User;

import java.util.Arrays;

public class SecUtils {
    public static boolean hasRole(HttpServletRequest request , String... roleNames){
        User user = ServletUtils.getUserFromSession(request);
        if(user == null){ // Temp for Stranger
            return false;
        }
        return Arrays.asList(roleNames).contains(user.getRole().getName());
    }
}
