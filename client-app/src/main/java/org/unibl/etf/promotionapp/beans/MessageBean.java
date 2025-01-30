package org.unibl.etf.promotionapp.beans;

import java.io.Serial;
import java.io.Serializable;

public class MessageBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private String message;

    public MessageBean() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
