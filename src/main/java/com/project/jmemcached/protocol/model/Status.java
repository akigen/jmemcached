package com.project.jmemcached.protocol.model;

import com.project.jmemcached.exception.JMemcachedException;

public enum Status {
    ADDED(0),

    REPLACED(1),

    GOTTEN(2),

    NOT_FOUND(3),

    REMOVED(4),

    CLEARED(5);

    private final byte code;

    Status(int code) {
        this.code = (byte) code;
    }

    public static Status valueOf(byte byteCode) {
        for (Status instance: Status.values()) {
            if (instance.getByteCode() == byteCode) {
                return instance;
            }
        }
        throw new JMemcachedException("Unsupported byteCode for Status: " + byteCode);
    }

    public byte getByteCode() {
        return code;
    }
}
