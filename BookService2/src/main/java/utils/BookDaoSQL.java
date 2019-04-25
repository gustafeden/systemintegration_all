/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import Models.Book;
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
import java.util.List;
import java.util.Properties;
/**
 *
 * @author gusta
 */
public class BookDaoSQL{
   // 
    
    private static List<Book> booklist;
  //  ResultSet rs;
    Properties p;
    public BookDaoSQL(){
        booklist = new ArrayList<>();
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        p = new Properties();
        p.load(new FileInputStream("C:\\Users\\gusta\\Documents\\Nackademin\\Nätverksprogrammering\\NetBeansProjects\\BookService2\\src\\main\\java\\utils\\settings.properties"));
       //     System.out.println("initializing errthing");
       //     System.out.println(p.getProperty("connstring"));
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
        updateAllBooksFromSQL();

    }
    public List<Book> getAllBooks(){
        updateAllBooksFromSQL();
        List<Book> templist = new ArrayList<>();
        templist = booklist;
        return templist;
    }
    private void updateAllBooksFromSQL(){
        List<Book> templist = new ArrayList<>();
        
        /*
        try{
        p = new Properties();
        p.load(new FileInputStream("src/main/java/Utils/settings.properties"));
        System.out.println("updateBook "+p.size());
        }
        catch(FileNotFoundException fex){
            fex.printStackTrace();
        }
        catch(IOException IOex){
            IOex.printStackTrace();
        }*/
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connstring"),
                "gustafeden",
                "securepassword");
                PreparedStatement stmt = con.prepareStatement("SELECT id, title, author from books");
                ) {
           
                //stmt.setString(1,"");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    templist.add(new Book(id,title,author));

                }
                booklist = templist;
                if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
}
