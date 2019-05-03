/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TempReport")
/**
 *
 * @author gusta
 */
public class TempReport {

    private static final long serialVersionUID = 1L;

    private List<Data> values;
    private Float maxTemp;
    private Float minTemp;
    private Float avgTemp;

    public TempReport() {
    }

    public List<Data> getValues() {
        return values;
    }

    public void setValues(List<Data> values) {
        this.values = values;
    }

    public Float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Float minTemp) {
        this.minTemp = minTemp;
    }

    public Float getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(Float avgTemp) {
        this.avgTemp = avgTemp;
    }
}
