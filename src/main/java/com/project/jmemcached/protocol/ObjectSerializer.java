package com.project.jmemcached.protocol;

public interface ObjectSerializer {

    byte[] toByteArray(Object object);

    Object fromByteArray(byte[] array);
}
