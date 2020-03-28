package Database;

import Domain.*;
import java.util.ArrayList;
import javafx.collections.ObservableList;

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
    
    boolean addOrder(String code, String name);
    
    boolean orderExists(String code);
    
    ArrayList<WorkPhase> getOrder(String code);
    
    
    // Event actions
    
    boolean addEvent(String workphase, String code, String descr, String name);
    
     
     
     
}
