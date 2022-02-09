package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.exception.JMemcachedException;
import com.project.jmemcached.protocol.test.SerializableFailedClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultObjectSerializerTest {
    private final DefaultObjectSerializer defaultObjectSerializer = new DefaultObjectSerializer();

    private final Object testObject = "Test";

    //Byte array for testObject instance
    private final byte[] testObjectArray = {-84, -19, 0, 5, 116, 0, 4, 84, 101, 115, 116};

    //Byte array for a.B (class not found) instance
    private final byte[] testClassNotFoundArray =
            {-84, -19, 0, 5, 115, 114, 0, 3, 97, 46, 66, 56, 54, 57, -101, -3, 120, 66, 4, 2, 0, 0, 120, 112};

    //Byte array for test.SerializableFailedClass instance
    private final byte[] testIOExceptionDuringDeserialization = {-84, -19, 0, 5, 115, 114, 0, 26, 116, 101, 115, 116, 46, 83,
            101, 114, 105, 97, 122, 97, 98, 108, 101, 70, 97, 105, 108, 101, 100, 67, 108, 97, 115, 115, -21, -110, -27, 28,
            43, -101, 0, -74, 2, 0, 0, 120, 112};

    @Test
    void testToByteArraySuccess() {
        byte[] actual = defaultObjectSerializer.toByteArray(testObject);
        assertArrayEquals(testObjectArray, actual);
    }

    @Test
    void testToByteArrayNull() {
        assertNull(defaultObjectSerializer.toByteArray(null));
    }

    @Test
    void testToByteArraySerializableException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.toByteArray(new Object());
        });

        assertEquals("Class java.lang.Object should implement java.io.Serializable interface.", exception.getMessage());
    }

    @Test
    void testToByteArrayIOException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.toByteArray(new SerializableFailedClass());
        });

        assertEquals("Can't convert object to byte array Write IO", exception.getMessage());
    }

    @Test
    public void testFromByteArraySuccess() {
        String actual = (String) defaultObjectSerializer.fromByteArray(testObjectArray);
        assertEquals(testObject, actual);
    }

    @Test
    public void testFromByteArrayNull() {
        assertNull(defaultObjectSerializer.fromByteArray(null));
    }

    @Test
    public void testFromByteArrayIOException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.fromByteArray(testIOExceptionDuringDeserialization);
        });

        assertEquals("Can't convert byte array to object test.SeriazableFailedClass", exception.getMessage());
        assertEquals("java.lang.ClassNotFoundException: test.SeriazableFailedClass", exception.getCause().toString());
    }

    @Test
    public void testFromByteArrayClassNotFoundException() {
        Exception exception = assertThrows(JMemcachedException.class, () -> {
            defaultObjectSerializer.fromByteArray(testClassNotFoundArray);
        });

        assertEquals("Can't convert byte array to object a.B", exception.getMessage());
        assertEquals("java.lang.ClassNotFoundException: a.B", exception.getCause().toString());
    }
}