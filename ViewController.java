package FintessM;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
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
    ComboBox dressingRoomType2;
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
    Label actualCustomerName2;
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
    Circle gymCircle;
    @FXML
    Circle solariumCircle;
    @FXML
    Circle saunaCircle;
    @FXML
    Circle saltCircle;
    @FXML
    Label gymRemaining;
    @FXML
    Label solariumRemaining;
    @FXML
    Label saunaRemaining;
    @FXML
    Label saltRemaining;
    @FXML
    Button keyButton;
    @FXML
    Button keyButton2;
    @FXML
    Spinner<Integer> keyNumber;
    @FXML
    Spinner<Integer> keyNumber2;
    @FXML
    TextField filterField;
    @FXML
    Label keyLabel;
    @FXML
    Button minusGym;
    @FXML
    Button minusSolarium;
    @FXML
    Button minusSauna;
    @FXML
    Button minusSalt;

    DB db=new DB();

    private Customer actualCustomer=null;
    private String actualTicketType=null;
    private Lease actualGymlease=null;
    private Lease actualSolariumLease=null;
    private Lease actualSaunaLease=null;
    private Lease actualSaltLease=null;

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
    private FilteredList<Customer> filteredData = new FilteredList<>(customerData, p -> true);



    TableColumn customerName=null;

    //INITIALIZE **********************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMenuData();
        setTable();
        setButtonContents();
        setListeners();

        createWcabinets(28, wkeysflow);
        createWcabinets(24, mkeysflow);
        test();
    }

    private void setListeners() {
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                refreshCustomerTable();
                enableMinusButtons();
            }
        });
        customerSearch();
        keyNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                keyNumber.increment(0); // won't change value, but will commit editor
            }
        });
        keyNumber2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                keyNumber2.increment(0); // won't change value, but will commit editor
            }
        });
    }

    private void enableKeyButton() {
        if(!gymRemaining.getText().equals("---") || !solariumRemaining.getText().equals("---") || !saunaRemaining.getText().equals("---") || !saltRemaining.getText().equals("---")){
            keyButton.setDisable(false);
            keyLabel.setVisible(false);
        }else{
            keyButton.setDisable(true);
            keyLabel.setVisible(true);
        }
    }

    private void refreshCustomerTable(){
        try{
        actualCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        actualCustomerName.setText(actualCustomer.getName());
        actualCustomerName2.setText(actualCustomer.getName());
        checkValidation(actualCustomer.getCustomerID());
        enableKeyButton();
            enableMinusButtons();
        }catch (Exception e){
           //selected item is not in the filtered list
        }
    }

    private void enableMinusButtons() {
        try{
            if(Integer.parseInt(gymRemaining.getText())<=10) {
                minusGym.setVisible(true);
            }
        }catch (Exception e){minusGym.setVisible(false);}
        try {
            if(Integer.parseInt(solariumRemaining.getText())<=10) {
                minusSolarium.setVisible(true);
            }
        }catch (Exception e){minusSolarium.setVisible(false);}
        try {
            if(Integer.parseInt(saunaRemaining.getText())<=10) {
                minusSauna.setVisible(true);
            }
        }catch (Exception e){minusSauna.setVisible(false);}
        try {
            if(Integer.parseInt(saltRemaining.getText())<=10) {
                minusSalt.setVisible(true);
            }
        }catch (Exception e){minusSalt.setVisible(false);}
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
                            enableMinusButtons();
                            refreshCustomerTable();
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

        customerName = new TableColumn("Vendég neve");
        customerName.setMinWidth(250);
        customerName.setPrefWidth(250);
        customerName.setMaxWidth(250);
        customerName.setCellFactory(TextFieldTableCell.forTableColumn());
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

        customerName.setSortType(TableColumn.SortType.ASCENDING);

        customerTable.getColumns().add(customerName);
        refreshCustomerData();

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
        dressingRoomType2.getItems().addAll("férfi", "női");

        DailyTicket d=new DailyTicket();
        ticketType.getItems().addAll(d.GYM, d.SOLARIUM, d.SAUNA, d.COMBINED, d.SALTROOM);
    }

    //end initialize **********************************************************

    //LEASES **********************************************************

    @FXML
    public void enableSellLeaseButton(){
        LeaseType l= (LeaseType) leaseType.getSelectionModel().getSelectedItem();
        try {
            if (!l.getType().equals("")) {
                sellLeaseButton.setDisable(false);
            }
        }catch (Exception e){

        }
    }

    @FXML
    private void giveOutKey(MouseEvent mouseEvent) {
        mainFrame.setOpacity(0.3);
        String roomType = null;
        int actualKey=0;
        try {
            roomType = (String) dressingRoomType.getSelectionModel().getSelectedItem();
            if(roomType.equals("férfi") && keyNumber.getValue()>24){
                wrongKeyNumberError();

            }else{
                if (!roomType.equals(null)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Kulcskiadás");
                    alert.setHeaderText("Biztosan " + actualCustomer.getName() + "  nevű vendégnek szeretnéd kiadni az alábbi számozású kulcsot: " + roomType + " " + keyNumber.getValue() + "?");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
                    ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        actualKey=keyNumber.getValue();
                        //woman keys count begins at 25
                        if(roomType.equals("női")){
                            actualKey+=24;
                        }try {
                            db.addKeyHolder(actualCustomer.getName(), actualKey);
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Sikeres kulcskiadás!");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Sikeres kulcskiadás!");
                            alert2.showAndWait();
                            dressingRoomType.getSelectionModel().clearSelection();
                            keyNumber.getValueFactory().setValue(1);
                            mainFrame.setOpacity(1);
                        }catch (Exception e){
                            keyAlreadyInUseError();
                        }
                    } else {
                        mainFrame.setOpacity(1);
                    }
                }
            }

        }catch (Exception e){
            chooseDressingRoomTypeError();
        }
    }

    @FXML
    private void giveOutKey2(MouseEvent mouseEvent) {
        mainFrame.setOpacity(0.3);
        String roomType = null;
        int actualKey=0;
        try {
            roomType = (String) dressingRoomType2.getSelectionModel().getSelectedItem();
            if(roomType.equals("férfi") && keyNumber2.getValue()>24){
                wrongKeyNumberError();
            }else{
                if (!roomType.equals(null)) {
                    TextInputDialog dialog = new TextInputDialog("Vezetéknév Keresztnév");
                    dialog.setTitle("Text Input Dialog");
                    dialog.setHeaderText("Look, a Text Input Dialog");
                    dialog.setContentText("Please enter your name:");
                    String name=null;
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        name=result.get();
                        actualKey=keyNumber2.getValue();
                        System.out.println(actualKey);
                    }
                        //woman keys count begins at 25
                        if(roomType.equals("női")){
                            actualKey+=24;
                        }try {
                            db.addKeyHolder(name, actualKey);
                            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                            alert2.setTitle("Sikeres kulcskiadás!");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Sikeres kulcskiadás!");
                            alert2.showAndWait();
                            dressingRoomType2.getSelectionModel().clearSelection();
                            keyNumber2.getValueFactory().setValue(1);
                            mainFrame.setOpacity(1);
                        }catch (Exception e){
                        keyAlreadyInUseError();
                        }
                    } else {
                        mainFrame.setOpacity(1);
                    }
                }
        }catch (Exception e){
            chooseDressingRoomTypeError();
        }
    }

    private void keyAlreadyInUseError(){
        Alert alert2 = new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("Sikertelen kulcskiadás!");
        alert2.setHeaderText(null);
        alert2.setContentText("A kulcs már valaki másnál van! Válassz másik kulcsot!");
        alert2.showAndWait();
        mainFrame.setOpacity(1);
    }

    private void chooseDressingRoomTypeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Nem választottad ki az öltöző típusát!");
        alert.setContentText("Válaszd ki, hogy férfi vagy női öltöző");
        alert.showAndWait();
        mainFrame.setOpacity(1);
    }

    private void wrongKeyNumberError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Csak 24 férfi öltöző van");
        alert.setContentText("Válassz érvényes sorszámú férfi öltözőt!");
        alert.showAndWait();
        mainFrame.setOpacity(1);
    }

    public void customerSearch() {
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        customerTable.setItems(sortedData);
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
            refreshCustomerData();
            customerSearch();
            mainFrame.setOpacity(1);
        }else{
            mainFrame.setOpacity(1);
        }
    }

    public void sellLease(){
        mainFrame.setOpacity(0.3);

        LeaseType actualLease= (LeaseType) leaseType.getSelectionModel().getSelectedItem();
        Customer c= (Customer) customerTable.getSelectionModel().getSelectedItem();
        String startDate=null;
        String endDate=null;
        int remainingTime=0;
        int price=Integer.parseInt(actualLease.getPrice());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bérleteladás");
        alert.setHeaderText("Biztosan " + actualLease.getType() + " bérletet szeretnél eladni "+ actualCustomerName2.getText() +" nevű vendégnek?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            Calendar cal = Calendar.getInstance();
            startDate=dateFormat.format(cal.getTime());
            endDate=calculateEndDate(actualLease.getId(), cal);

            if(Integer.parseInt(actualLease.getId())==1){
                remainingTime=10000;
            }else{
                remainingTime=10;
            }


            db.addNewLease(Integer.parseInt(c.getCustomerID()), Integer.parseInt(actualLease.getId()), startDate, endDate, remainingTime, price, actualLease.getType());

            alert2.setTitle("Sikeres bérleteladás!");
            alert2.setHeaderText(null);
            alert2.setContentText("A fizetendő összeg: "+  price+ " Ft");

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
                cal.add(cal.MONTH, 1);
                endDate=dateFormat.format(cal.getTime());
                break;
            case 2:
                cal.add(cal.MONTH, 2);
                endDate=dateFormat.format(cal.getTime());
                break;
            case 3:
            case 4:
            case 5:
                cal.add(cal.YEAR, 1);
                endDate=dateFormat.format(cal.getTime());
                break;
        }
        System.out.println(endDate);
        return endDate;
    }

    private void refreshCustomerData(){
        customerData.clear();
        //get data from database
        customerData.addAll(db.getCustomers());
        //add database data to the table
        customerTable.setItems(customerData);
        //order data by name
        customerTable.getSortOrder().add(customerName);
    }

    public void clearLeaseSale(){
        sellLeaseButton.setDisable(true);
        leaseType.getSelectionModel().clearSelection();
    }

    public void checkValidation(String customerID){
        actualGymlease=db.getGymValid(customerID, 1);
        try {
            actualGymlease.getTicketID();
            gymCircle.setFill(javafx.scene.paint.Color.web("#b5ed87"));
            gymRemaining.setText("korlátlan");
        }catch (Exception e){
            //nincs érvényes havi bérlete
            actualGymlease=db.getGymValid(customerID, 2);
            printValidation(actualGymlease, gymCircle, gymRemaining);
        }
        actualSolariumLease=db.getGymValid(customerID, 3);
        printValidation(actualSolariumLease, solariumCircle, solariumRemaining);
        actualSaunaLease=db.getGymValid(customerID, 4);
        printValidation(actualSaunaLease, saunaCircle, saunaRemaining);
        actualSaltLease=db.getGymValid(customerID, 5);
        printValidation(actualSaltLease, saltCircle, saltRemaining);
    }

    private void printValidation(Lease gymlease, Circle gymCircle, Label gymRemaining) {
        try {
            gymlease.getTicketID();
            gymCircle.setFill(javafx.scene.paint.Color.web("#b5ed87"));
            gymRemaining.setText(gymlease.getRemainingTime());
        }catch (Exception e2){
            //nincs érvényes 10 napos bérlete
            gymCircle.setFill(javafx.scene.paint.Color.web("#fb5856"));
            gymRemaining.setText("---");
        }
    }

    public void minusGymCount(MouseEvent mouseEvent) {
        minus1(Integer.parseInt(actualGymlease.getLeaseID()));
    }

    public void minusSolariumCount(MouseEvent mouseEvent) {
        minus1(Integer.parseInt(actualSolariumLease.getLeaseID()));
    }

    public void minusSaunaCount(MouseEvent mouseEvent) {
        minus1(Integer.parseInt(actualSaunaLease.getLeaseID()));
    }

    public void minusSaltCount(MouseEvent mouseEvent) {
        minus1(Integer.parseInt(actualSaltLease.getLeaseID()));
    }

    private void minus1(int leaseID) {
        db.minusCount(leaseID);
        minusGym.setVisible(true);
        refreshCustomerTable();
        enableMinusButtons();
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

    //KEY **********************************************************



    //end key **********************************************************

    private void test() {


    }

}
