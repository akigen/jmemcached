package com.project.jmemcached.protocol.model;

import com.project.jmemcached.exception.JMemcachedException;

public enum Command {
    CLEAR(0),

    PUT(1),

    GET(2),

    REMOVE(3);

    private final byte code;

    Command(int code) {
        this.code = (byte) code;
    }

    public static Command valueOf(byte byteCode) {
        for (Command command: Command.values()) {
            if (command.getByteCode() == byteCode) {
                return command;
            }
        }
        throw new JMemcachedException("Unsupported byteCode for Command: " + byteCode);
    }

    public byte getByteCode(){
        return code;
    }
}
