package database;

import domain.WorkPhase;
import domain.User;
import domain.Order;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author sakorpi
 */
public class DataSql implements Data {

    private Connection dbCon;
    private final String fileName;
    private PreparedStatement p;
    private ResultSet rr;
    private Statement s;

    public DataSql(String fileName) {
        this.fileName = fileName;
    }

    public void connect() {
        try {
            this.dbCon = DriverManager.getConnection(this.fileName);
            this.dbCon.setAutoCommit(false);
            System.out.println("Yhteys luotu");
        } catch (SQLException e) {
            System.out.println("Yhteyden luominen epäonnistui.");
            System.out.println(e.getMessage());
        }
    }

    public void closeConnections() {
        try {
            if (this.dbCon != null) {
                this.dbCon.close();
            }
            if (this.rr != null) {
                this.rr.close();
            }
            if (this.p != null) {
                this.p.close();
            }
            if (this.s != null) {
                this.s.close();
            }
            System.out.println("Yhteydet suljettu.");
        } catch (SQLException e) {
            System.out.println("Yhteyden sulkeminen tietokantaan epäonnistui.");
        }
    }

    @Override
    public boolean format() {
        try {
            this.connect();
            // this.dbCon = DriverManager.getConnection("jdbc:sqlite:testi.db");
            this.s = this.dbCon.createStatement();
            this.s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT UNIQUE, status INTEGER)");
            this.s.execute("INSERT INTO Users (name, status) VALUES ('admin',1)");
            this.s.execute("CREATE TABLE Orders (code TEXT UNIQUE PRIMARY KEY, timestamp TEXT, usr_id INTEGER REFERENCES Users)");
            this.s.execute("CREATE TABLE Events (id INTEGER PRIMARY KEY, workphase TEXT, code TEXT NOT NULL REFERENCES Orders, usr_id REFERENCES Users, description TEXT, timestamp TEXT)");
            this.s.execute("PRAGMA foreign_keys = ON");
            this.dbCon.commit();
            //        s.close();
            System.out.println("tietokanta alustettu");
            return true;
        } catch (SQLException e) {
            System.out.println("Tietokanta ei alustettu" + e.getMessage());
            return false;
        } finally {
            this.closeConnections();
        }
    }

    @Override
    public User getUser(String name) {
        try {
            this.connect();
            this.p = this.dbCon.prepareStatement("SELECT name, status from Users WHERE name=?");
            this.p.setString(1, name);
            this.rr = p.executeQuery();
            if (!this.rr.next()) {
                System.out.println("Käyttäjää ei löydy");
                return null;
            } else {
                System.out.println("Käyttäjä löytyi");
                return new User(this.rr.getString("name"), this.rr.getInt("status"));
            }
        } catch (SQLException e) {
      //      System.out.println("Yhteyttä tietokantaan ei löydy.(userexist)" + e.getMessage());
            return null;
        } finally {
            this.closeConnections();
        }
    }

    @Override
    public boolean addUser(User user) {
        try {
            this.connect();
            this.p = this.dbCon.prepareStatement("INSERT INTO Users (name, status) VALUES (?,?)");
            this.p.setString(1, user.getName());
            this.p.setInt(2, user.getStatus());
            this.p.executeUpdate();
            this.dbCon.commit();
            //     p.close();
            System.out.println("Käyttäjä lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println("Käyttäjää ei lisätty.");
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnections();
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
            this.p = this.dbCon.prepareStatement("INSERT INTO Orders (code, timestamp, usr_id) VALUES (?,?,(SELECT id FROM Users WHERE name=?))");
            this.p.setString(1, order.getCode());
            this.p.setString(2, order.getTimeStamp());
            this.p.setString(3, order.getUserName());
            this.p.executeUpdate();
            this.dbCon.commit();
            // p.close();
            System.out.println("Tilaus lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            this.closeConnections();
        }
    }

    @Override
    public Order getOrder(String code) {
        try {
            this.connect();
            Order foundOrder = null;
            this.p = this.dbCon.prepareStatement("SELECT O.code, U.name, O.timestamp from Orders O LEFT JOIN Users U ON U.id == O.usr_id WHERE code=?");
            this.p.setString(1, code);
            this.rr = this.p.executeQuery();
            if (!this.rr.next()) {
                System.out.println("Tilausta ei löydy");
            } else {
//                PreparedStatement pp = this.dbCon.prepareStatement("SELECT name FROM Users WHERE id=?");
//                pp.setInt(1, rr.getInt("usr_id"));
//                ResultSet rrr = pp.executeQuery();
                foundOrder = new Order(this.rr.getString("code"), this.rr.getString("name"), this.rr.getString("timestamp"));
//                rrr.close();
//                pp.close();
            }
            //   p.close();
            //   rr.close();
            return foundOrder;
        } catch (SQLException e) {
            System.out.println("Yhteyttä tietokantaan ei löydy. Tilausta ei löydy." + e.getMessage());
            return null;
        } finally {
            this.closeConnections();
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
            this.p = this.dbCon.prepareStatement("SELECT E.workphase, E.description, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE code=?");
            this.p.setString(1, code);
            this.rr = this.p.executeQuery();
            // Create a Workphase object from sql data
            while (this.rr.next()) {
                WorkPhase event = new WorkPhase(this.rr.getString("timestamp"), this.rr.getString("workphase"), code, this.rr.getString("name"), this.rr.getString("description"));
                events.add(event);
            }
            //   p.close();
            //   rr.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnections();
        }
        return events;
    }

    @Override
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
        ArrayList<WorkPhase> events = new ArrayList<>();
        // First select the events with specific date and order them by date, then group by order code
        try {
            this.connect();
            this.p = this.dbCon.prepareStatement("SELECT * FROM (SELECT E.workphase, E.description, E.code, U.name, E.timestamp FROM Events E LEFT JOIN Users U ON U.id == E.usr_id WHERE date(E.timestamp)=? ORDER BY E.timestamp DESC) AS S GROUP BY S.code");
            this.p.setString(1, date);
            this.rr = this.p.executeQuery();
            // Create a Workphase object from sql data
            while (this.rr.next()) {
                WorkPhase event = new WorkPhase(this.rr.getString("timestamp"), this.rr.getString("workphase"), this.rr.getString("code"), this.rr.getString("name"), this.rr.getString("description"));
                events.add(event);
            }
            //   p.close();
            //  rr.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnections();
        }
        return events;
    }

    @Override
    public boolean addEvent(WorkPhase event) {
        try {
            this.connect();
            this.p = this.dbCon.prepareStatement("INSERT INTO Events (workphase, code, usr_id, description, timestamp) VALUES (?,?, (SELECT id FROM Users WHERE name=?), ?, ?)");
            this.p.setString(1, event.getWorkphase());
            this.p.setString(2, event.getCode());
            this.p.setString(3, event.getName());
            this.p.setString(4, event.getDescription());
            this.p.setString(5, event.getTimestamp());
            this.p.executeUpdate();
            this.dbCon.commit();
            // p.close();
            System.out.println("Työvaihe lisätty");
            return true;
        } catch (SQLException e) {
            System.out.println("Työvaihetta ei lisätty sql" + e.getMessage());
            return false;
        } finally {
            this.closeConnections();
        }
    }

    @Override
    public void removeAllDataFromDatabase() {
        try {
            this.connect();
            this.s = this.dbCon.createStatement();
            this.s.execute("PRAGMA foreign_keys = OFF");
            this.s.execute("DROP TABLE IF EXISTS Users");
            this.s.execute("DROP TABLE IF EXISTS Orders");
            this.s.execute("DROP TABLE IF EXISTS Events");
            this.s.execute("DROP TABLE IF EXISTS Users");
            this.dbCon.commit();
            //    s.close();
            System.out.println("Tietokanta tyhjennetty.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.closeConnections();
        }

    }
}
