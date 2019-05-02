/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "DataCenters") 
/**
 *
 * @author gusta
 */
public class DataCenters implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<DataCenter> allDataCenters;

    
    private int DCamount;
    public List<DataCenter> getAllDataCenters() {
        return allDataCenters;
    }
    @XmlElement 
    public void setAllDataCenters(List<DataCenter> allDataCenters) {
        this.allDataCenters = allDataCenters;
    }
    
    public DataCenters(){
        DCamount = 0;
        allDataCenters = new ArrayList<>();
    }
    public int getDCamount() {
        return DCamount;
    }

    public void setDCamount(int DCamount) {
        this.DCamount = DCamount;
    }
    @XmlElement 
    public void addDataCenter(DataCenter dc){
        allDataCenters.add(dc);
        DCamount++;
    }
}
