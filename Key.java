package FintessM;

import javafx.beans.property.SimpleStringProperty;

public class Key {
    private final SimpleStringProperty number;
    private final SimpleStringProperty ownerName;

    public Key(Integer number, String ownerName){
        this.number = new SimpleStringProperty(String.valueOf(number));
        this.ownerName = new SimpleStringProperty(String.valueOf(ownerName));
    }

    public Key(Integer number){
        this.number = new SimpleStringProperty(String.valueOf(number));
        this.ownerName = new SimpleStringProperty("");
    }

    public String getNumber() {
        return number.get();
    }

    public String getOwnerName() {
        return ownerName.get();
    }

    public void setOwnerName(String name){
        ownerName.set(name);
    }
}
