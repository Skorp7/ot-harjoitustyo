/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import database.DataMap;
import domain.Service;
import domain.User;
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
public class ServiceTest {
    
    private Service service;
    private User user;

    public ServiceTest() {
        this.service = new Service(new DataMap());
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.service.addUser("Esko", 0);
        this.user = this.service.getUser("Esko");
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
    public void checkDatabaseReturnsTrue() {
        assertTrue(this.service.checkDatabase());
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
        this.service.addUser("LoginU", 1);
        assertTrue(this.service.login("LoginU"));
    }
      
    @Test
    public void loginReturnFalseIfUserDoesNotExist() {
        assertFalse(this.service.login("LoginUser"));
    }
    
    @Test
    public void rightUserLoggedIn() {
        this.service.addUser("Eppu", 1);
        this.service.login("Eppu");
        this.service.addUser("Uuno", 0);
        this.service.login("Uuno");
        assertEquals("Uuno", this.service.getLoggedInUser().getName());
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
        this.service.addOrder("O", user);
        assertTrue(this.service.getOrder("O") != null);
    }
}
