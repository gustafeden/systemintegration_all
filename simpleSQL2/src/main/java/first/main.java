/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;


//Try with resources saves a lot of closing...
public class main {

    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException, InterruptedException {
        
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("connecting to mysql..");
        Properties p = new Properties();
        p.load(new FileInputStream("src/main/java/first/settings.properties"));
        
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        
        try (Connection con = DriverManager.getConnection(
                             p.getProperty("connectionstring"),
                             p.getProperty("name"),
                             p.getProperty("password"));
            Statement stmt =  con.createStatement();) {
            while(true){
                System.out.println("vem vill du kolla upp?");
                String namein = sc.nextLine();
                rs = stmt.executeQuery("select id, name, nice from child where name = '"+namein+"'");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String nice = rs.getInt("nice") == 0 ? "not nice" : "nice";

                    System.out.println("id: " + id + ", name: " + name + ", " + nice);
                }
                System.out.println("-----------------------------------------------------");
                
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
         finally {
            if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
           
        }
    }
    
}