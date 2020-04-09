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
    private final String fileName;

    public DataSql(String fileName) {
        this.fileName = fileName;
    }
    
    public void connect() {
        try {
            this.dbCon = DriverManager.getConnection(this.fileName);
            System.out.println("Yhteys luotu");
        } catch (SQLException e) {
            System.out.println("Yhteyden luominen epäonnistui.");
            System.out.println(e.getMessage());
        }
    }    

    public void closeConnection() {
        try {
            this.dbCon.close();
            System.out.println("Yhteys suljettu.");
        } catch (SQLException e) {
            System.out.println("Yhteyden sulkeminen tietokantaan epäonnistui.");
        }
    }
    
    
    @Override
    public boolean format() {
        try {
            this.connect();
           // this.dbCon = DriverManager.getConnection("jdbc:sqlite:testi.db");
            Statement s = this.dbCon.createStatement();
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, status INTEGER)");
            s.execute("INSERT INTO Users (name, status) VALUES ('admin',1)");
            s.execute("CREATE TABLE Orders (code TEXT UNIQUE PRIMARY KEY, timestamp TEXT, usr_id INTEGER REFERENCES Users)");
            s.execute("CREATE TABLE Events (id INTEGER PRIMARY KEY, workphase TEXT, code TEXT NOT NULL REFERENCES Orders, usr_id REFERENCES Users, description TEXT, timestamp TEXT)");
            s.execute("PRAGMA foreign_keys = ON");
            this.dbCon.commit();
            s.close();
            System.out.println("tietokanta alustettu");
            return true;
        } catch (SQLException e) {
            System.out.println("Tietokanta ei alustettu");
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public User getUser(String name) {
        try {
            this.connect();
            User foundUser = null;
            PreparedStatement p = this.dbCon.prepareStatement("SELECT name, status from Users WHERE name=?");
            p.setString(1, name);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Käyttäjää ei löydy");
            } else {
                System.out.println("Käyttäjä löytyi");
                foundUser = new User(rr.getString("name"), rr.getInt("status"));
            }
            p.close();
            rr.close();
            return foundUser;
        } catch (SQLException e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(userexist)");
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            this.connect();
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Users (name, status) VALUES (?,?)");
            p.setString(1, user.getName());
            p.setInt(2, user.getStatus());
            p.executeUpdate();
            p.close();
            System.out.println("Käyttäjä lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println("Käyttäjää ei lisätty.");
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnection();
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
            this.connect();
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Orders (code, timestamp, usr_id) VALUES (?,?,(SELECT id FROM Users WHERE name=?))");
            p.setString(1, order.getCode());
            p.setString(2, order.getTimeStamp());
            p.setString(3, order.getUserName());
            p.executeUpdate();
            p.close();
            System.out.println("Tilaus lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnection();
        }
    }
    
    @Override
    public Order getOrder(String code) {
        try {
            this.connect();
            Order foundOrder = null;
            PreparedStatement p = this.dbCon.prepareStatement("SELECT code, usr_id, timestamp from Orders WHERE code=?");
            p.setString(1, code);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Tilausta ei löydy");
            } else {
                PreparedStatement pp = this.dbCon.prepareStatement("SELECT name FROM Users WHERE id=?");
                pp.setInt(1, rr.getInt("usr_id"));
                ResultSet rrr = pp.executeQuery();
                foundOrder = new Order(code, rrr.getString("name"), rr.getString("timestamp"));
                rrr.close();
                pp.close();
            }
            p.close();
            rr.close();
            return foundOrder;
 
        } catch (SQLException e) {
            System.out.println("Yhteyttä tietokantaan ei löydy. Tilausta ei löydy.");
            System.out.println(e.getMessage());
            return null;
        } finally {
            this.closeConnection();
        }
    }
    
    @Override
    public ArrayList<WorkPhase> getOrderInfo(String code) {
        ArrayList<WorkPhase> events = new ArrayList<>();
        try {
//            // First select the registration time for the order code
//            PreparedStatement pp = this.dbCon.prepareStatement("SELECT O.timestamp, U.name FROM Orders O LEFT JOIN Users U ON U.id == O.usr_id WHERE O.code=?");
//
//            pp.setString(1, code);
//            ResultSet r = pp.executeQuery();
//            WorkPhase registration = new WorkPhase(r.getString("timestamp"), "registration", code, r.getString("name"), "");
//            events.add(registration);
            this.connect();
            // Then select all the events for this order code
            PreparedStatement p = this.dbCon.prepareStatement("SELECT E.workphase, E.description, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE code=?");
            p.setString(1, code);
            ResultSet rr = p.executeQuery();
            // Create a Workphase object from sql data
            while (rr.next()) {
                WorkPhase event = new WorkPhase(rr.getString("timestamp"), rr.getString("workphase"), code, rr.getString("name"), rr.getString("description"));
                events.add(event);
            }
            p.close();
            rr.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnection();
        }
        return events;
    }
    
    
    @Override
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
        ArrayList<WorkPhase> events = new ArrayList<>();
        // First select the events with specific date and order them by date, then group by order code
        try {
            this.connect();
            PreparedStatement p = this.dbCon.prepareStatement("SELECT * FROM (SELECT E.workphase, E.description, E.code, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE date(E.timestamp)=? ORDER BY E.timestamp DESC) AS S GROUP BY S.code");
            p.setString(1, date);
            ResultSet rr = p.executeQuery();
            // Create a Workphase object from sql data
            while (rr.next()) {
                WorkPhase event = new WorkPhase(rr.getString("timestamp"), rr.getString("workphase"), rr.getString("code"), rr.getString("name"), rr.getString("description"));
                events.add(event);
            }
            p.close();
            rr.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnection();
        }
        return events;
    }

    @Override
    public boolean addEvent(WorkPhase event) {
        try {
            this.connect();
            PreparedStatement p = this.dbCon.
                    prepareStatement("INSERT INTO Events (workphase, code, usr_id, description, timestamp) VALUES (?,?, (SELECT id FROM Users WHERE name=?), ?, ?)");
            p.setString(1, event.getWorkphase());
            p.setString(2, event.getCode());
            p.setString(3, event.getName());
            p.setString(4, event.getDescription());
            p.setString(5, event.getTimestamp());
            p.executeUpdate();
            p.close();
            System.out.println("Työvaihe lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println("Työvaihetta ei lisätty sql");
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void removeAllDataFromDatabase() {
        try {
            this.connect();
            Statement s = this.dbCon.createStatement();
            s.execute("PRAGMA foreign_keys = OFF");
            s.execute("DROP TABLE IF EXISTS Users");
            s.execute("DROP TABLE IF EXISTS Orders");
            s.execute("DROP TABLE IF EXISTS Events");
            s.execute("DROP TABLE IF EXISTS Users");
            s.close();
            System.out.println("Tietokanta tyhjennetty.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnection();
        }

    }
}
    