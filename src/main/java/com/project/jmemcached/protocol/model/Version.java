package com.project.jmemcached.protocol.model;

import com.project.jmemcached.exception.JMemcachedException;

public enum Version {

    VERSION_0_0(0,0),

    VERSION_1_0(1,0);

    private final byte high;

    private final byte low;

    Version(int high, int low) {
        this.high = (byte) (high & 0x7);
        this.low = (byte) (low & 0xF);
    }

    public static Version valueOf(byte byteCode) {
        for (Version version: Version.values()) {
            if (version.getByteCode() == byteCode) {
                return version;
            }
        }
        throw new JMemcachedException("Unsupported byteCode for Version: " + byteCode);
    }

    public byte getByteCode() {
        return (byte) (low + (high << 4));
    }

    @Override
    public String toString() {
        return String.format("%s.%s", high, low);
    }
}
