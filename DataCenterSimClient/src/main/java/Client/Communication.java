/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Models.Data;
import Models.DataCenters;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import Models.Response;

/**
 *
 * @author gusta
 */
public class Communication {

    private ClientConfig config;
    private Client client;
    private WebResource service;

    public Communication() {
        config = new DefaultClientConfig();
        client = Client.create(config);
        service = client.resource(
                UriBuilder.fromUri("http://localhost:8080/DataCenterService").build());
    }

    public DataCenters getAllDataCenters() {
        Gson gson = new Gson();
        String dcsJson = service.path("rest/DataCenter/get/datacentersinfoobj")
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        DataCenters res = gson.fromJson(dcsJson, DataCenters.class);
        return res;
    }

    public Data getCurrentHourPrice() {
        Data data = null;
        Gson gson = new Gson();
        String dcsJson = service.path("rest/DataCenter/get/currenthourprice")
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        data = gson.fromJson(dcsJson, Data.class);

        return data;
    }

    public Response postToDB(Data data) {
        Gson gson = new Gson();
        ClientResponse response = service.path("rest/DataCenter/add/data")
                .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);
        Response res = gson.fromJson(response.getEntity(String.class), Response.class);
        return res;
    }
    /*
     ClientResponse response = service.path("rest/BookService2/books/update")
                    .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, bookoriginal);
            System.out.println("Response: "+response.getEntity(String.class));*/
}
