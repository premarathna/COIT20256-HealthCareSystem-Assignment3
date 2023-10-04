/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import model.User;

/**
 *
 * @author Chamali
 */
public class UserManager {
    private static UserManager userManager = null;
    private User user;
   
    private UserManager() {}
    
    public static UserManager shared() {
        if (userManager == null)
            userManager = new UserManager();
          
        return userManager;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
