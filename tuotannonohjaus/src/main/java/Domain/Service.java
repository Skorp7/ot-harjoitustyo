/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import Database.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sakorpi
 */


public class Service {
    
    private Data database;
    
    public Service() {
        // Create database:
        this.database = new DataSql();
    }
    
    public boolean login(String name) {
        if (database.userExists(name)) {
            return true;
        } else {
            return false;
        }
    }

    // try to format database, return false if can not format (most likely already formated)
    public boolean checkDatabase() {
        return database.format();
    }
    
    // User functions
    
    public boolean getUserStatus(String name) {
        return database.getUserStatus(name);
    }

    public boolean addUser(String name, Integer status) {
        return database.addUser(name, status);
    }
    
    // Order functions

    public boolean addOrder(String code) {
        return database.addOrder(code);
    }

    public ObservableList<WorkPhase> getOrder(String code) {
       ObservableList<WorkPhase> list = FXCollections.observableArrayList();
       list.addAll(database.getOrder(code));
        return list;
    }

    public boolean orderExists(String code) {
        return database.orderExists(code);
    }

    // Event functions
    
    public boolean addEvent(String workphase, String code, String descr, String name) {
        return database.addEvent(workphase, code,descr, name);
    }
}
