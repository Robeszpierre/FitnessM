package FintessM;

import javafx.beans.property.SimpleStringProperty;

public class LeaseType {
    private final SimpleStringProperty id;
    private final SimpleStringProperty type;
    private final SimpleStringProperty price;

    public LeaseType(){
        this.id = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.price = new SimpleStringProperty("");
    }

    public LeaseType(Integer id, String type, Integer price){
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleStringProperty(String.valueOf(price));
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }
}
