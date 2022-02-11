package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.protocol.model.Response;
import com.project.jmemcached.protocol.model.Status;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultResponseConverterTest {
    private final DefaultResponseConverter defaultResponseConverter = new DefaultResponseConverter();

    private final byte[] responseWithoutData = new byte[]
            // version, status, flags
            {16,      0,      0};

    private final byte[] responseWithData = new byte[]
            // version, status, flags,  int length,  byte array
            {16,      0,      1,      0, 0, 0, 3,  1, 2, 3};

    @Test
    public void testReadResponseWithoutData() throws IOException {
        Response response = defaultResponseConverter.readResponse(new ByteArrayInputStream(responseWithoutData));
        assertEquals(Status.ADDED, response.getStatus());
        assertFalse(response.hasData());
    }

    @Test
    public void testReadResponseWithData() throws IOException {
        Response response = defaultResponseConverter.readResponse(new ByteArrayInputStream(responseWithData));
        assertEquals(Status.ADDED, response.getStatus());
        assertTrue(response.hasData());
        assertArrayEquals(new byte[]{1, 2, 3}, response.getData());
    }

    @Test
    public void testWriteResponseWithoutData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(Status.ADDED);
        defaultResponseConverter.writeResponse(out, response);
        assertArrayEquals(responseWithoutData, out.toByteArray());
    }

    @Test
    public void testWriteResponseWithData() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(Status.ADDED, new byte[]{1, 2, 3});
        defaultResponseConverter.writeResponse(out, response);
        assertArrayEquals(responseWithData, out.toByteArray());
    }
}