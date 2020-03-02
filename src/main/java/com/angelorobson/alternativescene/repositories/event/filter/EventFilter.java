package com.angelorobson.alternativescene.repositories.event.filter;

import com.angelorobson.alternativescene.enums.StatusEnum;

import java.io.Serializable;

public class EventFilter implements Serializable {

    private static final long serialVersionUID = -6209219512751878213L;

    private StatusEnum status;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
