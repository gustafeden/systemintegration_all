/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import java.io.IOException;
import Models.*;
import java.util.Scanner;
/**
 *
 * @author gusta
 */
public class AdminClient {
   
    private static DataCenters allDCs;
    private static Communication CC = new Communication();
    private static Menus menu = new Menus();
    
    public static void main(String[] args) throws IOException {
        int userChoice = 0, chosenDC = 0;
        allDCs = CC.getAllDcs();
        Scanner input = new Scanner(System.in);
        while((chosenDC = menu.premenu(allDCs)) != 9){
            if(chosenDC == (allDCs.getDCamount()+1)){
                //chosen to view all datacenters
                while((userChoice = menu.mainAllMenu()) != 9){
                switch(userChoice){
                    
                }
                }
            }else if(!checkinputDC(chosenDC)){
                continue;
            }
            else{
                //chosen single datacenters
                while((userChoice = menu.mainSingleMenu(getDCfromIndex(chosenDC))) != 9){
                switch(userChoice){
                    case 1:
                        menu.showCurrentTemp(CC.getDCCurrentTemp(chosenDC));
                        break;
                    case 2:
                        menu.showCurrentEnergy(CC.getDCCurrentEnergy(chosenDC));
                        break;
                    case 3:
                        menu.showCurrentEnergyCost(CC.getDCCurrentEnergyCost(chosenDC));
                        break;
                    case 4:
                        menu.showCurrentFullReport(CC.getDCCurrentFullReport(chosenDC));
                        break;
                    case 5:
                        menu.showFullTempReport(CC.getDCFullTempReport(chosenDC));
                        break;
                    default:
                        break;
                }
            }
            }
           allDCs = CC.getAllDcs();
        }
        
    }
    
    private static boolean checkinputDC(int in){
        if (in > allDCs.getDCamount()) {
            return false;
        }else if(in <= 0){
            return false;
        }
        return true;
    }
    
    private static DataCenter getDCfromIndex(int index){
        DataCenter dc = null;
      
        for(DataCenter d : allDCs.getAllDataCenters()){
            if(d.getId() == index)
                dc = d;
           
        }
        return dc;
    }
   
   
   
}
