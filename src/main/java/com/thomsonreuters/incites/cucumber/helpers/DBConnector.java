package com.thomsonreuters.incites.cucumber.helpers;

import com.thomsonreuters.incites.cucumber.Base.Instances;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnector extends Instances {

    public static Connection connection;
    final private static String driverName = "oracle.jdbc.driver.OracleDriver";

    public static void getConnection() {
        try {
            // Load the SQL JDBC driver
            Class.forName(driverName);

            // Create a connection to the database
            String serverName = Instances.configReader.DB_HOST;
            String schema = configReader.DB_SERVICE_NAME;
/*
            String url = "jdbc:oracle:thin:@//"+serverName+":1521/"+schema;//
*/
            String url = "jdbc:oracle:thin:@//%s";
            url = String.format(url,serverName)+"%s";
            url = String.format(url,":1521/")+"%s";
            url = String.format(url,schema);
            String username = configReader.DB_USER_ID;
            String password = configReader.DB_PASSWORD;

            connection = DriverManager.getConnection(url, username, password);

            baseElements.grsReport.addSubStep("Successfully Connected to the database! URL: " + url);


        } catch (ClassNotFoundException e) {
            baseElements.grsReport.addException("Could not find the database driver " + e.getMessage(),false);
        } catch (SQLException e) {
            baseElements.grsReport.addException("Could not connect to the database " + e.getMessage(), false);
        }
    }


    public static List<String> getDataFromTable(String query) {
        List<String> datas = new ArrayList<String>();
        Statement statement = null;
        try {
            // Get a result set containing all data from test_table
            query =query.replace("\n","").replace("           ","");
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                datas.add(results.getString(1));
            }
            results.close();
        } catch (SQLException e) {
            baseElements.grsReport.addException("Could not retrieve data from the database " + e.getMessage(), false);
        }
        return datas;
    }

    public static List<String> getDataFromTable(String tableName, String ConditionColumnName, String ConditionValue, String DataField) {
        List<String> datas = new ArrayList<String>();
        Statement statement = null;
        try {
            // Get a result set containing all data from test_table
            statement = connection.createStatement();
            String query = "SELECT DISTINCT * FROM " + tableName.toUpperCase() + " where " + ConditionColumnName.toUpperCase() + "=\'" + ConditionValue + "\'";
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                datas.add(results.getString(DataField));
            }
        } catch (SQLException e) {
            baseElements.grsReport.addException("Could not retrieve data from the database " + e.getMessage(), false);
        }
        return datas;
    }

    public static void CloseConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            baseElements.grsReport.addException("Could not retrieve data from the database " + e.getMessage(), false);
        }
    }
}

