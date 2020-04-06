/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author sakorpi
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
