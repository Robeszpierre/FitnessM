package FintessM;

import javafx.beans.property.SimpleStringProperty;

public class Products {

    private final SimpleStringProperty prductName;
    private final SimpleStringProperty productPrice;
    private final SimpleStringProperty productQuantity;
    private final SimpleStringProperty id;
    private final SimpleStringProperty limit;

    public Products() {
        this.prductName = new SimpleStringProperty("");
        this.productPrice = new SimpleStringProperty("");
        this.productQuantity = new SimpleStringProperty("");
        this.limit = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }

    public Products(String prductName, String productPrice, String productQuantity, String limit) {
        this.prductName = new SimpleStringProperty(prductName);
        this.productPrice = new SimpleStringProperty(productPrice);
        this.productQuantity = new SimpleStringProperty(productQuantity);
        this.limit = new SimpleStringProperty(limit);
        this.id = new SimpleStringProperty("");
    }

    public Products(Integer id, String prductName, String productPrice, String productQuantity, String limit) {
        this.prductName = new SimpleStringProperty(prductName);
        this.productPrice = new SimpleStringProperty(productPrice);
        this.productQuantity = new SimpleStringProperty(productQuantity);
        this.limit = new SimpleStringProperty(limit);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getPrductName() {
        return prductName.get();
    }
    public void setPrductNamee(String name) {
        prductName.set(name);
    }

    public String getProductPrice() {
        return productPrice.get();
    }
    public void setProductPrice(String price) {
        productPrice.set(price);
    }

    public String getProductQuantity() {
        return productQuantity.get();
    }
    public void setProductQuantity(String quantity) {
        productQuantity.set(quantity);
    }

    public String getLimit() {
        return limit.get();
    }
    public void setLimit(String flimit) {
        limit.set(flimit);
    }

    public String getId(){
        return id.get();
    }
    public void setId(String fId){
        id.set(fId);
    }

}
