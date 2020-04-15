/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import database.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sakorpi
 */
public class Service {

    private final Data database;
    private final DateTimeFormatter formatter;
    private User loggedIn;

    public Service(Data database) {
        // Create database:
        this.database = database;
        this.database.format();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
   
    // try to format database, return false if can not format (most likely already formated)
    public boolean checkDatabase() {
        return this.database.format();
    }

    // User functions:
    // Return true if user exists and store it to 'loggedIn'
    // This method is also used by UI to log User out. Then use login(null) and set 'loggedIn' null.
    public boolean login(String name) {
        if (this.database.getUser(name) != null) {
            this.loggedIn = this.getUser(name);
            return true;
        }
        return false;
    }

    public boolean logOut() {
        this.loggedIn = null;
        return true;
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

    public Order getOrder(String code) {
        return this.database.getOrder(code);
    }

    public User getLoggedInUser() {
        return this.loggedIn;
    }

    // Event functions:
    // Give event a timestamp and add Event
    public boolean addEvent(String workphase, String code, String descr, User user) {
        if (this.database.getOrder(code) == null || this.database.getUser(user.getName()) == null) {
            return false;
        }

        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.format(this.formatter);
        WorkPhase event = new WorkPhase(modTimestamp, workphase, code, user.getName(), descr);
        return this.database.addEvent(event);
    }

    // Get all Events for an asked Order as list
    public ObservableList<WorkPhase> getOrderInfo(String code) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfo(code);
        list.addAll(events);
        return list;
    }

    // Get all newest Events for an asked date as list
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
       // ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfoByDate(date);
       // list.addAll(events);
        return events;
    }
    
    // Group Events by Order code and show the latest Events
    public ObservableList<WorkPhase> getOrderInfoByDateGrouped(String date) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.getOrderInfoByDate(date);
        ArrayList<WorkPhase> modEvents = new ArrayList<>();
        ArrayList<String> codeCheckList = new ArrayList<>();
        for (WorkPhase e : events) {
            if (!codeCheckList.contains(e.getCode())) {
                modEvents.add(e);
                codeCheckList.add(e.getCode());
            }
        }
        list.addAll(modEvents);
        return list;
    }
    
    // Chart functions:
    // Create a map which contains event amount per day, for 30days
    public HashMap<LocalDate, Integer> getOrderAmount30Days() {
        LocalDate today = LocalDate.now();
        HashMap<LocalDate, Integer> orderAmounts = new HashMap<>();
        HashMap<String, Integer> orders = this.database.getOrderCountByDate();
        int i = 0;
        while (i < 30) {
            LocalDate date = today.minusDays(i);
            // Format value 0 for every day before real values
            orderAmounts.putIfAbsent(date, 0);
            orders.entrySet().stream().forEach(pair -> {
                if (pair.getKey().contains(date.toString())) {
                    orderAmounts.put(date, pair.getValue());
                }
            });
            i += 1;
        }
        return orderAmounts;
    }

}
