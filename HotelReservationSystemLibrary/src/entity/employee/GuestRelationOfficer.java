/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.employee;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Winter
 */
@Entity
public class GuestRelationOfficer extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    public GuestRelationOfficer() {
    }

    public GuestRelationOfficer(String name, String username, String password) {
        super(name, username, password);
    }
    

    @Override
    public String toString() {
        return "Guest Relation Officer -> " + super.toString();
    }
    
}
