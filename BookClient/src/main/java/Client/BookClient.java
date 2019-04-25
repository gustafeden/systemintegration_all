/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import Models.*;
import Utils.*;
/**
 *
 * @author gusta
 */
public class BookClient {
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource(
            UriBuilder.fromUri("http://localhost:8080/BookService").build());
    
    public static void main(String[] args) throws IOException {

        // getting XML data
        String xmlString = service.path("rest/BookService/books")
                .accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(xmlString);
        System.out.println();
        
        //Getting book objects
        Book[] bookArray = service.path("rest/BookService/books")
                .accept(MediaType.APPLICATION_XML).get(Book[].class);
        
        for (Book b : bookArray){
            System.out.println("Book id: "+b.getId()+ " title: "+b.getTitle() +" author: "+b.getAuthor());
        }
        
        //Converting an array to List
        List<Book> bookList = Arrays.asList(bookArray);
        
        Gson gson = new Gson();
        
        //Getting one book
        String jsonBook = service.path("rest/BookService/booksJSON/1")
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(jsonBook);
        System.out.println();
        
        Book book = gson.fromJson(jsonBook, Book.class);
        System.out.println("Book info, id: "+book.getId()+" title: "
                +book.getTitle() +" author: "+book.getAuthor()+ "\n");
        
        //Getting all books
        String jsonString = service.path("rest/BookService/booksJSON")
        .accept(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println(jsonString);
        System.out.println();
        
        Book[] books = gson.fromJson(jsonString, Book[].class);
        for (Book b : books){
            System.out.println("Book info, id: "+b.getId()+" title: "+b.getTitle() +" author: "+b.getAuthor());
        }
        
        //COnvert POJO to JSON
        Book myBook = new Book(13, "4 Hour WorkWeek", "Tim Ferriss");
        String convertedBook = gson.toJson(myBook);
        System.out.println(convertedBook);
        
        ClientResponse response = service.path("rest/BookService/books/add")
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, myBook);
        
        System.out.println("Response " + response.getEntity(String.class));
       
    }
}
