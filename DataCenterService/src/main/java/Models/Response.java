/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
/**
 *
 * @author gusta
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private boolean status;

    public Response() {

    }

    public Response(String msg, boolean bol) {
        this.message = msg;
        this.status = bol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
