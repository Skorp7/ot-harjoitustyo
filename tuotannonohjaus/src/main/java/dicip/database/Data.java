package dicip.database;

import dicip.domain.WorkPhase;
import dicip.domain.User;
import dicip.domain.Order;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Rajapinta tietokantojen käsittelyä varten
 */
public interface Data {

    // Database actions:
    /**
     * Tietokannan alustava metodi, laittaa tietokannan käyttövalmiiksi.
     *
     * @return onnistuiko alustus
     */
    boolean format();

    /**
     * Metodi tyhjentää tietokannan kokonaan, jonka jälkeen se pitää alustaa
     * uudelleen.
     */
    boolean removeAllDataFromDatabase();

    // User actions:
    /**
     * Hakee käyttäjän tietokannasta
     *
     * @param name käyttäjän antama syöte (nimi)
     * @return User-olio jolla on nimenä syötetty nimi tai 'null' jos käyttäjää
     * ei löydy.
     */
    User getUser(String name);

    /**
     * Lisää käyttäjän tietokantaan
     *
     * @param user annettu User-olio
     * @return true jos käyttäjän lisäys onnistui, muutoin false
     */
    boolean addUser(User user);

    /**
     * Poistaa käyttäjän tietokannasta
     *
     * @param user annettu User-olio
     * @return true jos käyttäjän poistaminen onnistui, muutoin false
     */
    public boolean removeUser(User user);

    public boolean changeUserStatus(User user, int status);

    public ArrayList<User> getAllUsers();

    // Order actions:
    /**
     * Lisää tilauksen tietokantaan
     *
     * @param order annettu Order-olio (tilaus)
     * @return true jos tilauksen lisäys onnistui, muutoin false
     */
    boolean addOrder(Order order);

    Order getOrder(String code);

    ArrayList<Order> getAllOrders();

    /**
     * Hakee tietyn tilauksen kaikki työvaiheet
     *
     * @param code annettu tilauskoodi
     * @return lista työvaiheista joihin liittyy annettu koodi
     */
    ArrayList<WorkPhase> getOrderInfo(String code);

    /**
     * Hakee tietyn päivän ajalta kaikki työvaiheet
     *
     * @param date annettu päivämäärä
     * @return lista työvaiheista joihin liittyy annettu päivämäärä
     */
    ArrayList<WorkPhase> getOrderInfoByDate(String date);

    ArrayList<WorkPhase> getOrderInfoByUser(User user);

    /**
     * Hakee uusien tilausten määrän per päivä
     *
     * @return HashMap joka sisältää arvoinaan päivämäärän ja sinä päivänä
     * luotujen uusien tilausten määrän
     */
    HashMap<String, Integer> getOrderCountByDate();

    // Event actions
    /**
     * Lisää työvaiheen tietokantaan
     *
     * @param event annettu WorkPhase-olio (työvaihe)
     * @return true jos työvaiheen lisäys onnistui, muutoin false
     */
    boolean addEvent(WorkPhase event);

}
