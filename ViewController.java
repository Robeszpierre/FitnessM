package FintessM;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    Spinner<Integer> productAmount;
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
    SplitPane mainScene;
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
    Spinner<Integer> cancellationAmount;
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
    @FXML
    TextField otherProductName;
    @FXML
    Spinner<Integer> otherProductAmount;
    @FXML
    Button otherProductSellButton;
    @FXML
    TextField productName;
    @FXML
    TextField productPrice;
    @FXML
    TextField productLimit;
    @FXML
    Button addNewProduct;
    @FXML
    ComboBox productList;
    @FXML
    ComboBox productList2;
    @FXML
    TextField productQuantity;
    @FXML
    Button productSellButton;
    @FXML
    Label currentCashLabel;
    @FXML
    Label dailyIncomeLabel;
    @FXML
    Label currentMonthIncomeLabel;
    @FXML
    Label lastMonthIncomeLabel;
    @FXML
    DatePicker startDatePicker;
    @FXML
    CheckBox dailyConti;
    @FXML
    CheckBox contiLeaseCheckBox;
    @FXML
    StackPane cancellation;
    @FXML
    Spinner<Integer> dailyContiReference;
    @FXML
    Label contiLabel;
    @FXML
    Spinner<Integer> dailyContiTicketNumber;
    @FXML
    Label contiTicketNumber;
    @FXML
    Label contiLabel1;
    @FXML
    Label contiLabel2;
    @FXML
    Spinner<Integer> leaseReferenceNumber;
    @FXML
    Spinner<Integer> contiLeaseNumber;
    @FXML
    Label prevMonthConti;
    @FXML
    TableColumn incomeName;
    @FXML
    TableColumn incomeAmount;
    @FXML
    StackPane incomeTable;
    @FXML
    TableView incomeTableView;


    DB db=new DB();

    private Customer actualCustomer=null;
    private String actualTicketType=null;
    private Lease actualGymlease=null;
    private Lease actualSolariumLease=null;
    private Lease actualSaunaLease=null;
    private Lease actualSaltLease=null;

    ArrayList<Button> manCabinets=new ArrayList<Button>();
    ArrayList<Button> womanCabinets=new ArrayList<Button>();

    final String dateFormatPattern = "yyyy-MM-dd";

    DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

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
    private final String CANCELLATION = "Sztornó";
    private final String INCOME = "Napi bevétel";

    private final ObservableList<Product> data = FXCollections.observableArrayList();
    private final ObservableList<Income> incomeData = FXCollections.observableArrayList();

    private final ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private FilteredList<Customer> filteredData = new FilteredList<>(customerData, p -> true);

    ArrayList<Key> keys=null;



    TableColumn customerName=null;
    TableColumn productNameCell =null;

    //INITIALIZE **********************************************************

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setMenuData();
        setTable();
        setIncomeTable();

        setButtonContents();
        setListeners();

        createWcabinets(28, wkeysflow);
        createMcabinets(24, mkeysflow);
        createKeys();
        test();

    }



    private void createKeys() {
        keys=new ArrayList<Key>();
        for(int i=1; i<=52; i++){
            keys.add(new Key(i));
        }
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
            cancellationAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    cancellationAmount.increment(0); // won't change value, but will commit editor
                }
            });
            otherProductAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    otherProductAmount.increment(0); // won't change value, but will commit editor
                }
            });
            productAmount.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    otherProductAmount.increment(0); // won't change value, but will commit editor
                }
            });
            dailyTicketQuantity.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    dailyTicketQuantity.increment(0); // won't change value, but will commit editor
                }
            });
            dailyContiReference.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        dailyContiReference.increment(0); // won't change value, but will commit editor
                    }catch (NumberFormatException e){
                        notANumberAlert();
                    }
                }
            });
            dailyContiTicketNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        dailyContiTicketNumber.increment(0); // won't change value, but will commit editor
                    }catch (NumberFormatException e){
                        notANumberAlert();
                    }
                }
            });
            leaseReferenceNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        leaseReferenceNumber.increment(0); // won't change value, but will commit editor
                    }catch (NumberFormatException e){
                        notANumberAlert();
                    }
                }
            });
            contiLeaseNumber.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        contiLeaseNumber.increment(0); // won't change value, but will commit editor
                    }catch (NumberFormatException e){
                        notANumberAlert();
                    }
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

    private void notANumberAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba!");
        alert.setHeaderText("");
        alert.setContentText("Helytelenül kitöltött mező! számot adj meg!");

        alert.showAndWait();
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
        TreeItem<String> nodeItemH = new TreeItem<>(CANCELLATION);
        TreeItem<String> nodeItemI = new TreeItem<>(INCOME);

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
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemE, nodeItemF, nodeItemB, nodeItemC, nodeItemG, nodeItemH, nodeItemI);

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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);

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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);

                            refreshStartDate();
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);

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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);
                            refreshKeys();
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);
                            refreshKeys();
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);

                            productList2.getSelectionModel().clearSelection();
                            productSellButton.setDisable(true);
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);
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
                            cancellation.setVisible(false);
                            incomeTable.setVisible(false);

                            refreshStatistic();
                            break;
                        case CANCELLATION:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            cancellation.setVisible(true);
                            incomeTable.setVisible(false);

                            cancellationAmount.getValueFactory().setValue(1000);
                            break;
                        case INCOME:
                            valid.setVisible(false);
                            leaseSell.setVisible(false);
                            tickets.setVisible(false);
                            mankeys.setVisible(false);
                            womankeys.setVisible(false);
                            productSell.setVisible(false);
                            storage.setVisible(false);
                            otherSells.setVisible(false);
                            statistic.setVisible(false);
                            cancellation.setVisible(false);
                            incomeTable.setVisible(true);

                            refreshIncomeTable();
                            break;
                    }
                }
            }
        });

        menupane.getChildren().add(treeView);
    }

    //create the buttons for women cabinets
    private void createWcabinets(int num, FlowPane wkeysflow){
        for(int i=1; i<=num; i++){
            int wnum=i+24;
            Button button= new Button(""+i);
            button.setId("button"+wnum);
            wkeysflow.getChildren().add(button);
            womanCabinets.add(button);
            button.getStyleClass().add("enableKey");

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int keyID = Integer.parseInt(button.getText()) + 23;
                    Key k = keys.get(keyID);
                    if (k.getOwnerName().equals("")) {
                        keyIsAtItsPlaceWarning();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Megerősítés");
                        alert.setHeaderText("");
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nem");
                        String ownerName=k.getOwnerName();
                        if (button.getText().matches("1")) {
                            alert.setContentText("Biztosan visszahozta " + ownerName+ " az " + button.getText() + "-es kulcsot?");
                        } else if (button.getText().matches("5")) {
                            alert.setContentText("Biztosan visszahozta " + ownerName + " az " + button.getText() + "-ös kulcsot?");
                        } else if (button.getText().matches("3|13|23|8|18|28")) {
                            alert.setContentText("Biztosanvisszahozta " + ownerName + " a " + button.getText() + "-as kulcsot?");
                        } else if (button.getText().matches("15|25")) {
                            alert.setContentText("Biztosan visszahozta " + ownerName + " a " + button.getText() + "-ös kulcsot?");
                        } else if (button.getText().matches("6|16|26")) {
                            alert.setContentText("Biztosan visszahozta " + ownerName + "a " + button.getText() + "-as kulcsot?");
                        } else {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " a " + button.getText() + "-es kulcsot?");
                        }
                        Optional<ButtonType> result = alert.showAndWait();
                        keyBack(keyID, result, button);
                    }
                }
            });
        }
    }

    private void createMcabinets(int num, FlowPane mkeysflow) {
        for(int i=1; i<=num; i++){
            Button button= new Button(""+i);
            button.setId("button"+i);
            mkeysflow.getChildren().add(button);
            manCabinets.add(button);
            button.getStyleClass().add("enableKey");

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int keyID = Integer.parseInt(button.getText()) - 1;
                    Key k = keys.get(keyID);
                    if (k.getOwnerName().equals("")) {
                        keyIsAtItsPlaceWarning();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Megerősítés");
                        alert.setHeaderText("");
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nem");
                        if (button.getText().matches("1")) {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza az " + button.getText() + "-es kulcsot?");
                        } else if (button.getText().matches("5")) {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza az " + button.getText() + "-ös kulcsot?");
                        } else if (button.getText().matches("3|13|23|8|18")) {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza a " + button.getText() + "-as kulcsot?");
                        } else if (button.getText().matches("15")) {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza a " + button.getText() + "-ös kulcsot?");
                        } else if (button.getText().matches("6|16")) {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza a " + button.getText() + "-os kulcsot?");
                        } else {
                            alert.setContentText("Biztosan " + k.getOwnerName() + " hozta vissza a " + button.getText() + "-es kulcsot?");
                        }
                        Optional<ButtonType> result = alert.showAndWait();
                        keyBack(keyID, result, button);
                    }
                }
            });
        }
    }

    private void keyIsAtItsPlaceWarning(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Nincs kiadva");
        alert.setHeaderText(null);
        alert.setContentText("Kulcs a helyén!");
        alert.showAndWait();
    }

    private void keyBack(int keyID, Optional<ButtonType> result, Button button) {
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            db.geyBroughtBack(keyID+1);
            button.getStyleClass().remove("disableKey");
            button.getStyleClass().add("enableKey");
            keys.get(keyID).setOwnerName("");
            refreshKeys();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    //create the table
    private void setTable() {
        productNameCell = new TableColumn("Termék neve");
        productNameCell.setMinWidth(200);
        productNameCell.setPrefWidth(300);
        productNameCell.setCellFactory(TextFieldTableCell.forTableColumn());
        productNameCell.setCellValueFactory(new PropertyValueFactory<Product, String>("prductName"));

        productNameCell.setSortType(TableColumn.SortType.ASCENDING);

        TableColumn price = new TableColumn("Termék ára");
        price.setMinWidth(200);
        price.setPrefWidth(300);
        price.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setCellValueFactory(new PropertyValueFactory<Product, String>("productPrice"));

        TableColumn quentity = new TableColumn("Termék darabszáma");
        quentity.setMinWidth(200);
        quentity.setPrefWidth(300);
        quentity.setCellFactory(TextFieldTableCell.forTableColumn());
        quentity.setCellValueFactory(new PropertyValueFactory<Product, String>("productQuantity"));

        TableColumn limit = new TableColumn("Termék utánrendelési limitje");
        limit.setMinWidth(200);
        limit.setPrefWidth(300);
        limit.setCellFactory(TextFieldTableCell.forTableColumn());
        limit.setCellValueFactory(new PropertyValueFactory<Product, String>("limit"));

        TableColumn removeCol = new TableColumn( "Törlés" );
        removeCol.setMinWidth(75);

        Button deleteButton= new Button("a");

        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFactory =
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Product, String> param )
                    {
                        Button deleteButton = new Button( "Törlés" );
                        deleteButton.getStyleClass().add("round-red");
                        final TableCell<Product, String> cell = new TableCell<Product, String>()
                        {

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    deleteButton.setOnAction( (ActionEvent event ) ->
                                    {
                                        Product product = getTableView().getItems().get( getIndex() );

                                        mainScene.setOpacity(0.3);
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Kulcskiadás");
                                        alert.setHeaderText("Biztosan törölni szeretnéd a(z) " + product.getPrductName() + " nevű terméket?");
                                        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
                                        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
                                        Optional<ButtonType> result = alert.showAndWait();

                                        if (result.get() == ButtonType.OK) {
                                            db.deleteProduct(product.getId());
                                        }else{

                                        }
                                        mainScene.setOpacity(1);

                                        refreshProducts();
                                    } );
                                    setGraphic(deleteButton);
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );

        productsTable.getColumns().addAll(productNameCell, price, quentity, limit, removeCol);
        productsTable.setItems(data);

        quentity.setCellFactory(column -> {
            return new TableCell<Product, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        setText(item); //Put the String data in the cell

                        //We get here all the info of the Person of this row
                        Product p = getTableView().getItems().get(getIndex());

                        // Style all persons wich name is "Edgard"
                        if(Integer.parseInt(p.getProductQuantity())==0){
                            setStyle("-fx-background-color: #f9a4a4");
                        }else if (Integer.parseInt(p.getProductQuantity())<Integer.parseInt(p.getLimit())) {
                            setStyle("-fx-background-color: #f6f99a"); //The background of the cell in yellow
                        } else {
                            setStyle("-fx-background-color: #bef999");
                        }
                    }
                }
            };
        });

        productNameCell.setCellFactory(column -> {
            return new TableCell<Product, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty

                        setText(item); //Put the String data in the cell

                        //We get here all the info of the Person of this row
                        Product p = getTableView().getItems().get(getIndex());

                        // Style all persons wich name is "Edgard"
                        if(Integer.parseInt(p.getProductQuantity())==0){
                            setStyle("-fx-background-color: #f9a4a4");
                        }else if (Integer.parseInt(p.getProductQuantity())<Integer.parseInt(p.getLimit())) {
                            setStyle("-fx-background-color: #f6f99a"); //The background of the cell in yellow
                        } else {
                            setStyle("-fx-background-color: #bef999");
                        }
                    }
                }
            };
        });


        customerName = new TableColumn("Vendég neve");
        customerName.setMinWidth(245);
        customerName.setPrefWidth(245);
        customerName.setMaxWidth(245);
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

        productList.setVisibleRowCount(25);
        productList2.setVisibleRowCount(15);

        startDatePicker.setValue(LocalDate.now());

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(dateFormatPattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        startDatePicker.setConverter(converter);
    }

    //end initialize **********************************************************

    //LEASES **********************************************************

    @FXML
    public void enableSellLeaseButton(){
        LeaseType l= (LeaseType) leaseType.getSelectionModel().getSelectedItem();
        try {
            if (!l.getType().equals("")) {
                sellLeaseButton.setDisable(false);
                if(l.getType().equals("szoli")){
                    contiLeaseCheckBox.setVisible(false);
                    contiLeaseCheckBox.setSelected(false);
                    leaseReferenceNumber.setVisible(false);
                    contiLeaseNumber.setVisible(false);
                    contiLabel1.setVisible(false);
                    contiLabel2.setVisible(false);
                }else {
                    contiLeaseCheckBox.setVisible(true);
                }
            }
        }catch (Exception e){

        }
    }

    @FXML
    private void giveOutKey(MouseEvent mouseEvent) {
        mainScene.setOpacity(0.3);
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
                            mainScene.setOpacity(1);
                        }catch (Exception e){
                            keyAlreadyInUseError();
                        }
                    } else {
                        mainScene.setOpacity(1);
                    }
                }
            }

        }catch (Exception e){
            chooseDressingRoomTypeError();
        }
    }

    @FXML
    private void giveOutKey2(MouseEvent mouseEvent) {
        mainScene.setOpacity(0.3);
        String roomType = null;
        int actualKey=0;
        try {
            roomType = (String) dressingRoomType2.getSelectionModel().getSelectedItem();
            if(roomType.equals("férfi") && keyNumber2.getValue()>24){
                wrongKeyNumberError();
            }else{
                if (!roomType.equals(null)) {
                    TextInputDialog dialog = new TextInputDialog("Vezetéknév Keresztnév");
                    dialog.setTitle("Vendég neve");
                    dialog.setHeaderText("Mi a vendég neve?");
                    dialog.setContentText("Írd be a vendég nevét:");
                    ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Rendben");
                    ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
                    String name=null;
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        name=result.get();
                        actualKey=keyNumber2.getValue();
                        System.out.println(actualKey);
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
                            mainScene.setOpacity(1);
                        }catch (Exception e){
                            keyAlreadyInUseError();
                            mainScene.setOpacity(1);
                        }
                    }
                    } else {
                        mainScene.setOpacity(1);
                    }
                }
            mainScene.setOpacity(1);
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
        mainScene.setOpacity(1);
    }

    private void chooseDressingRoomTypeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Nem választottad ki az öltöző típusát!");
        alert.setContentText("Válaszd ki, hogy férfi vagy női öltöző");
        alert.showAndWait();
        mainScene.setOpacity(1);
    }

    private void wrongKeyNumberError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Csak 24 férfi öltöző van");
        alert.setContentText("Válassz érvényes sorszámú férfi öltözőt!");
        alert.showAndWait();
        mainScene.setOpacity(1);
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
        mainScene.setOpacity(0.3);
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
            mainScene.setOpacity(1);
        }else{
            mainScene.setOpacity(1);
        }
    }

    public void sellLease(){
        mainScene.setOpacity(0.3);

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
            LocalDate chosenDay=startDatePicker.getValue();

            startDate=chosenDay.format(DateTimeFormatter.ofPattern(dateFormatPattern));

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                cal.setTime(sdf.parse(startDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            endDate=calculateEndDate(actualLease.getId(), cal);

            System.out.println(startDate);

            if(Integer.parseInt(actualLease.getId())==1){
                remainingTime=10000;
            }else{
                remainingTime=10;
            }

            int discount=0;

            if(contiLeaseCheckBox.isSelected()) {
                int contiPrice = db.getContiPrice(Integer.parseInt(actualLease.getId()));
                discount=Integer.parseInt(actualLease.getPrice())-contiPrice;
                String ticketType=null;
                if(actualLease.getType().equals("havi kondi")){
                    ticketType="A";
                }else if(actualLease.getType().equals("10 alkalmas kondi")){
                    ticketType="B";
                }else if(actualLease.getType().equals("szauna")){
                    ticketType="D";
                }else if(actualLease.getType().equals("sószoba")){
                    ticketType="F";
                }
                System.out.println(ticketType);

               int referenceNumber = leaseReferenceNumber.getValue();
               int leaseNumber = contiLeaseNumber.getValue();


                db.addConti(ticketType, leaseNumber, c.getName(), referenceNumber,discount, startDate);
            }

            db.addNewLease(Integer.parseInt(c.getCustomerID()), Integer.parseInt(actualLease.getId()), startDate, endDate, remainingTime, price, actualLease.getType(), discount);

            alert2.setTitle("Sikeres bérleteladás!");
            alert2.setHeaderText(null);
            int pay=price-discount;
            alert2.setContentText("A fizetendő összeg: "+ pay + " Ft");

            alert2.showAndWait();
            clearLeaseSale();
            mainScene.setOpacity(1);
        } else {
            mainScene.setOpacity(1);
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

    private void refreshStartDate() {

        startDatePicker.setValue(LocalDate.now());
    }

    public void contiLeaseDatas(ActionEvent actionEvent) {
        if(contiLeaseCheckBox.isSelected()) {
            contiLabel1.setVisible(true);
            contiLabel2.setVisible(true);
            leaseReferenceNumber.setVisible(true);
            contiLeaseNumber.setVisible(true);
        }else{
            contiLabel1.setVisible(false);
            contiLabel2.setVisible(false);
            leaseReferenceNumber.setVisible(false);
            contiLeaseNumber.setVisible(false);
        }
    }

    public void clearLeaseSale(){
        sellLeaseButton.setDisable(true);
        leaseType.getSelectionModel().clearSelection();
        contiLeaseCheckBox.setSelected(false);
        contiLeaseCheckBox.setVisible(false);
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
    public void activateTicketButton() {
        sellTicket.setDisable(false);
        try {
            actualTicketType = (String) ticketType.getSelectionModel().getSelectedItem();
            if (actualTicketType.equals("kondi") || actualTicketType.equals("szauna") || actualTicketType.equals("sószoba")) {
                dailyConti.setVisible(true);

            } else {
                dailyConti.setVisible(false);
                dailyConti.setSelected(false);
                contiLabel.setVisible(false);
                dailyContiReference.setVisible(false);
                contiTicketNumber.setVisible(false);
                dailyContiTicketNumber.setVisible(false);
            }
        } catch (Exception e) {
            dailyConti.setVisible(false);
            contiLabel.setVisible(false);
            dailyContiReference.setVisible(false);
            contiTicketNumber.setVisible(false);
            dailyContiTicketNumber.setVisible(false);
        }
    }

    public void dailyContiEnable(ActionEvent actionEvent) {
        if(dailyConti.isSelected()){
            contiLabel.setVisible(true);
            dailyContiReference.setVisible(true);
            contiTicketNumber.setVisible(true);
            dailyContiTicketNumber.setVisible(true);
        }else{
            contiLabel.setVisible(false);
            dailyContiReference.setVisible(false);
            contiTicketNumber.setVisible(false);
            dailyContiTicketNumber.setVisible(false);
        }
    }

    @FXML
    public void sellDailyTicket(){
        mainScene.setOpacity(0.3);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jegyeladás");
        alert.setHeaderText("Biztosan " + dailyTicketQuantity.getValue() + " db " + ticketType.getSelectionModel().getSelectedItem() + " jegyet szeretnél eladni?");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            int price=getTicketPrice();
            int discount=0;
            if(dailyConti.isSelected()){
                discount=getDiscount()*dailyTicketQuantity.getValue();
                String ticketType=null;
                if(actualTicketType.equals("kondi")){
                    ticketType="C";
                }else if(actualTicketType.equals("szauna")){
                    ticketType="E";
                }else if(actualTicketType.equals("sószoba")){
                    ticketType="G";
                }
                int ticketNumber=dailyContiTicketNumber.getValue();
                int referenceNumber=dailyContiReference.getValue();
                String name=null;

                TextInputDialog dialog = new TextInputDialog("Kersztnév Vezetéknév");
                dialog.setTitle("Vásárló neve");
                dialog.setHeaderText("Vásárló neve");
                dialog.setContentText("Írd be a vásárló nevét:");
                ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("Rendben");
                ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Mégse");

                Optional<String> result2 = dialog.showAndWait();
                if (result2.isPresent()){
                    name="" + result2.get();
                    db.addConti(ticketType, ticketNumber, name, referenceNumber, discount);
                }
            }

            System.out.println(discount);
            System.out.println(price);
            db.sellDailyTicket(price, actualTicketType, discount);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikeres jegyeladás!");
            alert2.setHeaderText(null);
            int amount=price-discount;
            alert2.setContentText("A fizetendő összeg: " + amount + " Ft");

            alert2.showAndWait();
            clearDailyTicketSale();
            mainScene.setOpacity(1);
        } else {
            mainScene.setOpacity(1);
        }
    }

    private DailyTicket dailyTicket=null;

    private int getDiscount() {
        int dc=0;
        dc=dailyTicket.getContiDiscount();
        return dc;
    }

    public int getTicketPrice(){
        dailyTicket= new DailyTicket(actualTicketType);
        return dailyTicket.getPrice()*dailyTicketQuantity.getValue();
    }

    private void clearDailyTicketSale(){
        ticketType.getSelectionModel().clearSelection();
        dailyTicketQuantity.getValueFactory().setValue(1);
        sellTicket.setDisable(true);
        dailyConti.setSelected(false);
    }
    //end ticket ******************************************************

    //KEY **********************************************************

    private void refreshKeys(){
        ArrayList<Key> usedKeys=db.getKeys();
        for(Key kUsed:usedKeys){
            for(Key k: keys){
                if(kUsed.getNumber().equals(k.getNumber())){
                    keys.get(Integer.parseInt(k.getNumber())-1).setOwnerName(kUsed.getOwnerName());
                }
            }
        }
        for(Key k:keys){
            Button b=null;
            int num=Integer.parseInt(k.getNumber());
            if(!k.getOwnerName().equals("")){
                if(num<=24) {
                    b = manCabinets.get(num-1);
                    b.getStyleClass().remove("enableKey");
                    b.getStyleClass().add("disableKey");
                }else{
                    b = womanCabinets.get(num - 25);
                    b.getStyleClass().remove("enableKey");
                    b.getStyleClass().add("disableKey");
                }
            }
        }
    }

    //end key **********************************************************

    //OTHER SELL **********************************************************

    @FXML
    public void activateOtherSellButton(KeyEvent keyEvent) {
        if(!otherProductName.getText().equals("")) {
            otherProductSellButton.setDisable(false);
        }else{
            otherProductSellButton.setDisable(true);
        }
    }

    @FXML
    public void sellOtherItem(MouseEvent mouseEvent) {
        mainScene.setOpacity(0.3);
        String name=otherProductName.getText();
        int price=otherProductAmount.getValue();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Megerősítés");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Igen");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nem");
        alert.setHeaderText("");
        alert.setContentText("Biztosan " + name + "-t adtál el " + price + " Ft értékben?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            // ... user chose OK
            db.sellOtherProduct(name, price);

            otherProductName.clear();
            otherProductAmount.getValueFactory().setValue(1000);
            otherProductSellButton.setDisable(true);
            mainScene.setOpacity(1);
        } else {
            // ... user chose CANCEL or closed the dialog
            mainScene.setOpacity(1);
        }
    }

    //end other sell **********************************************************

    //PRODUCT **********************************************************

    public void addNewProduct(MouseEvent mouseEvent) {
        try {
            String name = productName.getText();
            int price = Integer.parseInt(productPrice.getText());
            int limit = Integer.parseInt(productLimit.getText());
            Product newProduct = new Product(name, price, limit);

            db.addNewProduct(newProduct);
            refreshProducts();
            productName.setText("");
            productPrice.setText("");
            productLimit.setText("");
        }catch (Exception e){
            wrongInputsError();
        }
    }

    private void refreshProducts(){
        data.clear();
        ArrayList<Product> products=db.getProducts();
        data.addAll(products);
        productsTable.getSortOrder().add(productNameCell);

        productList.getItems().clear();
        productList2.getItems().clear();

        Callback<ListView<Product>, ListCell<Product>> factory = lv -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getPrductName());
            }
        };
        productList.setCellFactory(factory);
        productList.setButtonCell(factory.call(null));
        productList.getItems().addAll(products);

        productList2.setCellFactory(factory);
        productList2.setButtonCell(factory.call(null));
        productList2.getItems().addAll(products);
    }

    public void addProductToStorage(MouseEvent mouseEvent) {
        try {
            Product p = (Product) productList.getSelectionModel().getSelectedItem();
            int id = Integer.parseInt(p.getId());
            int quantity = Integer.parseInt(productQuantity.getText());
            String name = p.getPrductName();
            db.addProductToStorage(id, quantity, name);
            refreshProducts();
            productList.getSelectionModel().clearSelection();
            productQuantity.setText("");
        }catch (Exception e){
            wrongInputsError();
        }
    }

    private void wrongInputsError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Helytelenül töltötted ki az adatokat vagy hagytál kitöltetlen mezőt!");
        alert.setContentText("Kérlek ellenőrizd őket!");

        alert.showAndWait();
    }

    public void productSell(MouseEvent mouseEvent) {
            Product p = (Product) productList2.getSelectionModel().getSelectedItem();
            int id = Integer.parseInt(p.getId());
            int amount = productAmount.getValue();
            String name = p.getPrductName();
            int price=Integer.parseInt(p.getProductPrice());
            price=price*amount;
            db.sellProduct(id, amount, name, price);
            refreshProducts();
            productList2.getSelectionModel().clearSelection();
            productAmount.getValueFactory().setValue(1);
            productSellButton.setDisable(true);

            db.addProductToIncome(name, price);
    }

    public void activateProductSellButton(ActionEvent actionEvent) {
        productSellButton.setDisable(false);
    }

    //end product

    //Statistic **********************************************************

    private void refreshStatistic(){
        int currentCash=db.getCurrentCash();
        int dailyIncome=db.getDailyIncome();
        int actualMonthIncome=db.getActualMonthIncome();
        int lastMonthIncome=db.getLastMonthIncome();
        int prevContiIncome=db.getContiSum();

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);


        currentCashLabel.setText(formatter.format(currentCash) + " Ft");
        dailyIncomeLabel.setText(formatter.format(dailyIncome) + " Ft");
        currentMonthIncomeLabel.setText(formatter.format(actualMonthIncome) + " Ft");
        lastMonthIncomeLabel.setText(formatter.format(lastMonthIncome) + " Ft");
        prevMonthConti.setText(formatter.format(prevContiIncome) + " Ft");
    }

    //end statistic**********************************************************

    private void test() {
        refreshProducts();
    }

    public void cancellation(MouseEvent mouseEvent) {
        int amount=cancellationAmount.getValue();
        db.cancellation(amount, mainScene);
        cancellationAmount.getValueFactory().setValue(1000);
    }

    public void createPDF(MouseEvent mouseEvent) {
        String DEST = "./conti.pdf";

            File file = new File(DEST);
            file.getParentFile().mkdirs();
        try {
            createPdf(DEST);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Kész!");
            alert2.setHeaderText(null);
            alert2.setContentText("Táblázat elkészült!");

            alert2.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    PdfPTable table = null;

        public void createPdf(String dest) throws IOException, DocumentException {
            table=new PdfPTable(100);
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
            document.open();
            Calendar cal=Calendar.getInstance();
            int month = cal.get(Calendar.MONTH); // beware of month indexing from zero
            int year  = cal.get(Calendar.YEAR);

            document.add(new Paragraph("Continental Automotive Hungary Kft részére"));
            document.add(new Paragraph("Összesítö lista: bérletekröl, Napi jegyekröl"));
            document.add(new Paragraph("hónap: " + year + "-" + month ));
            document.add(new Paragraph("\n\n"));

            setCell( "havi korlátlan konditerem bérlet", 8);
            setCell("10 alkalmas kondi bérlet 2 havi", 8);
            setCell("Napi jegy konditerem", 8);
            setCell("Szauna éves bérlet", 8);
            setCell("Szauna alkalmi", 8);
            setCell("Sószoba éves bérlet", 8);
            setCell("sószoba napi jegy", 8);
            setCell("Név", 15);
            setCell("Törzsszám", 8);
            setCell("Dátum", 11);
            setCell("Continentál által fizetendö Összeg", 10);

            ArrayList<ContiPDF> conti=new ArrayList<ContiPDF>();
            conti=db.getContiPays();
                for (ContiPDF c : conti) {
                    String type=c.getType();
                    switch (type) {
                        case "A":
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            break;
                        case "B":
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            break;
                        case "C":
                            setCell("", 8);
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            break;
                        case "D":
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            break;
                        case "E":
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            setCell("", 8);
                            break;
                        case "F":
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            setCell("", 8);
                            break;
                        case "G":
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell("", 8);
                            setCell(c.getType() + c.getLeaseNum(), 8);
                            break;
                    }
                    setCell(c.getName(), 15);
                    setCell(Integer.toString(c.getRefNum()), 8);
                    setCell(c.getSellDate(), 11);
                    setCell(Integer.toString(c.getPrice()) + " Ft", 10);
                }
            setCell( "Összesen:", 90);
            setCell(""+db.getContiSum() +" Ft", 10);
            document.add(table);
            document.close();
        }

    private void setCell(String text, int colspan) {
        PdfPCell cell=new PdfPCell(new Phrase(text));
        cell.setColspan(colspan);
        table.addCell(cell);
    }


    //Daily income
    private void setIncomeTable() {
        incomeName.setCellFactory(TextFieldTableCell.forTableColumn());
        incomeName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));

        incomeAmount.setCellFactory(TextFieldTableCell.forTableColumn());
        incomeAmount.setCellValueFactory(new PropertyValueFactory<Product, String>("income"));
    }

    private void refreshIncomeTable(){
        incomeData.clear();

        ArrayList<Income> income=db.getIncome();
        incomeData.addAll(income);

        incomeTableView.setItems(incomeData);
    }
    //end Daily income**********************************************************
}

