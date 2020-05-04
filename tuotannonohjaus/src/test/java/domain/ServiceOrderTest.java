package domain;

import dicip.database.DataMap;
import dicip.domain.Service;
import dicip.domain.User;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class ServiceOrderTest {

    private final Service service;
    private User user;

    public ServiceOrderTest() {
        this.service = new Service(new DataMap());
    }

    @Before
    public void setUp() {
        this.service.addUser("Esko", 0);
        this.user = this.service.getUser("Esko");
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
    public void addedOrderExists() {
        this.service.addOrder("O", this.user);
        assertTrue(this.service.getOrder("O") != null);
    }

    @Test
    public void rightOrderAmountForTodayThreeOrders() {
        LocalDate date = LocalDate.now();
        this.service.addOrder("TodaysOrder", this.user);
        this.service.addOrder("TodaysOrder2", this.user);
        this.service.addOrder("TodaysOrder3", this.user);
        int amount = this.service.getOrderAmount30Days().get(date);
        assertEquals(3, amount);
    }

    @Test
    public void rightOrderAmountForTodayZeroOrders() {
        LocalDate date = LocalDate.now();
        int amount = this.service.getOrderAmount30Days().get(date);
        assertEquals(0, amount);
    }

}
