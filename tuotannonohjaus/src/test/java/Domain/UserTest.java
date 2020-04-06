/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

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
public class UserTest {
    private User user;
    
    public UserTest() {
        this.user = new User("testUser", 0);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {

    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void userNameIsCorrect() {
        assertEquals("testUser", this.user.getName());
    }
    
    @Test
    public void userStatusIsCorrect() {
        assertEquals(0, this.user.getStatus());
    }
    
    @Test
    public void setNameChangesName() {
        this.user.setName("changedName");
        assertNotEquals("testUser", this.user.getName());
    }
    
    @Test
    public void setStatusChangesStatus() {
        this.user.setStatus(1);
        assertNotEquals(0, this.user.getStatus());
    }
    
    @Test
    public void setStatusChangesStatusCorrect() {
        this.user.setStatus(1);
        assertEquals(1, this.user.getStatus());
    }
}
