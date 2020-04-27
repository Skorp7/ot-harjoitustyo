package dicip.database;

import dicip.domain.WorkPhase;
import dicip.domain.User;
import dicip.domain.Order;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Interface for the database
 */
public interface Data {

    // Database actions:
    /**
     * Format the database to make it ready to use. Returns false if already
     * formatted.
     *
     * @return true if formatted, else false
     */
    boolean format();

    /**
     * Clear the database. After this it has to be formatted again.
     *
     * @return true if done, else false
     */
    boolean removeAllDataFromDatabase();

    // User actions:
    /**
     * Get user from database
     *
     * @param name given name
     * @return Object User or 'null' if user was not found.
     */
    User getUser(String name);

    /**
     * Add user to the database
     *
     * @param user given User
     * @return true if done, else false
     */
    boolean addUser(User user);

    /**
     * Remove user from the database
     *
     * @param user given User
     * @return true if done, else false
     */
    public boolean removeUser(User user);

    /**
     * Change user status
     *
     * @param user given User
     * @param status given status
     * @return true if done, else false
     */
    public boolean changeUserStatus(User user, int status);

    /**
     * Get all users as list
     *
     * @return a list of all users as User-objects
     */
    public ArrayList<User> getAllUsers();

    // Order actions:
    /**
     * Add order to the database
     *
     * @param order given Order
     * @return true if done, else false
     */
    boolean addOrder(Order order);

    /**
     * Get user from database
     *
     * @param code given code
     * @return Order-object or 'null' if order was not found.
     */
    Order getOrder(String code);

    /**
     * Get all orders as a list
     *
     * @return a list of Order-objects
     */
    ArrayList<Order> getAllOrders();

    /**
     * Get all work phases for a specific order
     *
     * @param code given code
     * @return a list of WorkPhase-objects with the given code
     */
    ArrayList<WorkPhase> getOrderInfo(String code);

    /**
     * Get all the work phases with a specific date
     *
     * @param date given date
     * @return a list of WorkPhase-objects with the given date
     */
    ArrayList<WorkPhase> getOrderInfoByDate(String date);

    /**
     * Get all the work phases of the specific user
     *
     * @param user given User
     * @return a list of the WorkPhase-objects done by given User
     */
    ArrayList<WorkPhase> getOrderInfoByUser(User user);

    /**
     * Get the count of the new orders by date.
     *
     * @return HashMap which includes date as the 'key' and the count of new
     * orders for that date as the 'value'.
     */
    HashMap<String, Integer> getOrderCountByDate();

    // Event actions
    /**
     * Add a work phase to the database
     *
     * @param event given WorkPhase
     * @return true if done, else false
     */
    boolean addEvent(WorkPhase event);

}
