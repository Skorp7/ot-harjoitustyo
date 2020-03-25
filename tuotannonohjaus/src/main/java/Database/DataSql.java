package Database;


import Domain.*;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableArrayBase;

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
    
    public boolean format() {
        try {
            if (this.dbCon == null) {
                System.out.println("Yhteyttä tietokantaan ei löydy.(format)");
                return false;
            }
            Statement s = this.dbCon.createStatement();
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, status INTEGER)");
            s.execute("INSERT INTO Users (name, status) VALUES ('admin',1)");
            s.execute("CREATE TABLE Orders (code TEXT UNIQUE PRIMARY KEY, timestamp TEXT)");
            s.execute("CREATE TABLE Events (id INTEGER PRIMARY KEY, workphase TEXT, code TEXT NOT NULL REFERENCES Orders, usr_id REFERENCES Users, description TEXT, timestamp TEXT)");
//            this.dbCon.commit();
            System.out.println("tietokanta alustettu");
            this.formated = true;
            return true;
        } catch (Exception SQLException) {
            System.out.println("Tietokanta ei alustettu");
            System.out.println(SQLException.getMessage());
            return false;
        }
    }

    @Override
    public boolean userExists(String name) {
        try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT name from Users WHERE name=?");

            p.setString(1, name);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Käyttäjää ei löydy");
                return false;
            }
            String usrName = rr.getString("name");
             
            if (usrName.equals(name)) {
                System.out.println("Käyttäjä löytyi");
                return true;
            } else {
                System.out.println("Käyttäjää ei löydy/muu virhe");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(userexist)");
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean getUserStatus(String name) {
     try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT status from Users WHERE name=?");

            p.setString(1, name);
            ResultSet rr = p.executeQuery();
            Integer usrStatus = rr.getInt("status");
            
            if (usrStatus == 1) {
                System.out.println("Status: superuser");
                return true;
            } else {
                System.out.println("Status: normal user");
                return false;
            }
        } catch (Exception SQLException) {
            return false;
        }
    }

    @Override
    public boolean addUser(String name, int status) {

        try {
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Users (name, status) VALUES (?,?)");
            p.setString(1, name);
            p.setInt(2, status);
            p.executeUpdate();
        //    this.dbCon.commit();
            System.out.println("Käyttäjä lisätty");
            return true;
        } catch (Exception SQLException) {
            System.out.println(SQLException.getMessage());
            return false;
        }
    }

    @Override
    public boolean removeUser(String name) {
        System.out.println("Ei tuetttu vielä.");
        return false;
    }
    
    @Override
    public boolean addOrder(String code) {
          try {
            PreparedStatement p = this.dbCon.prepareStatement("INSERT INTO Orders (code, timestamp) VALUES (?,datetime('now'))");
            p.setString(1, code);
            p.executeUpdate();
        //    this.dbCon.commit();
            System.out.println("Tilaus lisätty");
            return true;
        } catch (Exception SQLException) {
            System.out.println(SQLException.getMessage());
            return false;
        }
    }
    
    public boolean orderExists(String code) {
        try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT code from Orders WHERE code=?");

            p.setString(1, code);
            ResultSet rr = p.executeQuery();
            if (!rr.next()) {
                System.out.println("Tilausta ei löydy");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(code exist)");
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    @Override
    public ArrayList<WorkPhase> getOrder(String code) {
        ArrayList<WorkPhase> events = new ArrayList<>();
        try {
            PreparedStatement p = this.dbCon.prepareStatement("SELECT E.workphase, E.description, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE code=?");

            p.setString(1, code);
            ResultSet rr = p.executeQuery();
                // Create a Workphase object from sql data
                while (rr.next()) {
                    WorkPhase event = new WorkPhase(rr.getString("timestamp"), rr.getString("workphase"), code, rr.getString("name"), rr.getString("description"));
                    System.out.println(rr.getString("timestamp") + " , " + rr.getString("workphase") + " , " + rr.getString("description") + " , " + rr.getString("name"));
                    events.add(event);
                }
        } catch (Exception e) {
            System.out.println("Yhteyttä tietokantaan ei löydy.(code exist)");
            System.out.println(e.getMessage());
        }
        if (events.isEmpty()) {
            System.out.println("tyhjä lista");
        }
        return events;
    }

    public boolean addEvent(String workphase, String code, String descr, String name) {
          try {   
            PreparedStatement p = this.dbCon.
                    prepareStatement("INSERT INTO Events (workphase, code, usr_id, description, timestamp) VALUES (?,?, (SELECT id FROM Users WHERE name=?), ?, datetime('now'))");
            p.setString(1, workphase);
            p.setString(2, code);
            p.setString(3, name);
            p.setString(4, descr);
            p.executeUpdate();
        //    this.dbCon.commit();
            System.out.println("Tilaus lisätty");
            return true;
        } catch (Exception SQLException) {
              System.out.println("Työvaihetta ei lisätty sql");
            System.out.println(SQLException.getMessage());
            return false;
        }
    }
}
    