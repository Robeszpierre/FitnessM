package FintessM;

import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleStringProperty customerID;
    private final SimpleStringProperty name;

    public Customer(){
        this.customerID = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
    }

    public Customer(String name){
        this.customerID = new SimpleStringProperty("");
        this.name = new SimpleStringProperty(name);
    }

    public Customer(Integer id, String name){
        this.customerID = new SimpleStringProperty(String.valueOf(id));
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }
    public void setName(String fname) {
        name.set(fname);
    }

    public String getCustomerID() {
        return customerID.get();
    }
    public void setCustomerID(String fcustomerID) {
        customerID.set(fcustomerID);
    }

    @Override
    public String toString(){
        return (getName());
    }
}
