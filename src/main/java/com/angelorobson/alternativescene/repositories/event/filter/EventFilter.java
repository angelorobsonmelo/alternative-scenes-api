package com.angelorobson.alternativescene.repositories.event.filter;

import java.io.Serializable;

public class EventFilter implements Serializable {

    private static final long serialVersionUID = -6209219512751878213L;

    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
