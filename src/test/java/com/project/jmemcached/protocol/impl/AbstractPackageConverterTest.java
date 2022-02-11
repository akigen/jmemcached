package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPackageConverterTest {

    private final AbstractPackageConverter abstractPackageConverter = new AbstractPackageConverter() {

    };

    @Test
    void testCheckProtocolVersionSuccess() {
        try {
            abstractPackageConverter.checkProtocolVersion((byte) 16);
        } catch (Exception e) {
            fail("Supported protocol version should be 1.0");
        }
    }

    @Test
    void testCheckProtocolVersionFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            abstractPackageConverter.checkProtocolVersion((byte) 0);
        });

        assertEquals("Unsupported protocol version: 0.0", exception.getMessage());
    }

    @Test
    void testGetVersionByte() {
        assertEquals(16, abstractPackageConverter.getVersionByte());
    }
}