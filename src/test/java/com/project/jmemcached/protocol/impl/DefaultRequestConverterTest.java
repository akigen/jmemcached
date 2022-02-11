package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.exception.JMemcachedException;
import com.project.jmemcached.protocol.model.Command;
import com.project.jmemcached.protocol.model.Request;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class DefaultRequestConverterTest {

    private final DefaultRequestConverter defaultRequestConverter = new DefaultRequestConverter();
    private final byte[] requestClear = new byte[]
            //version,  command, flags
            {16,       0,       0};
    private final byte[] requestPut = new byte[]
            //version,  command, flags, key length, key bytes (123), ttl (long),               data length (int), data bytes
            {16,       1,       7,     3,          49, 50, 51,      0, 0, 0, 0, 0, 0, 0, 5,   0, 0, 0, 3,        1, 2, 3};

    @Test
    void testGetFlagsByteEmpty() {
        Request request = new Request(Command.CLEAR);
        byte flags = defaultRequestConverter.getFlagsByte(request);
        assertEquals(0, flags);
    }

    @Test
    void testGetFlagsByteAll() {
        Request request = new Request(Command.CLEAR, "key", System.currentTimeMillis(), new byte[]{1});
        byte flags = defaultRequestConverter.getFlagsByte(request);
        assertEquals(7, flags);
    }

    @Test
    void testWriteKeySuccess() throws IOException {
        DataOutputStream dataOutputStream = spy(new DataOutputStream(mock(OutputStream.class)));
        String key = "key";
        defaultRequestConverter.writeKey(dataOutputStream, new Request(Command.GET, key));

        verify(dataOutputStream).write(key.getBytes(StandardCharsets.US_ASCII));
        verify(dataOutputStream).writeByte(3);
    }

    @Test
    void testWriteKeyFailed() throws IOException {
        String key = "key".repeat(128);
        DataOutputStream dataOutputStream = new DataOutputStream(null);
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultRequestConverter.writeKey(dataOutputStream, new Request(Command.GET, key));
        });

        assertEquals("Key length should be <= 127 bytes for key = " + key, exception.getMessage());
    }

    @Test
    void testReadSimpleRequest() throws IOException {
        Request request = defaultRequestConverter.readRequest(new ByteArrayInputStream(requestClear));
        assertEquals(Command.CLEAR, request.getCommand());
        assertFalse(request.hasKey());
        assertFalse(request.hasTtl());
        assertFalse(request.hasData());
    }

    @Test
    void testReadComplexRequest() throws IOException {
        Request request = defaultRequestConverter.readRequest(new ByteArrayInputStream(requestPut));
        assertEquals(Command.PUT, request.getCommand());
        assertTrue(request.hasKey());
        assertEquals("123", request.getKey());
        assertTrue(request.hasTtl());
        assertEquals(Long.valueOf(5L), request.getTtl());
        assertTrue(request.hasData());
        assertArrayEquals(new byte[]{1, 2, 3}, request.getData());
    }

    @Test
    void testWriteRequestWithoutData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConverter.writeRequest(out, new Request(Command.CLEAR));

        assertArrayEquals(requestClear, out.toByteArray());
    }

    @Test
    void testWriteRequestWithData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        defaultRequestConverter.writeRequest(out, new Request(Command.PUT, "123", 5L, new byte[]{1, 2, 3}));

        assertArrayEquals(requestPut, out.toByteArray());
    }


}