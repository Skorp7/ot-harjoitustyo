/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import database.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sakorpi
 */
public class Service {

    private Data database;
    private DateTimeFormatter formatter;
    private User loggedIn;

    public Service(Data database) {
        // Create database:
        this.database = database;
        this.database.format();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public boolean login(String name) {
        if (this.database.getUser(name) != null) {
            this.loggedIn = this.getUser(name);
        }
        return this.database.getUser(name) != null;
    }

    // try to format database, return false if can not format (most likely already formated)
    public boolean checkDatabase() {
        return this.database.format();
    }

    public boolean addUser(String name, Integer status) {
        if (this.database.getUser(name) != null) {
            return false;
        }
        User user = new User(name, status);
        return this.database.addUser(user);
    }

    public User getUser(String name) {
        return this.database.getUser(name);
    }

    // Order functions
    public boolean addOrder(String code, User user) {
        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.format(this.formatter);
        Boolean succeed = false;
        Order order = new Order(code, user.getName(), modTimestamp);
        if (this.database.getOrder(code) != null || this.database.getUser(user.getName()) == null) {
            return false;
        }
        if (this.database.addOrder(order)) {
            succeed = true;
        }
        this.addEvent("Sisäänkirjaus", code, "", user);
        return succeed;
    }

    public ObservableList<WorkPhase> getOrderInfo(String code) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfo(code);
        list.addAll(events);
        return list;
    }
    
    public ObservableList<WorkPhase> getOrderInfoByDate(String date) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfoByDate(date);
        list.addAll(events);
        return list;
    }

    public Order getOrder(String code) {
        return this.database.getOrder(code);
    }
    
    public User getLoggedInUser() {
        return this.loggedIn;
    }

    // Event functions
    public boolean addEvent(String workphase, String code, String descr, User user) {
        if (this.database.getOrder(code) == null || this.database.getUser(user.getName()) == null) {
            return false;
        }

        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.format(this.formatter);
        WorkPhase event = new WorkPhase(modTimestamp, workphase, code, user.getName(), descr);
        return this.database.addEvent(event);
    }
}
