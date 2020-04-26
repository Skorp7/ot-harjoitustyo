package dicip.domain;

import dicip.database.Data;
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
/**
 *
 * Luokka vastaa sovelluslogiikasta
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
     * Alustaa tietokannan mikäli ei ole vielä alustettu
     *
     * @return true jos alustus tuli tehtyä, muutoin false
     */
    // try to format database, return false if can not format (most likely already formated)
    public boolean checkDatabase() {
        return this.database.format();
    }

    public boolean removeAllDataFromDatabase() {
        this.database.removeAllDataFromDatabase();
        return true;
    }

    // User functions:
    /**
     * Kirjaa käyttäjän sisään, jos käyttäjä löytyy
     *
     * @param name syötetty nimi
     * @return true jos käyttäjä löytyi, muutoin false
     */
    // Return true if user exists and has right status to log in, then store it to 'loggedIn'
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
     * Kirjaa sisäänkirjautuneen olevan käyttäjän ulos
     */
    public void logOut() {
        this.loggedIn = null;
    }

    /**
     * Lisää käyttäjän mikäli samannimistä käyttäjää ei vielä ole
     *
     * @param name syötetty nimi
     * @param status syötetty status-arvo
     * @return true jos käyttäjän lisäys onnistui, muutoin false
     */
    public boolean addUser(String name, Integer status) {
        if (this.database.getUser(name) != null) {
            return false;
        }
        User user = new User(name, status);
        return this.database.addUser(user);
    }

    public boolean removeUserLogInRights(User user) {
        return this.database.changeUserStatus(user, 99);
    }

    // If the user already has some events done, don't remove it but remove log in rights.
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

    public User getUser(String name) {
        return this.database.getUser(name);
    }

    public boolean changeUserStatus(String name, int status) {
        if (this.getUser(name) != null) {
            User user = this.getUser(name);
            return this.database.changeUserStatus(user, status);
        }
        return false;
    }

    // Order functions:
    /**
     * Lisää tilauksen mikäli tilausta samalla koodilla ei vielä löydy Luo
     * samalla aikaleiman tilaukselle ja sisäänkirjausta kuvaavan tapahtuman
     * kyseiselle tilaukselle
     *
     * @param code syötetty koodi
     * @param user annettu käyttäjä
     * @return true jos tilauksen lisäys onnistui, muutoin false
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

    public User getLoggedInUser() {
        return this.loggedIn;
    }

    // Event functions:
    /**
     * Lisää työvaiheen ja luo sille samalla aikaleiman järjestelmän aikaan
     * perustuen
     *
     * @param code syötetty koodi
     * @param user annettu käyttäjä
     * @param descr annettu kuvaus
     * @param workphase annettu työvaiheen nimi
     * @return true jos työvaiheen lisäys onnistui, muutoin false
     */
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

    /**
     * Hakee tietyn tilauksen kaikki työvaiheet
     *
     * @param code annettu tilauskoodi
     * @return lista työvaiheista joihin liittyy annettu koodi
     */
    // Get all Events for an asked Order as list
    public ObservableList<WorkPhase> getOrderInfo(String code) {
        ObservableList<WorkPhase> list = FXCollections.observableArrayList();
        ArrayList<WorkPhase> events = this.database.getOrderInfo(code);
        list.addAll(events);
        return list;
    }

    /**
     * Hakee tietyn päivän ajalta kaikki työvaiheet
     *
     * @param date annettu päivämäärä
     * @return lista työvaiheista joihin liittyy annettu päivämäärä
     */
    // Get all newest Events for an asked date as list
    public ArrayList<WorkPhase> getOrderInfoByDate(String date) {
        ArrayList<WorkPhase> events = this.database.getOrderInfoByDate(date);
        return events;
    }

    /**
     * Hakee tietyn päivän ajalta kaikkien käsiteltyjen tilausten viimeisimmät
     * työvaiheet
     *
     * @param date annettu päivämäärä
     * @return lista työvaiheista
     */
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
    /**
     * Hakee uusien tilausten määrän per päivä edellisten 30 päivän ajalta
     * perustuen järjestelmän paikalliseen aikaan
     *
     * @return HashMap joka sisältää arvoinaan päivämäärän ja sinä päivänä
     * luotujen uusien tilausten määrän
     */
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
