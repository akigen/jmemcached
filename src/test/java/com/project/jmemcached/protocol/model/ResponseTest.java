package com.project.jmemcached.protocol.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    public void testToStringWithoutData() {
        Response response = new Response(Status.ADDED);
        assertEquals("ADDED", response.toString());
    }

    @Test
    public void testToStringWithData() {
        Response response = new Response(Status.GOTTEN, new byte[]{1, 2, 3});
        assertEquals("GOTTEN [3 bytes]", response.toString());
    }
}