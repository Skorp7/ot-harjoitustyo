package domain;

import dicip.database.DataMap;
import dicip.database.DataSql;
import dicip.domain.Service;
import dicip.domain.User;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class ServiceTest {

    private Service service;
    private User user;

    public ServiceTest() {
        this.service = new Service(new DataMap());
    }

    @Before
    public void setUp() {
        this.service.addUser("Esko", 0);
        this.user = this.service.getUser("Esko");
    }

    @Test
    public void checkDatabaseReturnsTrue() {
        assertTrue(this.service.checkDatabase());
    }

    @Test
    public void dataBaseIsEmptyAfterRemovingAllData() {
        this.service.addOrder("T", this.user);
        this.service.removeAllDataFromDatabase();
        assertTrue(this.service.getUser("Esko") == null);
        assertTrue(this.service.getOrder("T") == null);
    }

    @Test
    public void adminExistsAfterCheckDatabase() {
        this.service.checkDatabase();
        assertNotEquals(null, this.service.getUser("admin"));
    }

    @Test
    public void userNameIsCorrect() {
        this.service.addUser("Kayttaja", 0);
        assertEquals("Kayttaja", this.service.getUser("Kayttaja").getName());
    }

    @Test
    public void userStatusIsCorrect() {
        this.service.addUser("Kayttaja", 0);
        assertEquals(0, this.service.getUser("Kayttaja").getStatus());
    }

    @Test
    public void loginReturnTrueIfUserExists() {
        this.service.addUser("LoginU", 0);
        assertTrue(this.service.login("LoginU"));
    }

    @Test
    public void loginReturnFalseIfUserDoesNotExist() {
        assertFalse(this.service.login("LoginUser"));
    }

    @Test
    public void loginReturnFalseIfUserHasNoRights() {
        this.service.addUser("Elli", 0);
        this.service.changeUserStatus("Elli", 99);
        assertFalse(this.service.login("Elli"));
    }

    @Test
    public void logOutLogsOut() {
        this.service.login("Esko");
        this.service.logOut();
        assertTrue(this.service.getLoggedInUser() == null);
    }

    @Test
    public void rightUserLoggedIn() {
        this.service.addUser("Eppu", 0);
        this.service.login("Eppu");
        this.service.addUser("Uuno", 0);
        this.service.login("Uuno");
        assertEquals("Uuno", this.service.getLoggedInUser().getName());
    }

    @Test
    public void userIsremovedIfHasNoEvents() {
        this.service.addUser("Ulla", 0);
        this.service.removeUser("Ulla");
        assertTrue(service.getUser("Ulla") == null);
    }

    @Test
    public void userIsNotremovedIfHasEvents() {
        this.service.addUser("Ulla", 0);
        this.service.addOrder("Kode", this.service.getUser("Ulla"));
        this.service.addEvent("työvaihe", "Kode", "desc", this.service.getUser("Ulla"));
        this.service.removeUser("Ulla");
        assertFalse(this.service.getUser("Ulla") == null);
    }

    @Test
    public void adminIsNotRemovedIfOnlyAdmin() {
        assertFalse(this.service.removeUser("admin"));
    }

    @Test
    public void adminIsRemovedIfThereIsOtherAdmin() {
        this.service.addUser("admin2", 1);
        assertTrue(this.service.removeUser("admin"));
    }

    @Test
    public void userRightsAreRemovedOnRemoveIfHasEvents() {
        this.service.addUser("Ulla", 0);
        this.service.addOrder("Kode", this.service.getUser("Ulla"));
        this.service.addEvent("työvaihe", "Kode", "desc", this.service.getUser("Ulla"));
        this.service.removeUser("Ulla");
        assertFalse(service.login("Ulla"));
    }

    @Test
    public void addUserFailureIfDouble() {
        this.service.addUser("Nimi", 0);
        assertFalse(this.service.addUser("Nimi", 1));
    }

    @Test
    public void addOrderFailureIfDouble() {
        this.service.addOrder("O", this.user);
        assertFalse(this.service.addOrder("O", this.user));
    }

    @Test
    public void addOrderFailureIfFalseUser() {
        User falseUser = new User("nimi", 0);
        assertFalse(this.service.addOrder("O", falseUser));
    }

    @Test
    public void addEventFailureIfFalseUser() {
        User falseUser = new User("nimifalse", 0);
        this.service.addOrder("code", this.user);
        assertFalse(this.service.addEvent("orkphase", "code", "descr", falseUser));
    }

    @Test
    public void addEventFailureIfFalseOrderCode() {
        assertFalse(this.service.addEvent("workphase", "falseCode", "descr", this.user));
    }

    @Test
    public void addedOrderExists() {
        this.service.addOrder("O", this.user);
        assertTrue(this.service.getOrder("O") != null);
    }

    @Test
    public void rightOrderInfoCount() {
        this.service.addOrder("Y", this.user);
        this.service.addEvent("workphase", "Y", "descr", this.user);
        this.service.addEvent("workphase2", "Y", "descr", this.user);
        assertEquals(3, this.service.getOrderInfo("Y").size());
    }

    @Test
    public void rightOrderInfoCountByDate() {
        this.service.removeAllDataFromDatabase();
        this.service.checkDatabase();
        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.toString().substring(0, 10);
        this.service.addUser("Iivo", 0);
        User userr = new User("Iivo", 0);
        this.service.addOrder("V", userr);
        this.service.addEvent("workphase", "V", "descr", userr);
        this.service.addEvent("workphase2", "V", "descr", userr);
        assertEquals(3, this.service.getOrderInfoByDate(modTimestamp).size());
    }
}
