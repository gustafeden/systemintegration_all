/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "Data") 
/**
 *
 * @author gusta
 */
public class Data implements Serializable{
    private static final long serialVersionUID = 1L;

   
    private int id;
    private Date created;
    private String datacenter;
    private Float energycost;
    private Float temperature;
    private Float hourPrice;
    private int wattage;
    
    public Data(){
        
    }
    public Data(int id, Date created, Float temperature, String datacenter){
        this.id = id;
        this.created = created;
        this.temperature = temperature;
        this.datacenter = datacenter;
    }
    public Data(int id, Date created, int wattage, String datacenter){
        this.id = id;
        this.created = created;
        this.wattage = wattage;
        this.datacenter = datacenter;
    }
    public Data(int id, Date created, String datacenter, int wattage, Float price){
        this.id = id;
        this.created = created;
        this.wattage = wattage;
        this.datacenter = datacenter;
        this.energycost = price;
        
    }
     public Data(int id, Date created, String datacenter, Float totalPrice, Float hourprice){
        this.id = id;
        this.created = created;
        this.hourPrice = hourprice;
        this.datacenter = datacenter;
        this.energycost = totalPrice;
        
    }
     public Data(int id, Date created, Float hourPrice){
         this.id = id;
         this.created = created;
         this.hourPrice = hourPrice;
     }
     public Float getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(Float hourPrice) {
        this.hourPrice = hourPrice;
    }
    public int getId(){
        return this.id;
    }
    
@XmlElement 
    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }
@XmlElement 
    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDatacenter() {
        return datacenter;
    }
@XmlElement 
    public void setDatacenter(String datacenter) {
        this.datacenter = datacenter;
    }

    public Float getEnergycost() {
        return energycost;
    }
@XmlElement 
    public void setEnergycost(Float energycost) {
        this.energycost = energycost;
    }

    public Float getTemperature() {
        return temperature;
    }
@XmlElement 
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public int getWattage() {
        return wattage;
    }
@XmlElement 
    public void setWattage(int wattage) {
        this.wattage = wattage;
    }
}
