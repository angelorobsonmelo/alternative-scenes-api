package com.angelorobson.alternativescene.repositories.event.filter;

import java.io.Serializable;

public class EventFilter implements Serializable {

    private static final long serialVersionUID = -6209219512751878213L;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
