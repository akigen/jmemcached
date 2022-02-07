package com.project.jmemcached.protocol.model;

public class Response extends AbstractPackage {
    private final Status status;

    public Response(byte[] data, Status status) {
        super(data);
        this.status = status;
    }

    public Response(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
