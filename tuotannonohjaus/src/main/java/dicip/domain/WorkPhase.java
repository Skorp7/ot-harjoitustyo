package dicip.domain;

/**
 *
 * @author sakorpi
 */
/**
 *
 * Luokka kuvaa yksittäistä työvaihetta
 */
public class WorkPhase {
    private String workphase;
    private String orderCode;
    private String userName;
    private String description;
    private String timestamp;
    
    public WorkPhase(String timestamp, String workphase, String code, String name, String description) {
        this.workphase = workphase;
        this.orderCode = code;
        this.userName = name;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getWorkphase() {
        return workphase;
    }

    public String getCode() {
        return orderCode;
    }

    public String getName() {
        return userName;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setWorkphase(String workphase) {
        this.workphase = workphase;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
