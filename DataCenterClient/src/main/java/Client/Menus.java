/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Models.Data;
import Models.DataCenter;
import java.util.Scanner;
import Models.DataCenters;
import Models.EnergyReport;
import Models.TempReport;

/**
 *
 * @author gusta
 */
public class Menus {

    private Scanner input;

    public Menus() {
        input = new Scanner(System.in);
    }

    public Float newEnergyCost() {
        System.out.println("What would you like the new energy price to be?");
        Float newcost = getFloatIn();
        return newcost;
    }

    public void showCurrentEnergyCost(Data data) {
        System.out.println("current energy costs for DC: " + data.getDatacenter());
        System.out.println("Price per hour: " + data.getHourPrice() + " kr - total price for last hour: " + data.getEnergycost() + " kr");
        input.nextLine();
    }

    public void showCurrentEnergy(Data data) {
        System.out.println("current energy consumption for DC: " + data.getDatacenter() + " - " + data.getWattage() + " W");
        input.nextLine();
    }

    public void showCurrentTemp(Data data) {
        System.out.println("current temp for DC: " + data.getDatacenter() + " - temp: " + data.getTemperature());
        input.nextLine();
    }

    public void showCurrentFullReport(Data data) {
        System.out.println("Full Current report of DC: " + data.getDatacenter());
        System.out.println("Temperature: " + data.getTemperature() + " *C - Wattage: " + data.getWattage() + " W");
        System.out.println("Last hour cost : " + data.getEnergycost() + " kr - with an hourprice of: " + data.getHourPrice() + " kr");
        input.nextLine();
    }

    public void showFullTempReport(TempReport tr) {
        System.out.println("Full Temperature report for last 24 hours of DC: " + tr.getValues().get(0).getDatacenter());
        System.out.println("Last 24 temperatures: ");
        for (Data d : tr.getValues()) {
            System.out.println("temp: " + d.getTemperature() + " *C - date: " + d.getCreated());
        }
        System.out.println("--------------------------");
        System.out.println("Max: " + tr.getMaxTemp() + " *C - Min: " + tr.getMinTemp() + " *C - Avg: " + tr.getAvgTemp());
        input.nextLine();
    }

    public void showFullEnergyReport(EnergyReport er) {
        System.out.println("Full Energy report for last 24 hours of DC: " + er.getDatalist().get(0).getDatacenter());
        System.out.println("Last 24 values: ");
        for (Data d : er.getDatalist()) {
            System.out.println("Consumtion: " + d.getWattage() + " W - Price: " + d.getHourPrice() + " kr/kWh - Cost: " + d.getEnergycost() + " kr - timestamp: " + d.getCreated());
        }
        System.out.println("-------------------------");
        System.out.println("Max consumption: " + er.getMaxVal() + " W - Min consumption: " + er.getMinVal() + " W - Average consumption: " + er.getAvgVal() + " W");
        System.out.println("Most expensive hour: " + er.getMostExpensive().getCreated() + " - consumption: " + er.getMostExpensive().getWattage() + " W - Price: "
                + er.getMostExpensive().getHourPrice() + " kr/kWh - Cost: " + er.getMostExpensive().getEnergycost());
        System.out.println("Least expensive hour: " + er.getLeastExpensive().getCreated() + " - consumption: " + er.getLeastExpensive().getWattage() + " W - Price: "
                + er.getLeastExpensive().getHourPrice() + " kr/kWh - Cost: " + er.getLeastExpensive().getEnergycost());
        input.nextLine();
    }

    public void showAllDCsEnergyCosts(EnergyReport er) {
        System.out.println("All DataCenters energy consumption summed up: ");
        for (Data d : er.getDatalist()) {
            System.out.println("DataCenter: " + d.getDatacenter() + " - " + d.getWattage() + " kWh - " + d.getEnergycost() + " kr");
        }
        input.nextLine();

    }

    public void showDCMaxEnergyCosts(Data data) {
        System.out.println("DataCenter that cost the most last 24h: " + data.getDatacenter());
        System.out.println("Total wattage: " + data.getWattage() + " kWh - total cost: " + data.getEnergycost() + " kr");
        System.out.println("");
        input.nextLine();
    }

    private int getIntIn() {
        Scanner in = new Scanner(System.in);
        while (!in.hasNextLong()) {
            in.next();
        }
        int i = in.nextInt();
        return i;
    }

    private Float getFloatIn() {
        Scanner in = new Scanner(System.in);
        while (!in.hasNextFloat()) {
            in.next();
        }
        Float i = in.nextFloat();
        return i;
    }

    public int premenu(DataCenters dcs) {
        int selection;

        /**
         * ************************************************
         */
        System.out.println("Please choose which DataCenter to monitor");
        System.out.println("-------------------------\n");
        for (DataCenter d : dcs.getAllDataCenters()) {
            System.out.println(d.getId() + " - " + d.getName() + " - created: " + d.getCreated().toString());

        }
        System.out.println((dcs.getDCamount() + 1) + " - all datacenters");
        System.out.println("8 - Change energy cost");
        System.out.println("9 - Quit");

        selection = getIntIn();
        return selection;
    }

    public int mainSingleMenu(DataCenter currentDC) {
        int selection;
        System.out.println("Current DataCenter: " + currentDC.getName());
        System.out.println("Please choose one of the options below");
        System.out.println("-------------------------\n");
        System.out.println("1 - Current Temperature");
        System.out.println("2 - Current energy consumption");
        System.out.println("3 - Current energy costs");
        System.out.println("4 - Full current report");
        System.out.println("5 - Full temperature report");
        System.out.println("6 - Full energy report");
        System.out.println("9 - exit");
        //med mera
        selection = getIntIn();
        return selection;

    }

    public int mainAllMenu() {
        int selection;
        System.out.println("You have selected all DataCenters");
        System.out.println("Please choose one of the options below");
        System.out.println("-------------------------\n");
        System.out.println("1 - Cost of all DataCenters last 24h");
        System.out.println("2 - Most expensive DataCenter last 24h");
        System.out.println("9 - exit");
        //med mera
        selection = getIntIn();
        return selection;
    }
}
