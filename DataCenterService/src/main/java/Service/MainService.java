/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import Models.*;
import Utils.SQLDao;
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
@Path("/DataCenter")
public class MainService {
    SQLDao db = new SQLDao();
  
   @GET 
   @Path("/get/datacentersinfo") 
   @Produces(MediaType.APPLICATION_JSON) 
   public List<DataCenter> getAllDcsList(){ 
      return db.ListDataCenters();
   } 
   @GET 
   @Path("/get/datacentersinfoobj") 
   @Produces(MediaType.APPLICATION_JSON) 
   public DataCenters getAllDcs(){ 
      return db.getAllDcs();
   } 
   
    @GET 
   @Path("get/currenttemp/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Data getDCtemp(@PathParam("id") int id){ 
     return db.getDCCurrentTemp(id);
    
   } 
   
   @GET 
   @Path("get/currentenergy/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Data getDCenergy(@PathParam("id") int id){ 
     return db.getDCCurrentEnergy(id);
    
   } 
   @GET 
   @Path("get/currentenergycost/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Data getDCenergycost(@PathParam("id") int id){ 
     return db.getDCCurrentEnergyCost(id);
    
   } 
   
   @GET 
   @Path("get/currenthourprice") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Data getCurrentEnergyPrice(){ 
     return db.getDCCurrentEnergyPrice();
    
   }
   
   @GET 
   @Path("get/currentfullreport/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Data getCurrentEnergyPrice(@PathParam("id") int id){ 
     return db.getDCCurrentFullReport(id);
    
   }
   @GET 
   @Path("get/fulltempreport/{id}") 
   @Produces(MediaType.APPLICATION_JSON) 
   public TempReport getFullTempReport(@PathParam("id") int id){ 
     return db.getDCFullTempReport(id);
    
   }
   @POST 
   @Path("add/data") 
   @Produces(MediaType.APPLICATION_JSON) 
   public Response addNewDataPost(Data data){ 
       Response res = new Response("Data added", Boolean.FALSE);
       if (db.addNewData(data)) {
           res.setStatus(Boolean.TRUE);
       }
     return res;
    
   }
}
