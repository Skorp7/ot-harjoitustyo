package dicip.domain;

import dicip.database.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Class represents the application logic
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

    /**
     * Try to format database (ready to use state), return false if can not
     * format (most likely already formatted)
     *
     * @return true if formatted now, else false
     */
    public boolean checkDatabase() {
        return this.database.format();
    }

    /**
     *
     * Remove all data from database
     *
     * @return true when done, else false
     */
    public boolean removeAllDataFromDatabase() {
        this.database.removeAllDataFromDatabase();
        return true;
    }

    // User functions:
    /**
     * Log user in if user exists and has rights to log in (status 0 or 1)
     *
     * @param name given name
     * @return true if done, else false
     */
    // Return true if login is done, then store it to 'loggedIn'
    public boolean login(String name) {
        if (this.database.getUser(name) != null) {
            User user = this.database.getUser(name);
            if (user.getStatus() == 1 || user.getStatus() == 0) {
                this.loggedIn = this.getUser(name);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Logs the logged in user out
     */
    public void logOut() {
        this.loggedIn = null;
    }

    /**
     * Add user if there is no user with the same name
     *
     * @param name given name
     * @param status given status
     * @return true if done, else false
     */
    // The right length of the name is checked in UI, for getting the right error-messages
    public boolean addUser(String name, Integer status) {
        if (this.database.getUser(name) != null) {
            return false;
        }
        User user = new User(name, status);
        return this.database.addUser(user);
    }

    /**
     * Remove user login rights by changing the status to '99'
     *
     * @param user given user
     * @return true if done, else false
     */
    public boolean removeUserLogInRights(User user) {
        return this.database.changeUserStatus(user, 99);
    }

    /**
     * Remove user. If the user already has some events done, don't remove it
     * but remove the login rights.
     *
     * @param name given name
     * @return true if formatted now, else false
     */
    public boolean removeUser(String name) {
        Boolean succeed = false;
        if (this.getUser(name) != null) {
            User user = this.getUser(name);
            int count = this.countOfAdmins();
            // Make sure that all admin-right-users are not removed
            if (user.getStatus() == 0 && this.database.getOrderInfoByUser(user).isEmpty()
                    || user.getStatus() == 1 && this.database.getOrderInfoByUser(user).isEmpty() && count > 1) {
                this.database.removeUser(user);
                succeed = true;
            } else if (user.getStatus() == 0 || user.getStatus() == 1 && count > 1) {
                this.removeUserLogInRights(user);
                succeed = true;
            }
        }
        return succeed;
    }

    /**
     * Count users with admin rights
     *
     * @return count count of admins
     */
    public int countOfAdmins() {
        ArrayList<User> users = this.database.getAllUsers();
        int count = 0;
        for (User u : users) {
            if (u.getStatus() == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count users with regular rights
     *
     * @return count count of regular users
     */
    public int countOfRegularUsers() {
        ArrayList<User> users = this.database.getAllUsers();
        int count = 0;
        for (User u : users) {
            if (u.getStatus() == 0) {
                count++;
            }
        }
        return count;
    }

    public User getUser(String name) {
        return this.database.getUser(name);
    }

    /**
     * Change user status, check first is user exists. Also check that the last
     * user with admin rights is not changed
     *
     * @param name given name
     * @param status given status
     * @return count count of admins
     */
    public boolean changeUserStatus(String name, int status) {
        if (this.getUser(name) != null) {
            User user = this.getUser(name);
            if (user.getStatus() == 1 && this.countOfAdmins() <= 1) {
                return false;
            } else {
                return this.database.changeUserStatus(user, status);
            }
        }
        return false;
    }

    // Order functions:
    /**
     * Add an order if there is no order with the same code. Creates a time
     * stamp for the order creation moment and creates a work phase for
     * registration for the created order.
     *
     * @param code given code
     * @param user given user
     * @return true if done, else false
     */
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

    /**
     * Get the logged in user if exists. If nobody is logged in, then return
     * null.
     *
     * @return user or null
     */
    public User getLoggedInUser() {
        return this.loggedIn;
    }

    // Event functions:
    /**
     * Add a work phase and creates the time stamp for it, based on the system
     * local time. Also checks that the given code and user exist.
     *
     * @param code given code
     * @param user given user
     * @param descr given description
     * @param workphase given name of the work phase
     * @return true if done, else false
     */
    public boolean addEvent(String workphase, String code, String descr, User user) {
        if (this.database.getOrder(code) == null || this.database.getUser(user.getName()) == null) {
            return false;
        }

        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.format(this.formatter);
        WorkPhase event = new WorkPhase(modTimestamp, workphase, code, user.getName(), descr);
        return this.database.addEvent(event);
    }

    /**
     * Get all the work phases for a specific order.
     *
     * @param code given code
     * @return a list of the work phases with the given code
     */
    public ObservableList<WorkPhase> getOrderInfo(String code) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfo(code);
        list.addAll(events);
        return list;
    }

    /**
     * Get all the work phases with a specific date
     *
     * @param date given date
     * @return a list of the work phases with the given date
     */
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
        ArrayList<WorkPhase> events = this.database.getOrderInfoByDate(date);
        return events;
    }

    /**
     * Get all the latest work phases of the handled orders in the specific date
     *
     * @param date given date
     * @return a list of latest work phases
     */
    // Group WorkPhases by Order code and show the latest WorkPhases
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
    /**
     * Get the count of the new orders by date, during the last 30 days. Based
     * on the system local time.
     *
     * @return HashMap which includes date as the 'key' and the count of new
     * orders for that date as the 'value'.
     */
    // Create a map which contains order amount per day, for 30days
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

    /**
     * Get the median value of production time of all orders. Calculates the
     * median value of production time of all orders ever made. Uses the
     * registration time stamp and log out time stamp to calculate.
     *
     * @return double
     */
    public double getMedianOfProductionTime() {
        ArrayList<Long> durations = new ArrayList<>();
        ArrayList<Order> orders = this.database.getAllOrders();
        if (orders.isEmpty()) {
            return -1;
        }
        orders.stream().map((o) -> this.calculateProductionTime(o)).filter((duration) -> (duration >= 0)).forEachOrdered((duration) -> {
            durations.add(duration);
        });
        // Find out the value in the middle. If found two in the middle, take the average of those.
        Collections.sort(durations);
        int halfwayInd = durations.size() / 2;
        double result = -1;
        if (!durations.isEmpty() && durations.size() % 2 == 0) {
            result = (((double) durations.get(halfwayInd) + (double) durations.get(halfwayInd - 1)) / 2);
        } else if (!durations.isEmpty()) {
            result = durations.get(halfwayInd);
        }
        return result;
    }

    /**
     * Get all the events by users
     *
     *
     * @return HashMap which contains User as the key and all the events as
     * ArrayList as the value
     */
    public HashMap<User, ArrayList<WorkPhase>> getEventsByUser() {
        HashMap<User, ArrayList<WorkPhase>> usersEvents = new HashMap<>();
        ArrayList<User> users = this.database.getAllUsers();

        for (User u : users) {
            usersEvents.put(u, this.database.getOrderInfoByUser(u));
        }

        return usersEvents;
    }

    /**
     * Get the User who has most events done. If multiple users has as many
     * events done, it picks up randomly only one of them.
     *
     * @return user
     */
    public User getMaxEventCountUser() {
        HashMap<User, Integer> eventCountByUser = new HashMap<>();
        HashMap<User, ArrayList<WorkPhase>> eventsByUser = this.getEventsByUser();
        eventsByUser.entrySet().forEach(pair -> eventCountByUser.put(pair.getKey(), pair.getValue().size()));

        HashMap<User, Integer> eventCountByUserWinner = new HashMap<>();
        User user = Collections.max(eventCountByUser.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

        return user;
    }

    /**
     * Calculate the production time of an order. Uses the registration time
     * stamp and log out time stamp to calculate.
     *
     * @param order given Order
     * @return long production time
     */
    public long calculateProductionTime(Order order) {
        ArrayList<WorkPhase> events = this.database.getOrderInfo(order.getCode());
        if (events.isEmpty()) {
            return -1;
        }
        String logoutTime = "";

        // Find out log out time if exists
        for (WorkPhase wp : events) {
            if (wp.getWorkphase().equals("Uloskirjaus")) {
                logoutTime = wp.getTimestamp();
            }
        }
        if (logoutTime.equals("")) {
            return -1;
        }

        LocalDateTime regTime = LocalDateTime.parse(events.get(0).getTimestamp(), this.formatter);
        LocalDateTime outTime = LocalDateTime.parse(logoutTime, this.formatter);
        long days = ChronoUnit.DAYS.between(regTime, outTime);

        return days;
    }
}
