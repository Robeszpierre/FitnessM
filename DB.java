package FintessM;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;

import java.sql.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:FitnessMDB;create=true";
    final String URL2 = "jdbc:derby:FitnessMDB;shutdown=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    //create DB
    public DB() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println("" + ex);
        }

        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println("" + ex);
            }
        }

        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println("" + ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CUSTOMER", null);
            if(!rs.next())
            {
                createStatement.execute("create table customer(customer_ID INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(50) not null)");
                System.out.println("létrehozva customer");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "LEASE", null);
            if(!rs.next())
            {
                createStatement.execute("create table lease(lease_ID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,customer_ID INT not null, ticket_ID int not null, startDate date not null, endDate date not null, remaining_time int)");
                System.out.println("létrehozva lease");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "LEASE_TYPE", null);
            if(!rs.next())
            {
                createStatement.execute("create table lease_type(ticket_ID INT primary key not null, ticket_type varchar(20) not null, price int not null)");
                createStatement.execute("insert into lease_type (ticket_id, ticket_type, price)\n" +
                        "values (1, 'havi kondi', 7000),\n" +
                        "       (2, '10 alkalmas kondi', 5500),\n" +
                        "       (3, 'szoli', 3600),\n" +
                        "       (4, 'szauna', 7500),\n" +
                        "       (5, 'sószoba', 4000)");
                System.out.println("létrehozva lease type");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "KEYS", null);
            if(!rs.next())
            {
                createStatement.execute("create table keys(key_holder varchar(30) not null, key_number int unique not null)");
                System.out.println("létrehozva keys" +
                        "");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "PRODUCT", null);
            if(!rs.next())
            {
                createStatement.execute("create table product(product_ID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), product_name varchar(30) not null, price int not null, quantity int not null check (quantity>=0), limit int not null)");
                System.out.println("létrehozva product");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "INCOME", null);
            if(!rs.next())
            {
                createStatement.execute("create table income(income_name varchar(30), price int not null, selldate date not null)");
                System.out.println("létrehozva income");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTI", null);
            if(!rs.next())
            {
                createStatement.execute("create table conti(conti_ticket_ID INT not null unique , ticket_type varchar(20) not null, price int not null)");
                createStatement.execute("insert into conti (conti_ticket_id, ticket_type, price)\n" +
                        "                        values (1, 'havi kondi', 3500),\n" +
                        "                               (2, '10 alkalmas kondi', 2750),\n" +
                        "                               (3, 'szoli', 3600),     \n" +
                        "                               (4, 'szauna', 4500),    \n" +
                        "                               (5, 'sószoba', 2400)");
                System.out.println("létrehozva conti");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTI_TABLE", null);
            if(!rs.next())
            {
                createStatement.execute("create table conti_table(type varchar(1), lease_number int not null, name varchar(30), reference_number int not null , selldate date not null, price int not null)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a conti táblázat létrehozásakor.");
            System.out.println(""+ex);
        }

    }

    public void addCustomer(Customer customer){
        try {
            String sql = "insert into customer (name) values (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public void sellDailyTicket(int price, String incomeName, int discount){
        try {
            String sql = "insert into income (income_name, price, selldate) values (?, ?, CURRENT_DATE )";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, incomeName +" napi ");
            preparedStatement.setInt(2, price);
            preparedStatement.execute();

            if(discount>0) {
                addDiscount(discount);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj a napi eladás hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public ArrayList<Customer> getCustomers(){
        String sql = "select * from customer";
        ArrayList<Customer> customers = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            customers = new ArrayList<>();

            while (rs.next()){
                Customer actualPerson = new Customer(rs.getInt("customer_id"),rs.getString("name"));
                customers.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a customerek kiolvasásakor");
            System.out.println(""+ex);
        }
        return customers;
    }

    public ArrayList<LeaseType> getLeases(){
        String sql = "select * from lease_type";
        ArrayList<LeaseType> leases = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            leases = new ArrayList<>();

            while (rs.next()){
                LeaseType actualLease = new LeaseType(rs.getInt("ticket_id"),rs.getString("ticket_type"),rs.getInt("price"));
                leases.add(actualLease);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a customerek kiolvasásakor");
            System.out.println(""+ex);
        }
        return leases;
    }


    public void addNewLease(int customerID, int tickedID, String startDate, String endDate, int remainingTime, int price, String name, int discount) {
        try {
            String sql = "insert into LEASE (CUSTOMER_ID, TICKET_ID, STARTDATE, ENDDATE, REMAINING_TIME) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, tickedID);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, endDate);
            preparedStatement.setInt(5, remainingTime);
            System.out.println("sikeresen hozzaadtuk a berletet");
            preparedStatement.execute();

            sql = "insert into INCOME (INCOME_NAME, PRICE, SELLDATE) values (?, ?, CURRENT_DATE)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            System.out.println("sikeresen hozzaadtuk a berletet a bejövő jövedelemhez");
            preparedStatement.execute();

            if(discount>0) {
                addDiscount(discount);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj a bérlet eladásakor");
            System.out.println(""+ex);
        }
    }

    private void addDiscount(int discount){
        discount*=-1;
        try {
        String sql = "insert into income (income_name, price, selldate) values ('discount', ?, CURRENT_DATE )";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, discount);
        preparedStatement.execute();
            System.out.println("hozzadva a conti_table-hoz");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lease getGymValid(String customerID, int leaseID) {
        String sql = "select * from lease\n" +
                "        where customer_id="+customerID+" and ticket_id="+leaseID+" and ENDDATE>=CURRENT_DATE and STARTDATE<=CURRENT_DATE and remaining_time>0\n" +
                "        order by enddate fetch first row only";
        Lease lease = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            lease = new Lease(rs.getInt("lease_id"),rs.getInt("customer_id"),rs.getInt("ticket_id"),rs.getString("STARTDATE"),rs.getString("ENDDATE"),rs.getString("REMAINING_TIME"));
        } catch (SQLException ex) {
            //nincs érvényes bérlet
        }
        return lease;
    }

    public void addKeyHolder(String name, int actualKey) throws Exception {
        String sql = "insert into KEYS (KEY_HOLDER, KEY_NUMBER) values (?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, actualKey);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new Exception();
        }
    }

    public void minusCount(int leaseID) {
        try {
            String sql = "update lease set REMAINING_TIME = REMAINING_TIME-1 where LEASE_ID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, leaseID);
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("-1 alkalom");
            alert.setHeaderText(null);
            alert.setContentText("Sikeresen levontunk 1 alkalmat!");

            alert.showAndWait();
        } catch (SQLException ex) {
            System.out.println("nem sikerült levonni 1 napot");
            System.out.println(""+ex);
        }
    }

    public ArrayList<Key> getKeys() {
        String sql = "select * from keys";
        ArrayList<Key> keys = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            keys = new ArrayList<>();

            while (rs.next()){
                Key actualKey = new Key(rs.getInt("key_number"),rs.getString("key_holder"));
                keys.add(actualKey);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a kulcsok lekérdezésekor");
            System.out.println(""+ex);
        }
        return keys;
    }

    public void geyBroughtBack(int keyNumber) {
        try {
            String sql = "delete from keys where key_number=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, keyNumber);
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Visszahozták a kulcsot");
            alert.setHeaderText(null);
            alert.setContentText("Visszakerült a kulcs a helyére!");

            alert.showAndWait();
        } catch (SQLException ex) {
            System.out.println("nem sikerült újra elérhetővé tenni a kulcsot");
            System.out.println(""+ex);
        }
    }

    public void sellOtherProduct(String name, int price) {
        try {
            String sql = "insert into income (INCOME_NAME, PRICE, SELLDATE) VALUES (?, ?, current_date)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.execute();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikerült");
            alert2.setHeaderText(null);
            alert2.setContentText("Sikeresen eladtál " + name + "-t " + price + " Ft értékben!");
            alert2.showAndWait();
        } catch (SQLException ex) {
            System.out.println("nem sikerült egyéb terméket eladni");
            System.out.println(""+ex);
        }
    }

    public void addNewProduct(Product newProduct) {
        try {
            String sql = "insert into product (PRODUCT_NAME, PRICE, QUANTITY, LIMIT) values (?, ?, 0, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, newProduct.getPrductName());
            preparedStatement.setInt(2, Integer.parseInt(newProduct.getProductPrice()));
            preparedStatement.setInt(3, Integer.parseInt(newProduct.getLimit()));
            preparedStatement.execute();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikerült");
            alert2.setHeaderText(null);
            alert2.setContentText("Sikeresen hozzáadtad a " + newProduct.getPrductName() + " nevű terméket a raktárkészlethez!");
            alert2.showAndWait();
        } catch (SQLException ex) {
            System.out.println("nem sikerült a termáket hozzáadni a raktárkészlethez");
            System.out.println(""+ex);
        }
    }

    public ArrayList<Product> getProducts() {
        String sql = "select * from product order by lower(product_name)";
        ArrayList<Product> products = null;
        try {
            products=new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(sql);

            while (rs.next()){
                Product actualProduct = new Product(rs.getInt("PRODUCT_ID"),rs.getString("PRODUCT_NAME"),rs.getInt("PRICE"),rs.getInt("QUANTITY"),rs.getInt("LIMIT"));
                products.add(actualProduct);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a customerek kiolvasásakor");
            System.out.println(""+ex);
        }
        return products;
    }

    public void addProductToStorage(int id, int quantity, String name) {
        try {
            String sql = "update product set quantity=quantity+? where product_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikerült");
            alert2.setHeaderText(null);
            alert2.setContentText("Sikeresen hozzáadtál " + quantity + " db " + name +" nevű terméket a raktárkészlethez!");
            alert2.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sellProduct(int id, int amount, String name, int price) {
        try {
            String sql = "update product set quantity=quantity-? where product_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Sikerült");
            alert2.setHeaderText("Sikeresen eladtál " + amount + " db " + name +" nevű terméket!");
            alert2.setContentText("A fizetendő összeg: " + price + " Ft!");
            alert2.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba");
            alert.setHeaderText("Nincs elégendő termék készleten");
            alert.setContentText("Kérlek ellenőrizd!");

            alert.showAndWait();
        }
    }

    public void addProductToIncome(String name, int price) {
        try {
            String sql = "insert into INCOME(INCOME_NAME, PRICE, SELLDATE) VALUES (?, ?, CURRENT_DATE)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("nem sikerült a termáket hozzáadni a raktárkészlethez");
            System.out.println(""+ex);
        }
    }

    public int getDailyIncome() {
        int income=0;
        try {
            String sql = "select sum(PRICE) daily_income from income where SELLDATE=current_date and income_name not like 'discount'";
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            income=rs.getInt("daily_income");
        } catch (SQLException ex) {
            System.out.println("napi bevétel lekérdezési hiba");
            System.out.println("" + ex);
        }
        return income;
    }

    public int getCurrentCash() {
        int income=0;
        try {
            String sql = "select sum(PRICE) daily_income from income where SELLDATE=current_date";
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            income=rs.getInt("daily_income");
        } catch (SQLException ex) {
            System.out.println("kasszában lévő összeg lekérdezési hiba");
            System.out.println("" + ex);
        }
        return income;
    }

    public int getActualMonthIncome() {
        int income=0;
        try {
            String sql = "select sum(PRICE) actual_month from income where year(SELLDATE)=year(current_date) and month(SELLDATE)=month(current_date) and income_name not like 'discount'";
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            income=rs.getInt("actual_month");
        } catch (SQLException ex) {
            System.out.println("e havi bevétel lekérdezési hiba");
            System.out.println("" + ex);
        }
        return income;
    }

    public int getLastMonthIncome() {
        int income=0;
        try {
            String sql = "select sum(PRICE) last_month from income where year(SELLDATE)=year(current_date) and month(SELLDATE)=(month(current_date)-1) and income_name not like 'discount'";
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            income=rs.getInt("last_month");
        } catch (SQLException ex) {
            System.out.println("előző havi bevétel lekérdezési hiba");
            System.out.println("" + ex);
        }
        return income;
    }

    public void deleteProduct(String id) {
        try {
            String sql = "delete from product where product_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.execute();

        } catch (SQLException ex) {
            System.out.println("Valami baj van a termék törlésekor");
            System.out.println(""+ex);
        }
    }

    public  void shutDownDB(){
        try {
            conn = DriverManager.getConnection(URL2);
        } catch (SQLException e) {
            System.out.println("Sikeresen leállt az adatbázis");
        }
    }

    public int getContiPrice(int id) {
        int price=0;
        try {
            String sql = "select price from conti where conti_ticket_id=" + id;
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            price=rs.getInt("price");
        } catch (SQLException ex) {
            System.out.println("nem sikerült lekérni a contis bérlet árát");
            System.out.println(""+ex);
        }
        return price;
    }

    public void cancellation(int amount, SplitPane mainScene) {
        try {
            amount*=-1;
            String sql = "insert into INCOME(INCOME_NAME, PRICE, SELLDATE) VALUES ('sztornó', ?, CURRENT_DATE)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, amount);
            preparedStatement.execute();

            mainScene.setOpacity(0.3);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sztornózás");
            alert.setHeaderText(null);
            alert.setContentText("Sikeres sztornózás " + -amount + " Ft összegben!");
            alert.showAndWait();
            mainScene.setOpacity(1);
        } catch (SQLException ex) {
            System.out.println("nem sikerült a sztornózás");
            System.out.println(""+ex);
            mainScene.setOpacity(1);
        }
    }

    public void addConti(String ticketType, int ticketNumber, String name, int referenceNumber, int discount) {
        try {
            String sql = "insert into conti_table (TYPE, LEASE_NUMBER, NAME, REFERENCE_NUMBER, SELLDATE, PRICE) VALUES (?, ?, ?, ?, CURRENT_DATE , ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticketType);
            preparedStatement.setInt(2, ticketNumber);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, referenceNumber);
            preparedStatement.setInt(5, discount);
            preparedStatement.execute();

            System.out.println("sikeresen hozzáadva a conti táblához");

        } catch (SQLException ex) {
            System.out.println("nem sikerült a conti táblához hozzáadás");
            System.out.println(""+ex);
        }
    }

    public void addConti(String ticketType, int leaseNumber, String name, int referenceNumber, int discount, String startDate) {
        try {
            String sql = "insert into conti_table (TYPE, LEASE_NUMBER, NAME, REFERENCE_NUMBER, SELLDATE, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ticketType);
            preparedStatement.setInt(2, leaseNumber);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, referenceNumber);
            preparedStatement.setString(5, startDate);
            preparedStatement.setInt(6, discount);
            preparedStatement.execute();

            System.out.println("sikeresen hozzáadva a conti táblához");

        } catch (SQLException ex) {
            System.out.println("nem sikerült a conti táblához hozzáadás");
            System.out.println(""+ex);
        }
    }


    public ArrayList<ContiPDF> getContiPays() {
        ArrayList<ContiPDF> conti=new ArrayList<ContiPDF>();
        try {
            String sql = "select * from conti_table where year(SELLDATE)=year(current_date) and month(SELLDATE)=(month(current_date)-1)";
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                conti.add(new ContiPDF(rs.getString("type"), rs.getInt("lease_number"), rs.getString("name"), rs.getInt("reference_number"), rs.getString("selldate"), rs.getInt("price")));
            }
        } catch (SQLException ex) {
            System.out.println("nem sikerült lekérni a contis bérlet árát");
            System.out.println(""+ex);
        }
        return conti;
    }

    public int getContiSum() {
        int sum=0;
        try {
            String sql = "select sum(price) total from conti_table where year(SELLDATE)=year(current_date) and month(SELLDATE)=(month(current_date)-1)";
            ResultSet rs = createStatement.executeQuery(sql);
            rs.next();
            sum=rs.getInt("total");
        } catch (SQLException ex) {
            System.out.println("nem sikerült lekérni a contis bérlet össz árát");
            System.out.println(""+ex);
        }
        return sum;
    }

    public ArrayList<Income> getIncome() {
        ArrayList<Income> income=new ArrayList<Income>();
        try {
            String sql = "select INCOME_NAME, PRICE from INCOME where SELLDATE=current_date";
            ResultSet rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                income.add(new Income(rs.getString("income_name"), rs.getInt("price")));
            }
        } catch (SQLException ex) {
            System.out.println("nem sikerült lekérni a napi bevéfteltt");
            System.out.println(""+ex);
        }
        return income;
    }
}