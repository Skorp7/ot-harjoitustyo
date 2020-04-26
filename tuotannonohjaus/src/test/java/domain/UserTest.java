
package domain;

import dicip.domain.User;
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
    
    @Test
    public void sameUserIsTheSame() {
        User sameuser1 = new User("TheSame", 0);
        User sameuser2 = new User("TheSame", 0);
        assertEquals(sameuser1, sameuser2);
    }
    
    @Test
    public void sameUserIsTheSameEvenStatusIsNot() {
        User sameuser1 = new User("TheSame", 0);
        User sameuser2 = new User("TheSame", 1);
        assertEquals(sameuser1, sameuser2);
    }
}
