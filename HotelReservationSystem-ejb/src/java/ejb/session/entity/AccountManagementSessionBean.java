/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.entity;

import entity.user.Employee;
import entity.user.Guest;
import entity.user.Partner;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialsException;

/**
 *
 * @author Winter
 */
@Stateless
public class AccountManagementSessionBean implements AccountManagementSessionBeanLocal, AccountManagementSessionBeanRemote {

    @PersistenceContext(unitName = "HotelReservationSystem-PU")
    private EntityManager em;

    @Override
    public Employee loginEmployee(String username, String password) throws InvalidLoginCredentialsException {
        Employee employee = (Employee) em.createQuery("SELECT e FROM Employee e WHERE e.username = :username AND e.password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();

        if (employee == null) {
            throw new InvalidLoginCredentialsException("Invalid Login Credential! \n");
        }
        return employee;
    }

    @Override
    public Long createEmployee(Employee employee) {
        em.persist(employee);
        em.flush();
        return employee.getEmployeeId();
    }

    @Override
    public List<Employee> viewAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e")
                .getResultList();
    }

    @Override
    public Long createPartner(Partner partner) {
        em.persist(partner);
        em.flush();

        return partner.getPartnerId();
    }

    @Override
    public List<Partner> viewAllPartners() {
        return em.createQuery("SELECT p FROM Partner p")
                .getResultList();
    }

    @Override
    public Partner loginPartner(String username, String password) throws InvalidLoginCredentialsException{
        Partner partner = (Partner) em.createQuery("SELECT p FROM Partner p WHERE p.username = :username AND p.password = :password")
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
        if (partner == null) {
            throw new InvalidLoginCredentialsException("Invalid Login Credential! \n");
        }
        return partner;
    }
    public Guest guestLogin(String username, String password) throws InvalidLoginCredentialsException {
        Query query = em.createQuery("SELECT g FROM Guest g WHERE g.username = :inUsername");
        query.setParameter("inUsername", username);

        Guest guest = (Guest) query.getSingleResult();

        if (guest != null) {
            if (guest.getPassword().equals(password)) {
                guest.getReservations().size();
                guest.getAllocations().size();
                return guest;
            } else {
                throw new InvalidLoginCredentialsException("Invalid Login Credential! \n");
            }
        } else {
            throw new InvalidLoginCredentialsException("Invalid Login Credential! \n");
        }
    }

    @Override
    public String registerAsGuest(String username, String password, String passport, String name) throws InvalidLoginCredentialsException {
        //Occupant occupant = em.find(Occupant.class, passport);
        Guest newGuest = new Guest(username, password, passport, name);
        Query query = em.createQuery("SELECT g FROM Guest g WHERE g.username = :inUsername");
        query.setParameter("inUsername", username);
        Guest oldGuest = (Guest) query.getSingleResult();
        
        if (oldGuest == null) {
            /*
            if (occupant != null) {
                newGuest.setAllocations(occupant.getAllocations());
                newGuest.setReservations(occupant.getReservations());
            }
            */
            em.persist(newGuest);
            em.flush();
            
        } else {
            throw new InvalidLoginCredentialsException("The username is taken!");
        }
        return newGuest.getPassport();
    }
    
 
}
