package database;


import domain.WorkPhase;
import domain.User;
import domain.Order;
import java.sql.*;
import java.sql.DriverManager;
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
public class DataSql implements Data {

    private Connection dbCon;
    private boolean formated;

    public DataSql() {
        try {
            this.dbCon = DriverManager.getConnection("jdbc:sqlite:testi.db");
            //this.dbCon.setAutoCommit(false);
            this.pragma();
            System.out.println("Yhteys luotu");
        } catch (Exception e) {
            System.out.println("Yhteyden luominen epäonnistui.");
            System.out.println(e.getMessage());
        }
    }

    public void pragma() throws SQLException {
        Statement s = this.dbCon.createStatement();
        s.execute("PRAGMA foreign_keys = ON");
    }
    
    public void commit()  throws SQLException {
        this.dbCon.commit();
    }
    
    public void closeConnection() throws SQLException {
        try {
            this.dbCon.close();
        } catch (Exception e) {
            System.out.println("Yhteyden sulkeminen tietokantaan epäonnistui.");
        }
    }
    
    @Override
    public boolean format() {
        try {
            if (this.dbCon == null) {
                System.out.println("Yhteyttä tietokantaan ei löydy.(format)");
                return false;
            }
            Statement s = this.dbCon.createStatement();
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, status INTEGER)");
            s.execute("INSERT INTO Users (name, status) VALUES ('admin',1)");
            s.execute("CREATE TABLE Orders (code TEXT UNIQUE PRIMARY KEY, timestamp TEXT, usr_id INTEGER REFERENCES Users)");
            s.execute("CREATE TABLE Events (id INTEGER PRIMARY KEY, workphase TEXT, code TEXT NOT NULL REFERENCES Orders, usr_id REFERENCES Users, description TEXT, timestamp TEXT)");
//            this.dbCon.commit();
            System.out.println("tietokanta alustettu");
            this.formated = true;
            return true;
        } catch (SQLException e) {
            System.out.println("Tietokanta ei alustettu");
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(String name) {
        try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT name, status from Users WHERE name=?");

            p.setString(1, name);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Käyttäjää ei löydy");
                return null;
            } else {
                System.out.println("Käyttäjä löytyi");
                return new User(rr.getString("name"), rr.getInt("status"));
            }
        } catch (SQLException e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(userexist)");
            System.out.println(e.getMessage());
            return null;
        }
    }

//    @Override
//    public boolean getUserStatus(String name) {
//     try {
//            PreparedStatement p = this.dbCon.prepareStatement("SELECT status from Users WHERE name=?");
//
//            p.setString(1, name);
//            ResultSet rr = p.executeQuery();
//            Integer usrStatus = rr.getInt("status");
//            
//            if (usrStatus == 1) {
//                System.out.println("Status: superuser");
//                return true;
//            } else {
//                System.out.println("Status: normal user");
//                return false;
//            }
//        } catch (Exception SQLException) {
//            return false;
//        }
//    }

    @Override
    public boolean addUser(User user) {

        try {
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Users (name, status) VALUES (?,?)");
            p.setString(1, user.getName());
            p.setInt(2, user.getStatus());
            p.executeUpdate();
        //    this.dbCon.commit();
            System.out.println("Käyttäjä lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeUser(String name) {
        System.out.println("Ei tuetttu vielä.");
        return false;
    }
    
    @Override
    public boolean addOrder(Order order) {
        try {
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Orders (code, timestamp, usr_id) VALUES (?,datetime('now','localtime'),(SELECT id FROM Users WHERE name=?))");
            p.setString(1, order.getCode());
            p.setString(2, order.getUserName());
            p.executeUpdate();
        //    this.dbCon.commit();
            System.out.println("Tilaus lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public Order getOrder(String code) {
        try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT code, usr_id, timestamp from Orders WHERE code=?");

            p.setString(1, code);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Tilausta ei löydy");
                return null;
            } else {
                PreparedStatement pp = this.dbCon.prepareStatement("SELECT name FROM Users WHERE id=?");
                pp.setInt(1, rr.getInt("usr_id"));
                ResultSet rrr = pp.executeQuery();
                return new Order(code, rrr.getString("name"), rr.getString("timestamp"));
            }
        } catch (SQLException e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(code exist)");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    @Override
    public ArrayList<WorkPhase> getOrderInfo(String code) {
        ArrayList<WorkPhase> events = new ArrayList<>();
        try {
            // First select the registration time for the order code
            PreparedStatement pp = this.dbCon.prepareStatement("SELECT O.timestamp, U.name FROM Orders O LEFT JOIN Users U ON U.id == O.usr_id WHERE O.code=?");

            pp.setString(1, code);
            ResultSet r = pp.executeQuery();
            WorkPhase registration = new WorkPhase(r.getString("timestamp"), "registration", code, r.getString("name"), "");
            events.add(registration);
//            System.out.println(r.getString("timestamp") + "registration" + code + r.getString("name"));

            // Then select all the events for this order code
            PreparedStatement p = this.dbCon.prepareStatement("SELECT E.workphase, E.description, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE code=?");

            p.setString(1, code);
            ResultSet rr = p.executeQuery();
            // Create a Workphase object from sql data
            while (rr.next()) {
                WorkPhase event = new WorkPhase(rr.getString("timestamp"), rr.getString("workphase"), code, rr.getString("name"), rr.getString("description"));
//                System.out.println(rr.getString("timestamp") + " , " + rr.getString("workphase") + " , " + rr.getString("description") + " , " + rr.getString("name"));
                events.add(event);
            }
        } catch (SQLException e) {
 //           System.out.println("Yhteyttä tietokantaan ei löydy.(code exist)");
            System.out.println(e.getMessage());
        }
//        if (events.isEmpty()) {
//            System.out.println("tyhjä lista");
//        }
        return events;
    }

    @Override
    public boolean addEvent(WorkPhase event) {
        try {
            PreparedStatement p = this.dbCon.
                    prepareStatement("INSERT INTO Events (workphase, code, usr_id, description, timestamp) VALUES (?,?, (SELECT id FROM Users WHERE name=?), ?, datetime('now','localtime'))");
            p.setString(1, event.getWorkphase());
            p.setString(2, event.getCode());
            p.setString(3, event.getName());
            p.setString(4, event.getDescription());
            p.executeUpdate();
            //    this.dbCon.commit();
            System.out.println("Työvaihe lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println("Työvaihetta ei lisätty sql");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
    