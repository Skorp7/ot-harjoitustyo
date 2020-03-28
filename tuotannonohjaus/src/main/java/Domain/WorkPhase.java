/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author sakorpi
 */
public class WorkPhase {
    private String workphase;
    private String code;
    private String name;
    private String description;
    private String timestamp;
    
    public WorkPhase(String timestamp, String workphase, String code, String name, String description) {
        this.workphase = workphase;
        this.code = code;
        this.name = name;
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getWorkphase() {
        return workphase;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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
