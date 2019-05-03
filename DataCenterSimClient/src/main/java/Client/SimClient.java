/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Models.Data;
import Models.DataCenter;
import Models.DataCenters;
import Models.Response;
import java.io.IOException;
import static java.lang.Math.floor;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author gusta
 */
public class SimClient {

    private static Communication CC = new Communication();

    public static void main(String[] args) throws IOException {

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("scheduleed!");
                    updateDB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.HOURS);
    }

    static void updateDB() throws InterruptedException {
        DataCenters DCs = CC.getAllDataCenters();

        for (DataCenter dc : DCs.getAllDataCenters()) {
            Random r = new Random();
            int low = 20;
            int high = 50;
            int tempresult2 = map(r.nextGaussian(), -3f, 3f, low, high);
            int energyconsump = map(tempresult2, low, high, 5000, 35000);
            System.out.println("DataCenter: " + dc.getName() + " - temp: " + tempresult2 + " - energy: " + energyconsump);
            Data data = new Data();
            data.setDatacenter(dc.getName());
            data.setTemperature((float) tempresult2);
            data.setWattage(energyconsump);
            Response resp = CC.postToDB(data);
            System.out.println("Response: " + resp.getMessage() + " status: " + resp.isStatus());
            Thread.sleep(50);
        }
    }

    private static int map(int x, int in_min, int in_max, int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private static int map(Double x, Float in_min, Float in_max, int out_min, int out_max) {
        return (int) Math.round((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }
}
