package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.exception.JMemcachedException;
import com.project.jmemcached.protocol.model.Version;

public abstract class AbstractPackageConverter {

    protected void checkProtocolVersion(byte versionByte) {
        Version version = Version.valueOf(versionByte);

        if (version != Version.VERSION_1_0) {
            throw new JMemcachedException("Unsupported protocol version: " + version);
        }
    }

    protected byte getVersionByte() {
        return Version.VERSION_1_0.getByteCode();
    }
}
