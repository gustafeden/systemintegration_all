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

@XmlRootElement(name = "DataCenter")
/**
 *
 * @author gusta
 */
public class DataCenter implements Serializable {

    private static final long serialVersionUID = 1L;
    int id;
    String name;
    Date created;

    public DataCenter() {
    }

    public DataCenter(int id, String name, Date created) {
        this.id = id;
        this.name = name;
        this.created = created;

    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    @XmlElement
    public void setCreated(Date created) {
        this.created = created;
    }
}
