package FintessM;

import javafx.beans.property.SimpleStringProperty;

public class Lease {
    private final SimpleStringProperty leaseID;
    private final SimpleStringProperty customerID;
    private final SimpleStringProperty ticketID;
    private final SimpleStringProperty startDate;
    private final SimpleStringProperty endDate;
    private final SimpleStringProperty remainingTime;

    public Lease(Integer customerID, Integer ticketID, String startDate, String endDate, String remainingTime){
        this.leaseID = new SimpleStringProperty("");
        this.customerID = new SimpleStringProperty(String.valueOf(customerID));
        this.ticketID = new SimpleStringProperty(String.valueOf(ticketID));
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.remainingTime = new SimpleStringProperty(remainingTime);
    }

    public Lease(Integer leaseID, Integer customerID, Integer ticketID, String startDate, String endDate, String remainingTime){
        this.leaseID = new SimpleStringProperty(String.valueOf(leaseID));
        this.customerID = new SimpleStringProperty(String.valueOf(customerID));
        this.ticketID = new SimpleStringProperty(String.valueOf(ticketID));
        this.startDate = new SimpleStringProperty(startDate);
        this.endDate = new SimpleStringProperty(endDate);
        this.remainingTime = new SimpleStringProperty(remainingTime);
    }

    public String getCustomerID() {
        return customerID.get();
    }

    public String getTicketID() {
        return ticketID.get();
    }

    public String getStartDate() {
        return startDate.get();
    }

    public String getEndDate() {
        return endDate.get();
    }

    public String getRemainingTime() {
        return remainingTime.get();
    }

    public String getLeaseID() {
        return leaseID.get();
    }
}
