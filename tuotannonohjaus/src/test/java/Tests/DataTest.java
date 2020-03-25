package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.Data;
import Database.DataSql;
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
        this.databTest = new DataSql();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {}
     
     @Test
     public void adminExistsAtBeginning() throws SQLException{
         assertTrue(databTest.userExists("admin"));
     }
     
     @Test
     public void statusIsCorrect() throws SQLException {
         databTest.addUser("TestiKayttaja", 0);
         assertFalse(databTest.getUserStatus("TestiKayttaja"));
     }
}
