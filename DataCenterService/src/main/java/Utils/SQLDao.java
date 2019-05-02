/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import Models.Data;
import Models.DataCenter;
import Models.DataCenters;
import Models.TempReport;
import com.oracle.jrockit.jfr.DataType;
import com.sun.rowset.CachedRowSetImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.sql.Timestamp;   
/**
 *
 * @author gusta
 */
public class SQLDao {
    Properties p;
    
    public SQLDao(){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        p = new Properties();
        p.load(new FileInputStream("C:\\Users\\gusta\\Documents\\Nackademin\\Nätverksprogrammering\\NetBeansProjects\\DataCenterService\\src\\main\\java\\Utils\\settings.properties"));
    
        }
        catch(FileNotFoundException fex){
            fex.printStackTrace();
        }
        catch(IOException IOex){
            IOex.printStackTrace();
        }
        catch(ClassNotFoundException CLex){
            CLex.printStackTrace();
        }
    }
    public List<DataCenter> ListDataCenters(){
        List<DataCenter> dcs = new ArrayList<>();
         try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement("SELECT id,name, UNIX_TIMESTAMP(created) as created FROM datacenters;");
                ) {
                
                ResultSet rs = stmt.executeQuery();
                System.out.println("statement executed");
                while(rs.next()){
                    System.out.println(rs.getString("id") + " : " + rs.getLong("created") + " : " + rs.getString("name"));
                    Date date;
                    Timestamp ts = new Timestamp(rs.getLong("created"));
                    date = ts;
                    dcs.add(new DataCenter(rs.getInt("id"), rs.getString("name"), date));
                }
                
                if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return dcs;
    }
    
    public Data getDCCurrentTemp(int id){
        Data data = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement("SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, data.temperature FROM datacenterdb.data\n" +
                    "inner join datacenters\n" +
                    "on datacenters.id = data.datacenterid\n" +
                    "where datacenters.id = ?\n"
                    + "order by created DESC LIMIT 1;");
                ) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                    
                if(!rs.next()){
                    data = new Data();
                }else{
                    Date date;
                    date = new Date( rs.getLong("created") * 1000 );
                    data = new Data(rs.getInt("id"), date, rs.getFloat("temperature"), rs.getString("name"));
                }
                
                if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public Data getDCCurrentEnergy(int id){
         Data data = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement("SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, data.wattage FROM datacenterdb.data\n" +
                    "inner join datacenters\n" +
                    "on datacenters.id = data.datacenterid\n" +
                    "where datacenters.id = ?\n"
                        + "order by created DESC LIMIT 1;");
                ) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                    
                if(!rs.next()){
                    data = new Data();
                }else{
                    Date date;
                    date = new Date( rs.getLong("created") * 1000 );
                    data = new Data(rs.getInt("id"), date, rs.getInt("wattage"), rs.getString("name"));
                }
                
                if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public Data getDCCurrentEnergyCost(int id){
        Data data = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement("SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, energycosts.price as hourprice, energycosts.price*data.wattage/1000 as totalprice FROM datacenterdb.data\n" +
                    "inner join datacenters\n" +
                    "on datacenters.id = data.datacenterid\n" +
                    "inner join energycosts\n" +
                    "on energycosts.id = data.energycostid\n" +
                    "where datacenters.id = ?\n"
                        + "order by created DESC LIMIT 1;");
                ) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                    
                if(!rs.next()){
                    data = new Data();
                }else{
                    Date date;
                    date = new Date( rs.getLong("created") * 1000 );
                    data = new Data(rs.getInt("id"), date, rs.getString("name"), rs.getFloat("totalprice"), rs.getFloat("hourprice"));
                }
                
                if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public Data getDCCurrentFullReport(int id){
        Data data = null;
        String query = "SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, energycosts.price , energycosts.price*data.wattage/1000 as 'totalprice', data.temperature, data.wattage FROM datacenterdb.data\n" +
        "inner join datacenters\n" +
        "on datacenters.id = data.datacenterid\n" +
        "inner join energycosts\n" +
        "on energycosts.id = data.energycostid\n" +
        "where datacenters.id = '"+id+"'\n" +
        "order by created DESC LIMIT 1;";
         try{
            ResultSet rs = executeSQLQuery(query);
            if(!rs.next()){
                data = new Data();
            }else{
                Date date;
                date = new Date( rs.getLong("created") * 1000 );
                data = new Data(rs.getInt("id"), date, rs.getString("name"), rs.getFloat("totalprice"), rs.getFloat("price"));
                data.setTemperature(rs.getFloat("temperature"));
                data.setWattage(rs.getInt("wattage"));
            }
            closeRS(rs);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }
    public TempReport getDCFullTempReport(int id){
        TempReport data = new TempReport();
        List<Data> datalist = new ArrayList<>();
        String query1 = "SELECT data.id, UNIX_TIMESTAMP(data.created) as 'timestamp', data.created, datacenters.name,data.temperature from data\n" +
        "inner join datacenters\n" +
        "on datacenters.id = data.datacenterid\n" +
        "where datacenters.id = '"+id+"' \n" +
        "having created >= ( CURDATE() - INTERVAL 1 DAY )\n" +
        "LIMIT 24;";
        
        String query2 = "SELECT AVG(data.temperature) as 'average', MAX(data.temperature) as 'max', MIN(data.temperature) as 'min' from data\n" +
        "where data.datacenterid = '"+id+"' and data.created >= ( CURDATE() - INTERVAL 1 DAY )\n" +
        "LIMIT 24;";
         try{
            ResultSet rs1 = executeSQLQuery(query1);
            ResultSet rs2 = executeSQLQuery(query2);
            if(rs2.next()){
                data.setAvgTemp(rs2.getFloat("average")); data.setMaxTemp(rs2.getFloat("max")); data.setMinTemp(rs2.getFloat("min"));
            }
            closeRS(rs2);
            
            if(rs1.next()){
                do { 
                    Date date;
                    date = new Date( rs1.getLong("timestamp") * 1000 );
                    Data d = new Data(rs1.getInt("id"), date, rs1.getFloat("temperature"), rs1.getString("name"));
                    datalist.add(d);
                } while (rs1.next());
            }
            data.setValues(datalist);
            closeRS(rs1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }
    public Data getDCCurrentEnergyPrice(){
        Data data = null;
        String query = "SELECT id, price, UNIX_TIMESTAMP(created) as created FROM datacenterdb.energycosts\n" +
            "order by created DESC limit 1;";
        try{
            ResultSet rs = executeSQLQuery(query);
            if(!rs.next()){
                data = new Data();
            }else{
                Date date;
                date = new Date( rs.getLong("created") * 1000 );
                data = new Data(rs.getInt("id"), date, rs.getFloat("price"));
            }
            closeRS(rs);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }
    
    public boolean addNewData(Data data){
        Boolean ret = false;
        String query = "INSERT INTO data (`datacenterid`, `energycostid`, `temperature`, `wattage`)\n" +
            "SELECT (select datacenters.id from datacenters where name like '"+data.getDatacenter()+"'), (select energycosts.id "
                + "from energycosts order by created DESC LIMIT 1), '"+data.getTemperature()+"', '"+data.getWattage()+"'";
       
        int result = executeSQLUpdate(query);
        if(result != 0){
            ret = true;
        }
       return ret;
    }
    
    public DataCenters getAllDcs(){
        DataCenters dcs = new DataCenters();
        String query = "SELECT id,name, UNIX_TIMESTAMP(created) as created FROM datacenters;";
        try{
            ResultSet rs = executeSQLQuery(query);
            if(rs.next()){
                do {                    
                    //System.out.println(rs.getString("id") + " : " + rs.getLong("created") + " : " + rs.getString("name"));
                    Date date;
                    date = new Date( rs.getLong("created") * 1000 );
                    dcs.addDataCenter(new DataCenter(rs.getInt("id"), rs.getString("name"), date));
                } while (rs.next());
            }
            closeRS(rs);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
         return dcs;
    }
    private void closeRS(ResultSet rs){
        if(rs != null)
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private ResultSet executeSQLQuery(String query){
        ResultSet rs = null;
        CachedRowSetImpl crs = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement(query);
                ) {
                    rs = stmt.executeQuery();
                    crs = new CachedRowSetImpl();
                    crs.populate(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crs;
    }
    private int executeSQLUpdate(String query){
        int result = 0;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement(query);
                ) {
                    result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
