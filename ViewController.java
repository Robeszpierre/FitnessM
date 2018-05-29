package FintessM;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewController implements Initializable {

    @FXML
    StackPane menupane;
    @FXML
    StackPane valid;
    @FXML
    Pane leaseSell;
    @FXML
    StackPane productSell;
    @FXML
    StackPane storage;
    @FXML
    StackPane mankeys;
    @FXML
    StackPane womankeys;
    @FXML
    FlowPane wkeysflow;
    @FXML
    FlowPane mkeysflow;
    @FXML
    AnchorPane tableAnchor;
    @FXML
    TableView productsTable;
    @FXML
    Spinner productAmount;
    @FXML
    TableView customerTable;
    @FXML
    ComboBox dressingRoomType;
    @FXML
    StackPane tickets;
    @FXML
    Pane validPane;
    @FXML
    Label nameLabel;
    @FXML
    SplitPane mainFrame;
    @FXML
    StackPane otherSells;
    @FXML
    StackPane statistic;
    @FXML
    Label actualCustomerName;
    @FXML
    TextField actualCustomerName2;
    @FXML
    ComboBox leaseType;
    @FXML
    ComboBox ticketType;
    @FXML
    Spinner<Integer> dailyTicketQuantity;
    @FXML
    Button sellTicket;
    @FXML
    Button sellLeaseButton;
    @FXML
    Label outdateLabel;

    DB db=new DB();

    Customer actualCustomer=null;
    String actualTicketType=null;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String LEASES = "Bérletek";
    private final String VALID = "Érvényesség/kulcskiadás";
    private final String LEASESELL = "Bérleteladás";
    private final String TICKETS = "Napijegyek";
    private final String OTHERSELLS = "Egyéb eladások";
    private final String KEYS = "Kulcsok";
    private final String MENKEYS = "Férfi öltöző";
    private final String WOMENKEYS = "Női öltöző";
    private final String PRODUCTS = "Termékek";
    private final String PRODUCTSELL = "Termékeladás";
    private final String PRODUCTSTORAGE = "Raktár";
    private final String STATISTIC = "Statisztika";

    private final ObservableList<Products> data = FXCollections.observableArrayList(
            new Products("cola", "300", "10", "5"),
            new Products("fanta", "300", "20", "5")
    );

    private final ObservableList<Customer> customerData = FXCollections.observableArrayList();

    //INITIALIZE **********************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMenuData();
        setTable();
        setButtonContents();

        createWcabinets(28, wkeysflow);
        createWcabinets(24, mkeysflow);
        test();
    }

    //create menu items
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(LEASES);
        TreeItem<String> nodeItemB = new TreeItem<>(PRODUCTS);
        TreeItem<String> nodeItemC = new TreeItem<>(KEYS);
        TreeItem<String> nodeItemE = new TreeItem<>(TICKETS);
        TreeItem<String> nodeItemF = new TreeItem<>(OTHERSELLS);
        TreeItem<String> nodeItemG = new TreeItem<>(STATISTIC);

        nodeItemA.setExpanded(true);
        nodeItemB.setExpanded(true);
        nodeItemC.setExpanded(true);

        TreeItem<String> nodeItemA1 = new TreeItem<>(VALID);
        TreeItem<String> nodeItemA2 = new TreeItem<>(LEASESELL);

        nodeItemA2.setExpanded(true);

        TreeItem<String> nodeItemB1 = new TreeItem<>(PRODUCTSELL);
        TreeItem<String> nodeItemB2 = new TreeItem<>(PRODUCTSTORAGE);

        TreeItem<String> nodeItemC1 = new TreeItem<>(MENKEYS);
        TreeItem<String> nodeItemC2 = new TreeItem<>(WOMENKEYS);

        nodeItemA.getChildren().addAll(nodeItemA1, nodeItemA2);
        nodeItemB.getChildren().addAll(nodeItemB1, nodeItemB2);
        nodeItemC.getChildren().addAll(nodeItemC1, nodeItemC2);
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemE, nodeItemF, nodeItemB, nodeItemC, nodeItemG);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case LEASES:
                            selectedItem.setExpanded(true);
                            break;
                        case VALID:
                            valid.setVisible(true);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);

                            validPane.setVisible(true);
                            break;
                        case LEASESELL:
                            valid.setVisible(true);
                            leaseSell.setVisible(true);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);

                            validPane.setVisible(false);
                            clearLeaseSale();
                            break;
                        case TICKETS:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(true);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);

                            clearDailyTicketSale();
                            break;
                        case KEYS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENKEYS:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(true);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            break;
                        case WOMENKEYS:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(true);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            break;
                        case PRODUCTS:
                            selectedItem.setExpanded(true);
                            break;
                        case PRODUCTSELL:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(true);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            break;
                        case PRODUCTSTORAGE:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(true);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            break;
                        case OTHERSELLS:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(true);
                            statistic.setVisible(false);
                            break;
                        case STATISTIC:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(true);
                            break;

                    }
                }
            }
        });

        menupane.getChildren().add(treeView);
    }

    //create the buttons for women cabinets
    private void createWcabinets(int num, FlowPane flowpane){
        for(int i=1; i<=num; i++){
            Button button= new Button(""+i);
            button.setId("button"+i);

            flowpane.getChildren().add(button);
        }
    }

    //create the table
    private void setTable() {
        TableColumn productName = new TableColumn("Termék neve");
        productName.setMinWidth(200);
        productName.setPrefWidth(300);
        productName.setCellFactory(TextFieldTableCell.forTableColumn());
        productName.setCellValueFactory(new PropertyValueFactory<Products, String>("prductName"));

        TableColumn price = new TableColumn("Termék ára");
        price.setMinWidth(200);
        price.setPrefWidth(300);
        price.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setCellValueFactory(new PropertyValueFactory<Products, String>("productPrice"));

        TableColumn quentity = new TableColumn("Termék darabszáma");
        quentity.setMinWidth(200);
        quentity.setPrefWidth(300);
        quentity.setCellFactory(TextFieldTableCell.forTableColumn());
        quentity.setCellValueFactory(new PropertyValueFactory<Products, String>("productQuantity"));

        TableColumn limit = new TableColumn("Termék limitje");
        limit.setMinWidth(200);
        limit.setPrefWidth(300);
        limit.setCellFactory(TextFieldTableCell.forTableColumn());
        limit.setCellValueFactory(new PropertyValueFactory<Products, String>("limit"));

        productsTable.getColumns().addAll(productName, price, quentity, limit);
        productsTable.setItems(data);

        TableColumn customerName = new TableColumn("Vendég neve");
        customerName.setMinWidth(250);
        customerName.setPrefWidth(250);
        customerName.setMaxWidth(250);
        customerName.setCellFactory(TextFieldTableCell.forTableColumn());
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));


        customerTable.getColumns().add(customerName);
        refreshCustomerDatea();
        customerTable.getSelectionModel().selectFirst();

    }

    private void setButtonContents() {
        //you can choose here the type of the lease
        Callback<ListView<LeaseType>, ListCell<LeaseType>> factory = lv -> new ListCell<LeaseType>() {
            @Override
            protected void updateItem(LeaseType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getType());
            }
        };
        leaseType.setCellFactory(factory);
        leaseType.setButtonCell(factory.call(null));
        leaseType.getItems().addAll(db.getLeases());

        //type of the dressing room
        dressingRoomType.getItems().addAll("férfi", "női");

        DailyTicket d=new DailyTicket();
        ticketType.getItems().addAll(d.GYM, d.SOLARIUM, d.SAUNA, d.COMBINED, d.SALTROOM);
    }

    //end initialize **********************************************************

    //LEASES **********************************************************
    @FXML
    public void clickItem(MouseEvent event)
    {
            actualCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
            actualCustomerName.setText(actualCustomer.getName());
            actualCustomerName2.setText(actualCustomer.getName());


    }

    @FXML
    public void enableSellLeaseButton(){
            sellLeaseButton.setDisable(false);
    }

    public void getCustomerName() {
        mainFrame.setOpacity(0.3);
        TextInputDialog dialog = new TextInputDialog("Vezetéknév Keresztnév");
        dialog.setTitle("Új vendég hozzáadása");
        dialog.setHeaderText("Új vendég hozzáadása");
        dialog.setContentText("Írd be a vendég nevét:");

        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Hozzáadás");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");

        Optional<String> result = dialog.showAndWait();
        System.out.println("hozzaadtuk a nevet");

        if(result.isPresent()){
            Customer cust=new Customer(dialog.getResult());
            db.addCustomer(cust);
            refreshCustomerDatea();
            mainFrame.setOpacity(1);
        }else{
            mainFrame.setOpacity(1);
        }
    }


    public void sellLease(){
        mainFrame.setOpacity(0.3);

        LeaseType l= (LeaseType) leaseType.getSelectionModel().getSelectedItem();
        Customer c= (Customer) customerTable.getSelectionModel().getSelectedItem();
        String startDate=null;
        String endDate=null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bérleteladás");
        alert.setHeaderText("Biztosan " + l.getType() + " bérletet szeretnél eladni "+ actualCustomerName2.getText() +" nevű vendégnek?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            Calendar cal = Calendar.getInstance();
            startDate=dateFormat.format(cal.getTime());
            endDate=calculateEndDate(l.getId(), cal);

            db.addNewLease(Integer.parseInt(c.getCustomerID()), Integer.parseInt(l.getId()), startDate, endDate);

            alert2.setTitle("Sikeres bérleteladás!");
            alert2.setHeaderText(null);
            alert2.setContentText("A fizetendő összeg: "+  l.getPrice()+ " Ft");

            alert2.showAndWait();
            clearLeaseSale();
            mainFrame.setOpacity(1);
        } else {
            mainFrame.setOpacity(1);
        }
    }

    private String calculateEndDate(String id, Calendar cal) {
        String endDate=null;
        switch (Integer.parseInt(id)){
            case 1:
                cal.add(cal.DATE, 30);
                endDate=dateFormat.format(cal.getTime());
                break;
            case 2:
                cal.add(cal.DATE, 60);
                endDate=dateFormat.format(cal.getTime());
                break;
            case 3:
            case 4:
            case 5:
                cal.add(cal.DATE, 365);
                endDate=dateFormat.format(cal.getTime());
                break;
        }
        System.out.println(endDate);
        return endDate;
    }

    private void refreshCustomerDatea(){
        //get data from database
        customerData.addAll(db.getCustomers());
        //add database data to the table
        customerTable.setItems(customerData);
    }

    public void clearLeaseSale(){
        sellLeaseButton.setDisable(true);
        leaseType.getSelectionModel().clearSelection();
    }
    //end Leases **********************************************************

    //TICKET **********************************************************
    @FXML
    public void activateTicketButton(){
        sellTicket.setDisable(false);
    }

    @FXML
    public void sellDailyTicket(){
        mainFrame.setOpacity(0.3);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jegyeladás");
        alert.setHeaderText("Biztosan " + dailyTicketQuantity.getValue() + " db " + ticketType.getSelectionModel().getSelectedItem() + " jegyet szeretnél eladni?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            int price=getTicketPrice();
            db.sellDailyTicket(price, actualTicketType);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikeres jegyeladás!");
            alert2.setHeaderText(null);
            alert2.setContentText("A fizetendő összeg: " + price + " Ft");

            alert2.showAndWait();
            clearDailyTicketSale();
            mainFrame.setOpacity(1);
        } else {
            mainFrame.setOpacity(1);
        }
    }

    public int getTicketPrice(){
        actualTicketType = (String) ticketType.getSelectionModel().getSelectedItem();
        DailyTicket dailyTicket= new DailyTicket(actualTicketType);
        return dailyTicket.getPrice()*dailyTicketQuantity.getValue();
    }

    private void clearDailyTicketSale(){
        ticketType.getSelectionModel().clearSelection();
        dailyTicketQuantity.getValueFactory().setValue(1);
        sellTicket.setDisable(true);
    }
    //end ticket ******************************************************



    private void test() {



    }



}
