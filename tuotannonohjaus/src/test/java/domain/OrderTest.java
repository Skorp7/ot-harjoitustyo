
package domain;

import dicip.domain.Order;
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
