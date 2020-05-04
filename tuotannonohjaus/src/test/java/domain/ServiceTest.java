package domain;

import dicip.database.DataMap;
import dicip.domain.Order;
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

    private final Service service;
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

    @Test
    public void mostEventsUserIsRightUser() {
        this.service.removeAllDataFromDatabase();
        this.service.checkDatabase();
        this.service.addUser("Eetu", 0);
        this.service.addUser("Aivo", 0);
        User userr = new User("Aivo", 0);
        User user1 = new User("Eetu", 0);
        this.service.addOrder("V", userr);
        this.service.addEvent("workphase", "V", "descr", userr);
        this.service.addEvent("workphase2", "V", "descr", userr);
        this.service.addEvent("workphase2", "V", "descr", user1);
        this.service.addEvent("workphase2", "V", "descr", user1);
        assertEquals("Aivo", this.service.getMaxEventCountUser().getName());
    }

    @Test
    public void mostEventsUserIsChosenEvenEventCountIsZeroAfterFormat() {
        this.service.removeAllDataFromDatabase();
        this.service.checkDatabase();
        assertEquals("admin", this.service.getMaxEventCountUser().getName());
    }

    @Test
    public void calculateProductionTimeOfNonExistingOrderReturnsMinusOne() {
        Order order = new Order("koodi", "Esko", "2020-02-22");
        assertEquals(-1, this.service.calculateProductionTime(order));
    }

    @Test
    public void calculateProductionTimeIsZeroWhenTodaysOrderIsReadyToday() {
        Order order = new Order("koodi", "Esko", "2020-02-22");
        this.service.addOrder("koodi", this.user);
        this.service.addEvent("Uloskirjaus", "koodi", "descr", this.user);
        assertEquals(0.0, this.service.calculateProductionTime(order), 0.5);
    }

    @Test
    public void productionMedianValueIsZeroWhenTodaysEvenCountOrdersAreReadyToday() {
        this.service.addOrder("TodaysOrder", this.user);
        this.service.addOrder("TodaysOrder2", this.user);
        this.service.addEvent("Sisäänkirjaus", "TodaysOrder", "", this.user);
        this.service.addEvent("Uloskirjaus", "TodaysOrder", "", this.user);
        this.service.addEvent("Sisäänkirjaus", "TodaysOrder2", "", this.user);
        this.service.addEvent("Uloskirjaus", "TodaysOrder2", "", this.user);
        assertEquals(0.0, this.service.getMedianOfProductionTime(), 0.5);
    }

    @Test
    public void productionMedianValueIsMinusOneWhenNoOrdersAreReady() {
        this.service.addOrder("TodaysOrder", this.user);
        this.service.addOrder("TodaysOrder2", this.user);
        this.service.addEvent("Sisäänkirjaus", "TodaysOrder", "", this.user);
        this.service.addEvent("Mallien valmistus", "TodaysOrder", "", this.user);
        this.service.addEvent("Sisäänkirjaus", "TodaysOrder2", "", this.user);
        this.service.addEvent("Kuivaus", "TodaysOrder2", "", this.user);
        assertEquals(-1.0, this.service.getMedianOfProductionTime(), 0.5);
    }

    @Test
    public void productionMedianValueIsMinusOneWhenNoOrdersAreFound() {
        this.service.removeAllDataFromDatabase();
        this.service.checkDatabase();
        assertEquals(-1.0, this.service.getMedianOfProductionTime(), 0.5);
    }

    @Test
    public void productionMedianValueIsZeroWhenTodaysOddCountOrdersAreReadyToday() {
        this.service.addOrder("TodaysOrder", this.user);
        this.service.addEvent("Sisäänkirjaus", "TodaysOrder", "", this.user);
        this.service.addEvent("Uloskirjaus", "TodaysOrder", "", this.user);
        assertEquals(0.0, this.service.getMedianOfProductionTime(), 0.5);
    }

    @Test
    public void getLastEventFirstInGetOrderInfoByDateGrouped() {
        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.toString().substring(0, 10);
        this.service.addOrder("X", this.user);
        this.service.addOrder("Y", this.user);
        this.service.addEvent("työvaihe1x", "X", "desc", this.user);
        this.service.addEvent("työvaihe2x", "X", "desc", this.user);
        this.service.addEvent("työvaihe1y", "Y", "desc", this.user);
        assertEquals("työvaihe1y", this.service.getOrderInfoByDateGrouped(modTimestamp).get(0).getWorkphase());
    }

    @Test
    public void getOnlyTheLastEventsInGetOrderInfoByDateGrouped() {
        LocalDateTime timestamp = LocalDateTime.now();
        String modTimestamp = timestamp.toString().substring(0, 10);
        this.service.addOrder("X", this.user);
        this.service.addOrder("Y", this.user);
        this.service.addEvent("työvaihe1x", "X", "desc", this.user);
        this.service.addEvent("työvaihe2x", "X", "desc", this.user);
        this.service.addEvent("työvaihe1y", "Y", "desc", this.user);
        this.service.addEvent("työvaihe3x", "X", "desc", this.user);
        assertEquals(2, this.service.getOrderInfoByDateGrouped(modTimestamp).size());
    }

}
