package com.project.jmemcached.protocol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Request request;

    @BeforeEach
    void beforeEach() {
        request = new Request(Command.CLEAR);
    }

    @Test
    void testHasKeyTrue() {
        request.setKey("key");
        assertTrue(request.hasKey());
    }

    @Test
    void testHasKeyFalse() {
        assertFalse(request.hasKey());
    }

    @Test
    void testHasTTLTrue() {
        request.setTtl(System.currentTimeMillis());
        assertTrue(request.hasTtl());
    }

    @Test
    void testHasTTLFalse() {
        assertFalse(request.hasTtl());
    }

    @Test
    void testToStringClear() {
        assertEquals("CLEAR", request.toString());
    }

    @Test
    void testToStringRemove() {
        request = new Request(Command.REMOVE);
        request.setKey("key");
        assertEquals("REMOVE[key]", request.toString());
    }

    @Test
    public void testToStringPut() {
        request = new Request(Command.PUT);
        request.setKey("key");
        request.setData(new byte[]{1, 2, 3});
        assertEquals("PUT[key]=3 bytes", request.toString());
    }

    @Test
    public void testToStringPutWithTTL() {
        request = new Request(Command.PUT);
        request.setKey("key");
        request.setTtl(1484166240528L);
        request.setData(new byte[]{1, 2, 3});
        assertEquals("PUT[key]=3 bytes (Wed Jan 11 23:24:00 MSK 2017)", request.toString());
    }




}