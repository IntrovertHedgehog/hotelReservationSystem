/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package horsmanagementclient;

import ejb.session.entity.AccountManagementSessionBeanRemote;
import ejb.session.entity.ExceptionReportManagementSessionBeanRemote;
import ejb.session.entity.OccupantManagementSessionBeanRemote;
import ejb.session.entity.ReservationManagementSessionBeanRemote;
import ejb.session.entity.RoomManagementSessionBeanRemote;
import ejb.session.task.WalkInSessionBeanRemote;
import entity.user.Employee;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.exception.InvalidLoginCredentialsException;

/**
 *
 * @author brianlim
 */
public class MainApp {

    @EJB
    private WalkInSessionBeanRemote walkInSessionBeanRemote;

    @EJB
    private RoomManagementSessionBeanRemote roomManagementSessionBeanRemote;

    @EJB
    private ReservationManagementSessionBeanRemote reservationManagementSessionBeanRemote;

    @EJB
    private OccupantManagementSessionBeanRemote occupantManagementSessionBeanRemote;

    @EJB
    private ExceptionReportManagementSessionBeanRemote exceptionReportManagementSessionBeanRemote;

    @EJB
    private AccountManagementSessionBeanRemote accountManagementSessionBeanRemote;

    private Employee currentLoggedInEmployee;

    public MainApp() {

    }

    public MainApp(WalkInSessionBeanRemote walkInSessionBeanRemote, RoomManagementSessionBeanRemote roomManagementSessionBeanRemote,
            ReservationManagementSessionBeanRemote reservationManagementSessionBeanRemote, OccupantManagementSessionBeanRemote occupantManagementSessionBeanRemote,
            ExceptionReportManagementSessionBeanRemote exceptionReportManagementSessionBeanRemote, AccountManagementSessionBeanRemote accountManagementSessionBeanRemote) {

        this.walkInSessionBeanRemote = walkInSessionBeanRemote;
        this.roomManagementSessionBeanRemote = roomManagementSessionBeanRemote;
        this.occupantManagementSessionBeanRemote = occupantManagementSessionBeanRemote;
        this.exceptionReportManagementSessionBeanRemote = exceptionReportManagementSessionBeanRemote;
        this.accountManagementSessionBeanRemote = accountManagementSessionBeanRemote;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        int response;
        while (true) {

            System.out.println("****** Hotel Reservation Management Client ******");
            System.out.println("1. Employee Login");
            System.out.println("2. Exit \n");
            response = 0;
            
            while (response < 1 || response > 2) {
                System.out.print(" > ");
                response = Integer.parseInt(sc.nextLine());
                
                switch (response) {
                    case 1:
                        
                        try {
                            this.currentLoggedInEmployee = login();
                            mainMenu();

                        } catch (InvalidLoginCredentialsException e) {
                            System.out.println(e.getMessage() + "\n");

                        }
                    
                    case 2:
                        break;

                    default:
                        System.out.println("Invalid option, please try again!\\n");
                }
            }
            if (response == 2) {
                break;
            }
        }
    }

    private Employee login() throws InvalidLoginCredentialsException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee Username > ");
        String username = sc.nextLine().trim();
        System.out.println("reprint input: " + username);

        System.out.print("Enter Employee Passowrd > ");
        String password = sc.nextLine().trim();
        System.out.println("reprint input: " + password);

        return accountManagementSessionBeanRemote.loginEmployee(username, password);
    }

    private void mainMenu() {

        switch (this.currentLoggedInEmployee.getEmployeeType()) {
            case SYSTEM_ADMINISTRATOR:
                SystemAdministratorClient sysAdmin = new SystemAdministratorClient(this.accountManagementSessionBeanRemote, this.currentLoggedInEmployee);
                sysAdmin.menuSysAdminOperations();
                break;
            case OPERATIONS_MANAGER:
                OperationManagerClient opsManager = new OperationManagerClient(this.roomManagementSessionBeanRemote, this.exceptionReportManagementSessionBeanRemote, this.currentLoggedInEmployee);
                opsManager.menuOperationManagerOperations();
                break;
            case SALES_MANAGER:
                SalesManagerClient salesManager = new SalesManagerClient(this.roomManagementSessionBeanRemote, this.currentLoggedInEmployee);
                salesManager.menuSalesManagerOperations();
                break;
            case GUEST_RELATION_OFFICER:
                GuestRelationOfficerClient guestOfficer = new GuestRelationOfficerClient(occupantManagementSessionBeanRemote, this.walkInSessionBeanRemote, this.currentLoggedInEmployee);
                guestOfficer.menuGuestRelationOfficerOperations();
                break;
            default:
                System.out.println("No such employee role!");
                break;
        }

    }
}
