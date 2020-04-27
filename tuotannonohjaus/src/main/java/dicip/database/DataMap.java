package dicip.database;

/**
 *
 * @author sakorpi
 */
/**
 *
 * This class is only for testing purposes. To test the application
 * logic without the permanent data saving into database.
 */
import dicip.domain.Order;
import dicip.domain.User;
import dicip.domain.WorkPhase;
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
        Collections.reverse(phaseList);
        return phaseList;
    }
//
//    @Override
//    public ArrayList<WorkPhase> getOrderInfoByDateGrouped(String date) {
//        ArrayList<WorkPhase> phaseList = new ArrayList<>();
//        ArrayList<WorkPhase> modPhaseList = new ArrayList<>();
//        // Select events by date
//        for (WorkPhase e : this.events) {
//            if (e.getTimestamp().contains(date)) {
//                phaseList.add(e);
//            }
//        }
//        // Events are saved in ascending order, so reverse it first and then 
//        // save codes into a list and check that every WP is added only once
//        
//        ArrayList<String> codeList = new ArrayList<>();
//        for (WorkPhase e : phaseList) {
//            if (!codeList.contains(e.getCode())) {
//                modPhaseList.add(e);
//                codeList.add(e.getCode());
//            }
//        }
//        return modPhaseList;
//    }

    @Override
    public boolean removeAllDataFromDatabase() {
        orders.clear();
        users.clear();
        events.clear();
        return orders.isEmpty() && users.isEmpty() && events.isEmpty();
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        return this.orders;
    }

    @Override
    public HashMap<String, Integer> getOrderCountByDate() {
        HashMap<String, Integer> dateAndCount = new HashMap<>();
        // First add every date (cutted to format yyyy-mm-dd) once
        for (Order o : this.orders) {
            String modTimeStamp = o.getTimeStamp().substring(0, 10);
            dateAndCount.putIfAbsent(modTimeStamp, 0);
        }
        // Then calculate how many times each date is presented and store sum into value
        for (Order o : this.orders) {
            String modTimeStamp = o.getTimeStamp().substring(0, 10);
            dateAndCount.computeIfPresent(modTimeStamp, (key, value) -> value + 1);
        }
        return dateAndCount;
    }

    @Override
    public boolean changeUserStatus(User user, int status) {
        Boolean done = false;
        for (User u : this.users) {
            if (u.equals(user)) {
                u.setStatus(status);
                done = true;
            }
        }
        return done;
    }

    @Override
    public boolean removeUser(User user) {
        Boolean done = false;
        int i = this.users.indexOf(user);
        this.users.remove(i);
        return done;
    }

    @Override
    public ArrayList<WorkPhase> getOrderInfoByUser(User user) {
        ArrayList<WorkPhase> workPhases = new ArrayList<>();
        for (WorkPhase wp : this.events) {
            if (wp.getName().equals(user.getName())) {
                workPhases.add(wp);
            }
        }
        return workPhases;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return this.users;
    }

}
