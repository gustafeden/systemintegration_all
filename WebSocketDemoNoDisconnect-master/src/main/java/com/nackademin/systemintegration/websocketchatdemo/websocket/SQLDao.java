/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nackademin.systemintegration.websocketchatdemo.websocket;

/**
 *
 * @author gusta
 */
import com.sun.rowset.CachedRowSetImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.sql.Timestamp;

public class SQLDao {
    
    
    public SQLDao(){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        
    }
      public ResultSet executeSQLQuery(String query) {
        ResultSet rs = null;
        CachedRowSetImpl crs = null;
         try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(
                "sysintinstance.c3ftwz9lwjxd.us-east-1.rds.amazonaws.com",
               "gustafeden",
                "SecurePassword!");
                PreparedStatement stmt = con.prepareStatement(query);) {
            rs = stmt.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crs;
    }

    public int executeSQLUpdate(String query) {
        int result = 0;
         try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://sysintinstance.c3ftwz9lwjxd.us-east-1.rds.amazonaws.com:3306/sysint",
               "gustafeden",
                "SecurePassword!");
                PreparedStatement stmt = con.prepareStatement(query);) {
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    /*
      public static void main(String[] args) {

          executeSQLUpdate("INSERT INTO DHT11sensor (temperature, humidity, deviceid) VALUES ('24', '40', '1');");
      }
*/
}
