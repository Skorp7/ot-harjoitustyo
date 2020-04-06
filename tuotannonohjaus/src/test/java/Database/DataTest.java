package Database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import database.DataMap;
import database.Data;
import domain.Order;
import domain.User;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class DataTest {

    Data databTest;

    public DataTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // Change here if you want to test the real SQL-database or a fake database "DataMap" which is based on ArrayLists.
        this.databTest = new DataMap();
        this.databTest.format();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }

    @Test
    public void adminExistsAtBeginning() throws SQLException {
        assertTrue(this.databTest.getUser("admin") != null);
    }

    @Test
    public void statusIsCorrect() throws SQLException {
        this.databTest.addUser(new User("TestiKayttaja", 0));
        assertTrue(this.databTest.getUser("TestiKayttaja").getStatus() == 0);
    }

    @Test
    public void addedOrderExists() throws SQLException {
        this.databTest.addOrder(new Order("Koodi", "TestiKayttaja", "2020-04-06"));
        assertNotEquals(null, this.databTest.getOrder("Koodi"));
    }
    
    @Test
    public void addedOrderHasRightUserName() throws SQLException {
        this.databTest.addOrder(new Order("Koodi", "TestiKayttaja", "2020-04-06"));
        assertEquals("TestiKayttaja", this.databTest.getOrder("Koodi").getUserName());
    }
}
