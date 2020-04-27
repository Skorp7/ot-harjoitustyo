package dicip.domain;

/**
 *
 * @author sakorpi
 */
/**
 * Class represents a single order
 */
public class Order {

    private String code;
    private String userName;
    private String timestamp;  // The creation (registration) time

    public Order(String code, String userName, String timestamp) {
        this.code = code;
        this.userName = userName;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return this.code;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getTimeStamp() {
        return this.timestamp;
    }
}
