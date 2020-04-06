/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import database.Data;
import database.DataSql;
import java.util.ArrayList;
import java.util.Date;
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
        return this.database.getUser(name) != null;
    }

    // try to format database, return false if can not format (most likely already formated)
    public boolean checkDatabase() {
        return this.database.format();
    }

    public boolean addUser(String name, Integer status) {
        User user = new User(name, status);
        return this.database.addUser(user);
    }

    public User getUser(String name) {
        return this.database.getUser(name);
    }

    // Order functions
    public boolean addOrder(String code, String userName) {
        String timestamp = new Date().toString();
        Boolean succeed = false;
        Order order = new Order(code, userName, timestamp);
        if (this.database.addOrder(order)) {
            succeed = true;
        }
        this.addEvent("Sisäänkirjaus", code, "", userName);
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

    // Event functions
    public boolean addEvent(String workphase, String code, String descr, String name) {
        String timestamp = new Date().toString();
        WorkPhase event = new WorkPhase(timestamp, workphase, code, name, descr);
        return this.database.addEvent(event);
    }
}
