package database;

import domain.WorkPhase;
import domain.User;
import domain.Order;
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
    
    void removeAllDataFromDatabase();
    
    // User actions:
    
    User getUser(String name);
    
    boolean addUser(User user);
    
    boolean removeUser(String name);

    
    // Order actions:
    
    boolean addOrder(Order order);
    
    Order getOrder(String code);
    
    ArrayList<WorkPhase> getOrderInfo(String code);
    
    ArrayList<WorkPhase> getOrderInfoByDate(String date);
    
    
    // Event actions
    
    boolean addEvent(WorkPhase event);
    
     
     
     
}
