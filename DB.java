package FintessM;

import java.sql.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DB {
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:FitnessMDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;


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
                createStatement.execute("create table lease(customer_ID INT not null, ticket_ID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), startDate date not null, endDate date not null)");
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
                createStatement.execute("create table keys(customer_ID INT primary key not null, key_number int not null)");
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
                createStatement.execute("create table product(product_ID int not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), product_name varchar(30) not null, price int not null, limit int not null)");
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

    public void sellDailyTicket(int price, String incomeName){
        try {
            String sql = "insert into income (income_name, price, selldate) values (?, ?, CURRENT_DATE )";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, incomeName);
            preparedStatement.setInt(2, price);
            preparedStatement.execute();
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
}