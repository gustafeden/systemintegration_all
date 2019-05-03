/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Models.Data;
import Models.DataCenter;
import Models.DataCenters;
import Models.EnergyReport;
import Models.Response;
import Models.TempReport;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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

    public Communication() {
        config = new DefaultClientConfig();
        client = Client.create(config);
        service = client.resource(
                UriBuilder.fromUri("http://localhost:8080/DataCenterService").build());
    }

    public Response changeEnergyCost(Float newCost) {
        Gson gson = new Gson();
        Data data = new Data();
        data.setHourPrice(newCost);
        ClientResponse response = service.path("rest/DataCenter/add/newenergycost")
                .accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, data);
        Response res = gson.fromJson(response.getEntity(String.class), Response.class);
        return res;
    }

    public Data getDCCurrentTemp(int DCid) {
        Data res = getFromServerJson("get/currenttemp/" + DCid, Data.class);
        return res;
    }

    public Data getDCCurrentEnergy(int DCid) {
        Data res = getFromServerJson("get/currentenergy/" + DCid, Data.class);
        return res;
    }

    public Data getDCCurrentEnergyCost(int DCid) {
        Data res = getFromServerJson("get/currentenergycost/" + DCid, Data.class);
        return res;
    }

    public Data getDCCurrentFullReport(int DCid) {
        Data res = getFromServerJson("get/currentfullreport/" + DCid, Data.class);
        return res;
    }

    public TempReport getDCFullTempReport(int DCid) {
        TempReport res = getFromServerJson("get/fulltempreport/" + DCid, TempReport.class);
        return res;
    }

    public EnergyReport getFullEnergyReport(int DCid) {
        EnergyReport res = getFromServerJson("get/fullenergyreport/" + DCid, EnergyReport.class);
        return res;
    }

    public Data getDCMaxEnergyCost() {
        Data res = getFromServerJson("get/maxenergycost", Data.class);
        return res;
    }

    public EnergyReport getAllDcsEnergyCosts() {
        EnergyReport res = getFromServerJson("get/allenergycost", EnergyReport.class);
        return res;
    }

    public DataCenters getAllDcs() {
        DataCenters res = getFromServerJson("get/datacentersinfoobj", DataCenters.class);
        return res;
    }

    private <T> T getFromServerJson(String toServer, Class<T> toClass) {
        Gson gson = new Gson();
        String dcsJson = service.path("rest/DataCenter/" + toServer)
                .accept(MediaType.APPLICATION_JSON).get(String.class);
        T res = gson.fromJson(dcsJson, toClass);
        return res;
    }

}
