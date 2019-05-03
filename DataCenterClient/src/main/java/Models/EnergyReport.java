/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EnergyReport")
/**
 *
 * @author gusta
 */
public class EnergyReport implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Data> datalist;
    private Float avgVal, minVal, maxVal;
    private Data mostExpensive, leastExpensive;

    public EnergyReport() {
    }

    public List<Data> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<Data> datalist) {
        this.datalist = datalist;
    }

    public Float getAvgVal() {
        return avgVal;
    }

    public void setAvgVal(Float avgVal) {
        this.avgVal = avgVal;
    }

    public Float getMinVal() {
        return minVal;
    }

    public void setMinVal(Float minVal) {
        this.minVal = minVal;
    }

    public Float getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Float maxVal) {
        this.maxVal = maxVal;
    }

    public Data getMostExpensive() {
        return mostExpensive;
    }

    public void setMostExpensive(Data mostExpensive) {
        this.mostExpensive = mostExpensive;
    }

    public Data getLeastExpensive() {
        return leastExpensive;
    }

    public void setLeastExpensive(Data leastExpensive) {
        this.leastExpensive = leastExpensive;
    }
}
