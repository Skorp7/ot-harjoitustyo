package database;

import dicip.database.*;
import dicip.domain.Order;
import dicip.domain.User;
import dicip.domain.WorkPhase;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class DataTest {

    Data databTest;

    public DataTest() {
        // Change the database here to "new DataMap()" if you don't want to test with SQL
        // This test uses it's own SQL-database file testi.db and does not mess up the file used by real app.
         this.databTest = new DataSql("jdbc:sqlite:testi.db");
        //this.databTest = new DataMap();
        this.databTest.format();
    }

    @Before
    public void setUp() {
        this.databTest.addUser(new User("TestiKayttaja", 0));
        this.databTest.addOrder(new Order("P", "TestiKayttaja", "2020-03-06"));
    }

    @After
    public void tearDown() {
        this.databTest.removeAllDataFromDatabase();
    }

    @Test
    public void adminExistsAtBeginning() {
        assertTrue(this.databTest.getUser("admin") != null);
    }
    
    @Test
    public void notExistingUserReturnsNull() {
        assertEquals(null, this.databTest.getUser("olematon"));
    }
    
    @Test
    public void notExistingOrderReturnsNull() {
        assertEquals(null, this.databTest.getOrder("olematon"));
    }

    @Test
    public void statusIsCorrect() {
        this.databTest.addUser(new User("TestiKayttaja2", 0));
        assertTrue(this.databTest.getUser("TestiKayttaja2").getStatus() == 0);
    }

    @Test
    public void addedOrderExists() {
        this.databTest.addOrder(new Order("Koodi", "TestiKayttaja", "2020-04-06"));
        assertNotEquals(null, this.databTest.getOrder("Koodi"));
    }
    
    @Test
    public void addedOrderHasRightUserName() {
        this.databTest.addOrder(new Order("Koodi2", "TestiKayttaja", "2020-04-06"));
        assertEquals("TestiKayttaja", this.databTest.getOrder("Koodi2").getUserName());
    }

    @Test
    public void addedOrderHasRightTimestamp() {
        this.databTest.addOrder(new Order("Koodi3", "TestiKayttaja", "2020-04-06"));
        assertEquals("2020-04-06", this.databTest.getOrder("Koodi3").getTimeStamp());
    }

    @Test
    public void addedOrderHasRightCode() {
        this.databTest.addOrder(new Order("Koodi4", "TestiKayttaja", "2020-04-06"));
        assertEquals("Koodi4", this.databTest.getOrder("Koodi4").getCode());
    }
    
    @Test
    public void returnLastWorkPhaseWhenGetEventsByDate() {
        WorkPhase wp1 = new WorkPhase("2020-04-08 12:00:00", "Sisäänkirjaus", "P", "TestiKayttaja", "kuvaus");
        WorkPhase wp2 = new WorkPhase("2020-04-08 12:01:00", "Työvaihe", "P", "TestiKayttaja", "kuvaus");
        WorkPhase wp3 = new WorkPhase("2020-04-08 12:04:00", "Uloskirjaus", "P", "TestiKayttaja", "kuvaus");
        this.databTest.addEvent(wp1);
        this.databTest.addEvent(wp2);
        this.databTest.addEvent(wp3);
        ArrayList<WorkPhase> result = this.databTest.getOrderInfoByDate("2020-04-08");
        assertEquals("Uloskirjaus", result.get(0).getWorkphase());
    }
    
    @Test
    public void returnRightAmountOfEventsWhenGetOrderInfo() {
        Order order = new Order("H", "TestiKayttaja", "2020-04-08 11:00:00");
        this.databTest.addOrder(order);
        WorkPhase wp1 = new WorkPhase("2020-04-08 12:00:00", "Sisäänkirjaus", "H", "TestiKayttaja", "kuvaus");
        WorkPhase wp2 = new WorkPhase("2020-04-08 12:01:00", "Työvaihe", "H", "TestiKayttaja", "kuvaus");
        WorkPhase wp3 = new WorkPhase("2020-04-08 12:04:00", "Uloskirjaus", "H", "TestiKayttaja", "kuvaus");
        this.databTest.addEvent(wp1);
        this.databTest.addEvent(wp2);
        this.databTest.addEvent(wp3);
        ArrayList<WorkPhase> result = this.databTest.getOrderInfo("H");
        assertTrue(result.size()==3);
    }
    
    @Test
    public void addedEventExists() {
        Boolean found = false;
        WorkPhase wp1 = new WorkPhase("2020-04-08 12:00:00", "Sisäänkirjaus", "P", "TestiKayttaja", "kuvaus");
        this.databTest.addEvent(wp1);
        ArrayList<WorkPhase> result = this.databTest.getOrderInfo("P");
        for (WorkPhase wp : result) {
            if (wp.getCode().equals("P")) {
                found = true;
            }
        }
        assertTrue(found);
    }
    
    @Test
    public void getRightOrderCountByDay() {
        String day = "2020-04-14";
        ArrayList<Integer> foundCountForToday = new ArrayList<>();
        Order order = new Order("H", "TestiKayttaja", "2020-04-14 11:00:00");
        Order order2 = new Order("T", "TestiKayttaja", "2020-04-14 05:00:00");
        WorkPhase wp = new WorkPhase("2020-04-14 12:00:00", "Sisäänkirjaus", "T", "TestiKayttaja", "kuvaus");
        this.databTest.addEvent(wp);
        this.databTest.addOrder(order);
        this.databTest.addOrder(order2);
        HashMap<String, Integer> orderCounts = this.databTest.getOrderCountByDate();
        orderCounts.entrySet().stream().forEach(pair -> {
            if (pair.getKey().contains(day)) {
               foundCountForToday.add(pair.getValue());
            }
        });
        int foundCount = foundCountForToday.get(0);
        assertEquals(2, foundCount);
    }
    
    @Test
    public void getZeroOrderCountByZeroOrdersByDay() {
        String day = "2020-04-14";
        ArrayList<Integer> foundCountForToday = new ArrayList<>();
        HashMap<String, Integer> orderCounts = this.databTest.getOrderCountByDate();
        orderCounts.entrySet().stream().forEach(pair -> {
            if (pair.getKey().contains(day)) {
               foundCountForToday.add(pair.getValue());
            }
        });
        if (foundCountForToday.isEmpty()) {
            foundCountForToday.add(0);
        }
        int foundCount = foundCountForToday.get(0);
        assertEquals(0, foundCount);
    }
    
    @Test
    public void getRightAmountOfOrders() {
        Order order = new Order("H", "TestiKayttaja", "2020-04-14 11:00:00");
        Order order2 = new Order("T", "TestiKayttaja", "2020-04-14 05:00:00");
        Order order3 = new Order("T2", "TestiKayttaja", "2020-04-14 05:33:00");
        WorkPhase wp = new WorkPhase("2020-04-14 12:00:00", "Sisäänkirjaus", "T", "TestiKayttaja", "kuvaus");
        this.databTest.removeAllDataFromDatabase();
        this.databTest.format();
        this.databTest.addEvent(wp);
        this.databTest.addOrder(order);
        this.databTest.addOrder(order2);
        this.databTest.addOrder(order3);
        // The test class already has added one order, so insted of 3, we expect 4
        assertEquals(3, this.databTest.getAllOrders().size());
    }
}
    
