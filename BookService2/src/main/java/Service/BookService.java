/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import Models.*;
import utils.BookDaoSQL;
import utils.Response;
import java.util.ArrayList;
import java.util.List; 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET; 
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces; 
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;  
/**
 *
 * @author gusta
 */
@Path("/BookService2")
public class BookService {
    private static BookDaoSQL bookDaoSql= new BookDaoSQL();

    public BookService() {
       // bookDao = new BookDAO();
      //  bookList =  bookDao.getAllBooks(); 
     // bookDaoSql = new BookDaoSQL();
    }
   
    
   @GET 
   @Path("/books") 
   @Produces(MediaType.APPLICATION_XML) 
   public List<Book> getBooks(){ 
       System.out.println("getBooks");
       List<Book> sqlbooklist = bookDaoSql.getAllBooks();
       return sqlbooklist;
   }  
    @GET 
   @Path("/booksJSON") 
   @Produces(MediaType.APPLICATION_JSON) 
   public List<Book> getBooksJSON(){ 
      List<Book> sqlbooklist = bookDaoSql.getAllBooks();
       return sqlbooklist;
   } 
   /*
   @GET
   @Path("booksJSON/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   public Book getBookByIdJSON(@PathParam("id") int id){
       Book res = new Book();
       res = bookDao.getBookByIdFunc(id, bookList);
       if(res == null)
           res = new Book();
       return res;
       
   }
    @GET
   @Path("books/{id}")
   @Produces(MediaType.APPLICATION_XML)
   public Book getBookByIdXML(@PathParam("id") int id){
       Book res = new Book();
       res = bookDao.getBookByIdFunc(id, bookList);
       if(res == null)
           res = new Book();
       return res;
       
   }
    @GET
   @Path("booksJSON/{from}/{to}")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Book> getBookIntervalJSON(@PathParam("from") int idFrom, @PathParam("to") int idTo){
       List<Book> res = new ArrayList<>();
       
        for(Book b : bookList){
            if (b.getId() >= idFrom && b.getId() <= idTo) {
                res.add(b);
            }
        }
        return res;
   }
     @GET
   @Path("booksJSON/interval")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Book> getBookInterval2JSON(@QueryParam("from") int idFrom, @QueryParam("to") int idTo){
       List<Book> res = new ArrayList<>();
       
        for(Book b : bookList){
            if (b.getId() >= idFrom && b.getId() <= idTo) {
                res.add(b);
            }
        }
        return res;
   }*/
   /*
   @GET
   @Path("booksJSON/{id}/del")
   @Produces(MediaType.APPLICATION_JSON)
      public Response deleteBookByIdJSON(@PathParam("id") int id){
          Response res = new Response("Book Deleted", Boolean.FALSE);
          //bookDao.deleteBookByIdFunc(id);
           res.setStatus(bookDao.deleteBookByIdFunc(id, bookList));
          //res.setStatus(Boolean.TRUE);
          /*
            for( int i = 0; i < bookList.size(); i++ )
                {
                    Book lValue = bookList.get( i );
                    if(lValue.getId() == id)
                    {
                        bookList.remove(lValue);
                        i--;
                        res.setStatus(Boolean.TRUE);
                    }  
                }
          *//*
          return res;
      }
      
      @POST
      @Path("books/add")
      public Response addBook(Book b){
            Response res = new Response("Book added", Boolean.FALSE);
            Integer index = bookDao.getBookIndexFunc(b.getId(),bookList);
            if(index != null){
                bookList.set(index, b);
                res.setMessage("Book updated");
            }else{
                bookList.add(b);
            }
            res.setStatus(Boolean.TRUE);
            
            return res;
      }
      */

}
