/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.user;

import entity.business.Allocation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Winter
 */
@Entity
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 16)
    private String passport;
    @Column(length = 32)
    private String name;
    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    private List<Allocation> allocations;

    public Guest() {
        allocations = new ArrayList<>();
    }

    public Guest(String passport, String name) {
        this();
        this.passport = passport;
        this.name = name;
    }

    /**
     * @return the passport
     */
    public String getPassport() {
        return passport;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public List<Allocation> getAllocations() {
        return allocations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getPassport() != null ? getPassport().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the guestId fields are not set
        if (!(object instanceof Guest)) {
            return false;
        }
        Guest other = (Guest) object;
        if ((this.getPassport() == null && other.getPassport() != null) || (this.getPassport() != null && !this.passport.equals(other.passport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Guest | passport= " + getPassport();
    }
    
}
