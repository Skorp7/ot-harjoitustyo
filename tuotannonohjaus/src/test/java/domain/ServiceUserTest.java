package domain;

import dicip.database.DataMap;
import dicip.domain.Service;
import dicip.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class ServiceUserTest {

    private final Service service;
    private User user;

    public ServiceUserTest() {
        this.service = new Service(new DataMap());
    }

    @Before
    public void setUp() {
        this.service.addUser("Esko", 0);
        this.user = this.service.getUser("Esko");
    }

    @Test
    public void adminExistsAfterCheckDatabase() {
        this.service.checkDatabase();
        assertNotEquals(null, this.service.getUser("admin"));
    }

    @Test
    public void adminCountIsOneAtBeginning() {
        assertEquals(1, this.service.countOfAdmins());
    }

    @Test
    public void adminCountIncreases() {
        this.service.addUser("Taija", 1);
        this.service.addUser("Kaija", 1);
        assertEquals(3, this.service.countOfAdmins());
    }

    @Test
    public void regularUserCountIsZeroAtBeginning() {
        this.service.removeAllDataFromDatabase();
        this.service.checkDatabase();
        assertEquals(0, this.service.countOfRegularUsers());
    }

    @Test
    public void regularUserCountIncreases() {
        this.service.addUser("Taija", 0);
        assertEquals(2, this.service.countOfRegularUsers());
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
    public void changeStatusFalseIfUserNotExists() {
        assertFalse(this.service.changeUserStatus("Nobody", 0));
    }

    @Test
    public void changeStatusCorrect() {
        this.service.changeUserStatus("Esko", 1);
        assertEquals(1, this.service.getUser("Esko").getStatus());
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
    public void userIsNotRemovedIfNotExists() {
        assertFalse(this.service.removeUser("Nobody"));
    }

    @Test
    public void adminStatusIsNotChangedIfOnlyOneAdmin() {
        assertFalse(this.service.changeUserStatus("admin", 0));
    }

    @Test
    public void addUserFailureIfDouble() {
        this.service.addUser("Nimi", 0);
        assertFalse(this.service.addUser("Nimi", 1));
    }

}
