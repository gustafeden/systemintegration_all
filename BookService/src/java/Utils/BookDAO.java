/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Models.Book;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author gusta
 */
public class BookDAO {
    public static List<Book> bookList;
    
    public BookDAO(){
        bookList = new ArrayList<>();
        Book b1 = new Book(1, "test1", "test2");
        Book b2 = new Book(2, "test1", "test2");
        Book b3 = new Book(3, "test1", "test2");
        Book b4 = new Book(4, "test1", "test2");
        bookList.add(b1);
        bookList.add(b2);
        bookList.add(b3);
        bookList.add(b4);
    }
    public List<Book> getAllBooks(){
        
        return bookList;

    }
    public Integer getBookIndexFunc(int id, List<Book> list){
        for( int i = 0; i < list.size(); i++ )
        {
            Book lValue = list.get( i );
            if(lValue.getId() == id)
            {
                return i;
            }  
        }
        return null;
    }
    public Book getBookByIdFunc(int id, List<Book> list){
        
        for( int i = 0; i < list.size(); i++ )
        {
            Book lValue = list.get( i );
            if(lValue.getId() == id)
            {
                return lValue;
            }  
        }
        return null;
    }
    public Boolean deleteBookByIdFunc(int id, List<Book> list){
        Boolean res = Boolean.FALSE;
       // int i = 1;
        for( int i = 0; i < list.size(); i++ )
        {
            Book lValue = list.get( i );
            if(lValue.getId() == id)
            {
                list.remove(lValue);
                i--;
                res = Boolean.TRUE;
            }  
        }
        return res;
    }
}
