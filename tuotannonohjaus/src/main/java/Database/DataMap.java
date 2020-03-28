/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author sakorpi
 */
import Domain.WorkPhase;
import java.util.*;

public class DataMap implements Data {

    private HashMap<String, Integer> users;

    public DataMap() {
        this.users = new HashMap<>();
        users.put("admin", 1);
    }

    @Override
    public boolean userExists(String name) {
        if (users.containsKey(name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean getUserStatus(String name) {
        if (users.containsKey(name) && users.get(name) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addUser(String name, int status) {
        users.put(name, status);
        return true;
    }

    @Override
    public boolean removeUser(String name) {
        return false;
    }

    @Override
    public boolean format() {
        return false;
    }

    @Override
    public boolean addOrder(String code, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean orderExists(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<WorkPhase> getOrder(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEvent(String workphase, String code, String descr, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
