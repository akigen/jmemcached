package com.project.jmemcached.protocol.model;

import com.project.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    @Test
    void testValueOfSuccess() {
        assertEquals(Version.VERSION_1_0, Version.valueOf((byte) 16));
    }

    @Test
    void testValueOfFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            Version.valueOf(Byte.MAX_VALUE);
        });

        String expectedMessage = "Unsupported byteCode for Version: 127";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetByteCode() {
        assertEquals(16, Version.VERSION_1_0.getByteCode());
    }

    @Test
    void testVerifyToString() {
        assertEquals("1.0", Version.VERSION_1_0.toString());
    }
}