package Database;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakorpi
 */
public interface Data {
    
    // Database actions:
        
    boolean format();

    
    // User actions:
    
    boolean userExists(String name);
    
    // 0 = normal user, 1 = superuser
    boolean getUserStatus(String name);
    
    boolean addUser(String name, int status);
    
    boolean removeUser(String name);

    
    // Order actions:
    
    boolean addOrder(String code);
    
    boolean orderExists(String code);
    
    boolean getOrder(String code);
    
     
     
     
}
