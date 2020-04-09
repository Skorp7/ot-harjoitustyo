// This class is for testing the app logic
package database;

/**
 *
 * @author sakorpi
 */
import domain.Order;
import domain.User;
import domain.WorkPhase;
import java.util.*;

public class DataMap implements Data {

    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private ArrayList<WorkPhase> events;

    public DataMap() {
        this.users = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.events = new ArrayList<>();
        this.users.add(new User("admin", 1));
    }

    @Override
    public boolean format() {
        this.users.clear();
        this.orders.clear();
        this.events.clear();
        this.users.add(new User("admin", 1));
        return true;
    }

    @Override
    public User getUser(String name) {
        for (User u : this.users) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        this.users.add(user);
        return true;
    }

    @Override
    public boolean removeUser(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addOrder(Order order) {
        this.orders.add(order);
        return true;
    }

    @Override
    public Order getOrder(String code) {
        for (Order o : this.orders) {
            if (o.getCode().equals(code)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public ArrayList<WorkPhase> getOrderInfo(String code) {
        ArrayList<WorkPhase> phaseList = new ArrayList<>();
        Order order = this.getOrder(code);
        for (WorkPhase e : this.events) {
            if (e.getCode().equals(code)) {
                phaseList.add(e);
            }
        }
        return phaseList;
    }

    @Override
    public boolean addEvent(WorkPhase event) {
        this.events.add(event);
        return true;
    }

    @Override
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
        ArrayList<WorkPhase> phaseList = new ArrayList<>();
        ArrayList<WorkPhase> modPhaseList = new ArrayList<>();
        // Select events by date
        for (WorkPhase e : this.events) {
            if (e.getTimestamp().contains(date)) {
                phaseList.add(e);
            }
        }
        // Events are saved in ascending order, so reverse it first and then 
        // save codes into a list and check that every WP is added only once
        Collections.reverse(phaseList);
        ArrayList<String> codeList = new ArrayList<>();
        for (WorkPhase e : phaseList) {
            if (!codeList.contains(e.getCode())) {
                modPhaseList.add(e);
                codeList.add(e.getCode());
            }
        }
        return modPhaseList;
    }


    @Override
    public void removeAllDataFromDatabase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
