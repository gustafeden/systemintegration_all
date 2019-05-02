/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Models.Data;
import Models.DataCenter;
import Models.DataCenters;
import Models.TempReport;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author gusta
 */
public class Communication {
    private ClientConfig config;
    private Client client;
    private WebResource service;
    
    
    public Communication(){
    config = new DefaultClientConfig();
    client = Client.create(config);
    service = client.resource(
            UriBuilder.fromUri("http://localhost:8080/DataCenterService").build());
    }
    public Data getDCCurrentTemp(int DCid){
        Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/currenttemp/"+ DCid)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          Data res = gson.fromJson(dcsJson, Data.class);
          return res;
    }
     public Data getDCCurrentEnergy(int DCid){
         Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/currentenergy/"+ DCid)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          Data res = gson.fromJson(dcsJson, Data.class);
          return res;
    }
     public Data getDCCurrentEnergyCost(int DCid){
          Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/currentenergycost/"+ DCid)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          Data res = gson.fromJson(dcsJson, Data.class);
          return res;
     }
     public Data getDCCurrentFullReport(int DCid){
          Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/currentfullreport/"+ DCid)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          Data res = gson.fromJson(dcsJson, Data.class);
          return res;
     }
     public TempReport getDCFullTempReport(int DCid){
          Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/fulltempreport/"+ DCid)
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          TempReport res = gson.fromJson(dcsJson, TempReport.class);
          return res;
     }
     public DataCenters getAllDcs(){
        Gson gson = new Gson();
         String dcsJson = service.path("rest/DataCenter/get/datacentersinfoobj")
                    .accept(MediaType.APPLICATION_JSON).get(String.class);
          DataCenters res = gson.fromJson(dcsJson, DataCenters.class);
          return res;
    }
     
}
