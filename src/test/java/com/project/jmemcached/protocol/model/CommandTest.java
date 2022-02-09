package com.project.jmemcached.protocol.model;

import com.project.jmemcached.exception.JMemcachedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    public void testValueOfSuccess() {
        assertEquals(Command.CLEAR, Command.valueOf((byte) 0));
        assertEquals(Command.PUT, Command.valueOf((byte) 1));
        assertEquals(Command.GET, Command.valueOf((byte) 2));
        assertEquals(Command.REMOVE, Command.valueOf((byte) 3));
    }

    @Test
    public void testValueOfFailed() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            Command.valueOf(Byte.MAX_VALUE);
        });

        String expectedMessage = "Unsupported byteCode for Command: 127";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetByteCode() {
        assertEquals(0, Command.CLEAR.getByteCode());
        assertEquals(1, Command.PUT.getByteCode());
        assertEquals(2, Command.GET.getByteCode());
        assertEquals(3, Command.REMOVE.getByteCode());
    }

}