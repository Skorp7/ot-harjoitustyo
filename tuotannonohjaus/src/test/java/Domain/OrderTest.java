/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import domain.Order;
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
public class OrderTest {
    private Order order;
    
    public OrderTest() {
        this.order = new Order("G30", "Worker2", "2020-04-02");
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

    @Test
    public void codeIsCorrect() {
        assertEquals("G30", this.order.getCode());
    }
    
    @Test
    public void nameIsCorrect() {
        assertEquals("Worker2", this.order.getUserName());
    }
    
    @Test
    public void timestampIsCorrect() {
        assertEquals("2020-04-02", this.order.getTimeStamp());
    }
}
