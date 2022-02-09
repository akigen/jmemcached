package com.project.jmemcached.protocol.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractPackageTest {

    private static AbstractPackage newInstance(byte[] array) {
        return new AbstractPackage(array) {
        };
    }

    @Test
    void testHasDataNull() {
        AbstractPackage aPackage = newInstance(null);
        assertFalse(aPackage.hasData());
    }

    @Test
    void testHasDataEmpty() {
        AbstractPackage aPackage = newInstance(new byte[0]);
        assertFalse(aPackage.hasData());
    }

    @Test
    void testHasData() {
        AbstractPackage aPackage = newInstance(new byte[]{1, 2, 3});
        assertTrue(aPackage.hasData());
    }
}