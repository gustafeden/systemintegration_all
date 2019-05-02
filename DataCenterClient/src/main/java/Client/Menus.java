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
import Models.TempReport;
/**
 *
 * @author gusta
 */
public class Menus {
    
    
    
    private Scanner input;
    
    public Menus(){
        input = new Scanner(System.in);
    }
    public void showCurrentEnergyCost(Data data){
        System.out.println("current energy costs for DC: " + data.getDatacenter());
        System.out.println("Price per hour: " + data.getHourPrice()+ " kr - total price for last hour: " + data.getEnergycost() + " kr");
        input.nextLine();
    }
    
    public void showCurrentEnergy(Data data){
        System.out.println("current energy consumption for DC: " + data.getDatacenter() + " - " + data.getWattage() + " W");
        input.nextLine();
    }
   
    public void showCurrentTemp(Data data){
        System.out.println("current temp for DC: " + data.getDatacenter() + " - temp: " + data.getTemperature());
        input.nextLine();
    }
    public void showCurrentFullReport(Data data){
        System.out.println("Full Current report of DC: " + data.getDatacenter());
        System.out.println("Temperature: " + data.getTemperature() + " *C - Wattage: " + data.getWattage() + " W");
        System.out.println("Last hour cost : " + data.getEnergycost() + " kr - with an hourprice of: " + data.getHourPrice() + " kr");
        input.nextLine();
    }
    public void showFullTempReport(TempReport tr){
        System.out.println("Full Temperature report for last 24 hours of DC: " + tr.getValues().get(0).getDatacenter());
        System.out.println("Last 24 temperatures: ");
        for(Data d : tr.getValues()){
            System.out.println("temp: " + d.getTemperature() + " *C - date: " + d.getCreated());
        }
        System.out.println("--------------------------");
        System.out.println("Max: " + tr.getMaxTemp() + " *C - Min: " + tr.getMinTemp() + " *C - Avg: " + tr.getAvgTemp());
        input.nextLine();
    }
     private int getIntIn(){
           Scanner in = new Scanner(System.in);
           while (!in.hasNextLong()) {
                in.next();
            }
            int i = in.nextInt();    
            return i;
       }
     
      public int premenu(DataCenters dcs){
         int selection;
        
        /***************************************************/
        System.out.println("Please choose which DataCenter to monitor");
        System.out.println("-------------------------\n");
        for(DataCenter d : dcs.getAllDataCenters()){
            System.out.println(d.getId()+" - " + d.getName() + " - created: " + d.getCreated().toString());
            
        }
        System.out.println((dcs.getDCamount()+1)+" - all datacenters");
        System.out.println("9 - Quit");

        selection = getIntIn();
        return selection;    
    }
       public int mainSingleMenu(DataCenter currentDC){
        int selection;
        System.out.println("Current DataCenter: " + currentDC.getName());
        System.out.println("Please choose what to alter or monitor");
        System.out.println("-------------------------\n");
        System.out.println("1 - Current Temperature");
        System.out.println("2 - Current energy consumption");
        System.out.println("3 - Current energy costs");
        System.out.println("4 - Full current report");
        System.out.println("5 - Full temperature report");
        System.out.println("6 - Full energy report");
        System.out.println("7 - Change energy cost");
        System.out.println("9 - exit");
        //med mera
        selection = getIntIn();
        return selection;
        
    }
    public int mainAllMenu(){
        int selection;
        System.out.println("You have selected all DataCenters");
        System.out.println("Please choose what to alter or monitor");
        System.out.println("-------------------------\n");
        System.out.println("1 - Current Temperature");
        System.out.println("2 - Current energy consumption");
        System.out.println("9 - exit");
        //med mera
        selection = getIntIn();
        return selection;
    }
}
