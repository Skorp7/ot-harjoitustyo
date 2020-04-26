
package domain;

import dicip.domain.WorkPhase;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sakorpi
 */
public class WorkPhaseTest {
    private WorkPhase workphase;
    
    public WorkPhaseTest() {
        this.workphase = new WorkPhase("2020-04-01", "Scanning", "FI20", "Worker1", "-");
    }
   
    @Test
    public void timestampIsCorrect() {
        assertEquals("2020-04-01", this.workphase.getTimestamp());
    }
    
    @Test
    public void workPhaseIsCorrect() {
        assertEquals("Scanning", this.workphase.getWorkphase());
    }
    
    @Test
    public void codeIsCorrect() {
        assertEquals("FI20", this.workphase.getCode());
    }
    
    @Test
    public void nameIsCorrect() {
        assertEquals("Worker1", this.workphase.getName());
    }
    
    @Test
    public void descriptionIsCorrect() {
        assertEquals("-", this.workphase.getDescription());
    }
    
    @Test
    public void workPhaseIsChangedCorrect() {
        this.workphase.setWorkphase("Sisäänkirjaus");
        assertEquals("Sisäänkirjaus", this.workphase.getWorkphase());
    }
    
     @Test
    public void descriptionIsChangedCorrect() {
        this.workphase.setDescription("newDescript");
        assertEquals("newDescript", this.workphase.getDescription());
    }
    
}
