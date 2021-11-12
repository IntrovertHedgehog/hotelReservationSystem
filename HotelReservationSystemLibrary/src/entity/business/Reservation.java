/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.business;

import entity.user.Occupant;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.supplement.LocalDateAdapter;

/**
 *
 * @author Winter
 */
@XmlRootElement(name="Reservation")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ReservationId;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private RoomType roomType;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Occupant occupant;
    @Column(nullable = false, updatable = false)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate checkInDate;
    @Column(nullable = false, updatable = false)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate checkOutDate;
    @Column(nullable = false)
    private Boolean isProcessed;

    @ManyToMany
    @JoinColumn(nullable = false)
    private List<Rate> rates;
    
    @OneToOne(mappedBy = "reservation")
    @JoinColumn
    private Allocation allocation;
    
    public Reservation() {
    }

    public Reservation(RoomType roomType, Occupant occupant, List<Rate> rates, LocalDate checkInDate, LocalDate checkOutDate) {
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.occupant = occupant;
        this.rates = rates;
        this.isProcessed = false;
    }


    public Long getReservationId() {
        return ReservationId;
    }
    
    public List<Rate> getRates() {
        return rates;
    }

    /**
     * @return the roomType
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * @return the occupant
     */
    public Occupant getOccupant() {
        return occupant;
    }

    /**
     * @return the checkInDate
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * @return the checkOutDate
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * @return the allocation
     */
    public Allocation getAllocation() {
        return allocation;
    }

    /**
     * @param allocation the allocation to set
     */
    public void setAllocation(Allocation allocation) {
        if (allocation != null && allocation.getReservation().equals(this)) {
            this.allocation = allocation;
            this.isProcessed = true;
            this.occupant.addAllocation(allocation);
        }
    }

    /**
     * @return the isProcessed
     */
    public Boolean getIsProcessed() {
        return isProcessed;
    }
    
    public void setIsProcessed(Boolean isProcessed) {
        this.isProcessed = isProcessed;
    }

    /**
     * @param ReservationId the ReservationId to set
     */
    public void setReservationId(Long ReservationId) {
        this.ReservationId = ReservationId;
    }

    /**
     * @param roomType the roomType to set
     */
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    /**
     * @param occupant the occupant to set
     */
    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }

    /**
     * @param checkInDate the checkInDate to set
     */
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * @param checkOutDate the checkOutDate to set
     */
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * @param rates the rates to set
     */
    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
    
    public void nullify() {
        this.occupant.nullify();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ReservationId != null ? ReservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ReservationId fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.ReservationId == null && other.ReservationId != null) || (this.ReservationId != null && !this.ReservationId.equals(other.ReservationId))) {
            return false;
        }
        return true;
    }
    
}
