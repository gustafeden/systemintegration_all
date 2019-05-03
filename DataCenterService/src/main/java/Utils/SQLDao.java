/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Models.Data;
import Models.DataCenter;
import Models.DataCenters;
import Models.EnergyReport;
import Models.TempReport;
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

/**
 *
 * @author gusta
 */
public class SQLDao {

    Properties p;

    public SQLDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            p = new Properties();
            p.load(new FileInputStream("C:\\Users\\gusta\\Documents\\Nackademin\\Nätverksprogrammering\\NetBeansProjects\\DataCenterService\\src\\main\\java\\Utils\\settings.properties"));

        } catch (FileNotFoundException fex) {
            fex.printStackTrace();
        } catch (IOException IOex) {
            IOex.printStackTrace();
        } catch (ClassNotFoundException CLex) {
            CLex.printStackTrace();
        }
    }

    public List<DataCenter> ListDataCenters() {
        List<DataCenter> dcs = new ArrayList<>();
        String query = "SELECT id,name, UNIX_TIMESTAMP(created) as created FROM datacenters;";
        try {
            ResultSet rs = executeSQLQuery(query);
            while (rs.next()) {
                Date date;
                Timestamp ts = new Timestamp(rs.getLong("created"));
                date = ts;
                dcs.add(new DataCenter(rs.getInt("id"), rs.getString("name"), date));
            }

            closeRS(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dcs;
    }

    public Data getDCCurrentTemp(int id) {
        Data data = null;
        String query = "SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, data.temperature FROM datacenterdb.data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "order by created DESC LIMIT 1;";
        try {
            ResultSet rs = executeSQLQuery(query);

            if (!rs.next()) {
                data = new Data();
            } else {
                Date date;
                date = new Date(rs.getLong("created") * 1000);
                data = new Data(rs.getInt("id"), date, rs.getFloat("temperature"), rs.getString("name"));
            }

            closeRS(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Data getDCCurrentEnergy(int id) {
        Data data = null;
        String query = "SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, data.wattage FROM datacenterdb.data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "order by created DESC LIMIT 1;";
        try {
            ResultSet rs = executeSQLQuery(query);

            if (!rs.next()) {
                data = new Data();
            } else {
                Date date;
                date = new Date(rs.getLong("created") * 1000);
                data = new Data(rs.getInt("id"), date, rs.getInt("wattage"), rs.getString("name"));
            }

            closeRS(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Data getDCCurrentEnergyCost(int id) {
        Data data = null;
        String query = "SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, energycosts.price, energycosts.price*data.wattage/1000 as 'totalprice' FROM datacenterdb.data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "order by created DESC LIMIT 1;";
        try {
            ResultSet rs = executeSQLQuery(query);

            if (!rs.next()) {
                data = new Data();
            } else {
                Date date;
                date = new Date(rs.getLong("created") * 1000);
                data = new Data(rs.getInt("id"), date, rs.getString("name"), rs.getFloat("totalprice"), rs.getFloat("price"));
            }

            closeRS(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Data getDCCurrentFullReport(int id) {
        Data data = null;
        String query = "SELECT data.id, UNIX_TIMESTAMP(data.created) as created, datacenters.name, energycosts.price , energycosts.price*data.wattage/1000 as 'totalprice', data.temperature, data.wattage FROM datacenterdb.data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "order by created DESC LIMIT 1;";
        try {
            ResultSet rs = executeSQLQuery(query);
            if (!rs.next()) {
                data = new Data();
            } else {
                Date date;
                date = new Date(rs.getLong("created") * 1000);
                data = new Data(rs.getInt("id"), date, rs.getString("name"), rs.getFloat("totalprice"), rs.getFloat("price"));
                data.setTemperature(rs.getFloat("temperature"));
                data.setWattage(rs.getInt("wattage"));
            }
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public TempReport getDCFullTempReport(int id) {
        TempReport data = new TempReport();
        List<Data> datalist = new ArrayList<>();
        String query1 = "SELECT data.id, UNIX_TIMESTAMP(data.created) as 'timestamp', data.created, datacenters.name,data.temperature from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "where datacenters.id = '" + id + "' \n"
                + "having created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "LIMIT 24;";

        String query2 = "SELECT AVG(data.temperature) as 'average', MAX(data.temperature) as 'max', MIN(data.temperature) as 'min' from data\n"
                + "where data.datacenterid = '" + id + "' and data.created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "LIMIT 24;";
        try {
            ResultSet rs1 = executeSQLQuery(query1);
            ResultSet rs2 = executeSQLQuery(query2);
            if (rs2.next()) {
                data.setAvgTemp(rs2.getFloat("average"));
                data.setMaxTemp(rs2.getFloat("max"));
                data.setMinTemp(rs2.getFloat("min"));
            }
            closeRS(rs2);

            if (rs1.next()) {
                do {
                    Date date;
                    date = new Date(rs1.getLong("timestamp") * 1000);
                    Data d = new Data(rs1.getInt("id"), date, rs1.getFloat("temperature"), rs1.getString("name"));
                    datalist.add(d);
                } while (rs1.next());
            }
            data.setValues(datalist);
            closeRS(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Data getDCMaxEnergyCost() {
        Data data = null;
        String query = "select datacenters.name, SUM(energycosts.price*data.wattage/1000) as 'totalprice', SUM(data.wattage) as 'totalwattage'\n"
                + "from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where data.created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "group by datacenters.id\n"
                + "order by totalprice DESC LIMIT 1;";
        try {
            ResultSet rs = executeSQLQuery(query);
            if (!rs.next()) {
                data = new Data();
            } else {

                data = new Data();
                data.setWattage(rs.getInt("totalwattage"));
                data.setDatacenter(rs.getString("name"));
                data.setEnergycost(rs.getFloat("totalprice"));
            }
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public EnergyReport getAllDcsEnergyCosts() {
        EnergyReport rep = new EnergyReport();
        List<Data> datalist = new ArrayList<>();
        String query = "select datacenters.name, SUM(energycosts.price*data.wattage/1000) as 'totalprice', SUM(data.wattage) as 'totalwattage'\n"
                + "from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where data.created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "group by datacenters.id\n"
                + "order by datacenters.name;";
        try {
            ResultSet rs = executeSQLQuery(query);
            if (!rs.next()) {
                rep = new EnergyReport();
            } else {
                do {
                    Data data = new Data();
                    data.setWattage(rs.getInt("totalwattage"));
                    data.setDatacenter(rs.getString("name"));
                    data.setEnergycost(rs.getFloat("totalprice"));
                    datalist.add(data);
                    rep.setDatalist(datalist);
                } while (rs.next());

            }
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rep;
    }

    public EnergyReport getDCFullEnergyReport(int id) {
        EnergyReport rep = new EnergyReport();
        String query2 = "SELECT AVG(data.wattage) as 'average', MAX(data.wattage) as 'max', MIN(data.wattage) as 'min' from data\n"
                + "where data.datacenterid = '" + id + "' and data.created >= ( CURDATE() - INTERVAL 1 DAY );";

        try {
            ResultSet rs2 = executeSQLQuery(query2);
            if (rs2.next()) {
                rep.setAvgVal(rs2.getFloat("average"));
                rep.setMaxVal(rs2.getFloat("max"));
                rep.setMinVal(rs2.getFloat("min"));
            }
            closeRS(rs2);
            rep.setDatalist(getLastDayEnergyInfo(id));
            rep.setMostExpensive(getLastDayMaxEnergy(id));
            rep.setLeastExpensive(getLastDayLeastEnergy(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rep;

    }

    private Data getLastDayLeastEnergy(int id) {
        Data d = new Data();
        String query4 = "SELECT data.id, UNIX_TIMESTAMP(data.created) as 'timestamp', data.created, datacenters.name,data.wattage, energycosts.price, \n"
                + "energycosts.price*data.wattage/1000 as 'totalprice' from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "having created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "order by totalprice ASC LIMIT 1;";
        try {
            ResultSet rs4 = executeSQLQuery(query4);
            if (rs4.next()) {
                Date date;
                date = new Date(rs4.getLong("timestamp") * 1000);
                d.setId(rs4.getInt("id"));
                d.setCreated(date);
                d.setDatacenter(rs4.getString("name"));
                d.setWattage(rs4.getInt("wattage"));
                d.setHourPrice(rs4.getFloat("price"));
                d.setEnergycost(rs4.getFloat("totalprice"));
            }
            closeRS(rs4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    private Data getLastDayMaxEnergy(int id) {
        Data d = new Data();
        String query3 = "SELECT data.id, UNIX_TIMESTAMP(data.created) as 'timestamp', data.created, datacenters.name,data.wattage, energycosts.price, \n"
                + "energycosts.price*data.wattage/1000 as 'totalprice' from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where datacenters.id = '" + id + "'\n"
                + "having created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "order by totalprice DESC LIMIT 1;";
        try {
            ResultSet rs3 = executeSQLQuery(query3);
            if (rs3.next()) {
                Date date;
                date = new Date(rs3.getLong("timestamp") * 1000);
                d.setId(rs3.getInt("id"));
                d.setCreated(date);
                d.setDatacenter(rs3.getString("name"));
                d.setWattage(rs3.getInt("wattage"));
                d.setHourPrice(rs3.getFloat("price"));
                d.setEnergycost(rs3.getFloat("totalprice"));
            }
            closeRS(rs3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;

    }

    private List<Data> getLastDayEnergyInfo(int id) {
        List<Data> datalist = new ArrayList<>();
        String query1 = "SELECT data.id, UNIX_TIMESTAMP(data.created) as 'timestamp', data.created, datacenters.name,data.wattage, energycosts.price, \n"
                + "energycosts.price*data.wattage/1000 as 'totalprice' from data\n"
                + "inner join datacenters\n"
                + "on datacenters.id = data.datacenterid\n"
                + "inner join energycosts\n"
                + "on energycosts.id = data.energycostid\n"
                + "where datacenters.id = '" + id + "' \n"
                + "having created >= ( CURDATE() - INTERVAL 1 DAY )\n"
                + "order by created DESC LIMIT 24;";
        try {
            ResultSet rs1 = executeSQLQuery(query1);
            if (rs1.next()) {
                do {
                    Date date;
                    date = new Date(rs1.getLong("timestamp") * 1000);
                    Data d = new Data(rs1.getInt("id"), date, rs1.getString("name"), rs1.getFloat("totalprice"), rs1.getFloat("price"));
                    d.setWattage(rs1.getInt("wattage"));

                    datalist.add(d);
                } while (rs1.next());
            }

            closeRS(rs1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datalist;
    }

    public Data getDCCurrentEnergyPrice() {
        Data data = null;
        String query = "SELECT id, price, UNIX_TIMESTAMP(created) as created FROM datacenterdb.energycosts\n"
                + "order by created DESC limit 1;";
        try {
            ResultSet rs = executeSQLQuery(query);
            if (!rs.next()) {
                data = new Data();
            } else {
                Date date;
                date = new Date(rs.getLong("created") * 1000);
                data = new Data(rs.getInt("id"), date, rs.getFloat("price"));
            }
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean addNewData(Data data) {
        Boolean ret = false;
        String query = "INSERT INTO data (`datacenterid`, `energycostid`, `temperature`, `wattage`)\n"
                + "SELECT (select datacenters.id from datacenters where name like '" + data.getDatacenter() + "'), (select energycosts.id "
                + "from energycosts order by created DESC LIMIT 1), '" + data.getTemperature() + "', '" + data.getWattage() + "'";

        int result = executeSQLUpdate(query);
        if (result != 0) {
            ret = true;
        }
        return ret;
    }

    public boolean addNewEnergyPrice(Data data) {
        Boolean ret = false;
        String query = "INSERT INTO energycosts (`price`) VALUES ('" + data.getHourPrice() + "');";
        int result = executeSQLUpdate(query);
        if (result != 0) {
            ret = true;
        }
        return ret;
    }

    public DataCenters getAllDcs() {
        DataCenters dcs = new DataCenters();
        String query = "SELECT id,name, UNIX_TIMESTAMP(created) as created FROM datacenters;";
        try {
            ResultSet rs = executeSQLQuery(query);
            if (rs.next()) {
                do {
                    //System.out.println(rs.getString("id") + " : " + rs.getLong("created") + " : " + rs.getString("name"));
                    Date date;
                    date = new Date(rs.getLong("created") * 1000);
                    dcs.addDataCenter(new DataCenter(rs.getInt("id"), rs.getString("name"), date));
                } while (rs.next());
            }
            closeRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dcs;
    }

    private void closeRS(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private ResultSet executeSQLQuery(String query) {
        ResultSet rs = null;
        CachedRowSetImpl crs = null;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement(query);) {
            rs = stmt.executeQuery();
            crs = new CachedRowSetImpl();
            crs.populate(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crs;
    }

    private int executeSQLUpdate(String query) {
        int result = 0;
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                p.getProperty("username"),
                p.getProperty("password"));
                PreparedStatement stmt = con.prepareStatement(query);) {
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
