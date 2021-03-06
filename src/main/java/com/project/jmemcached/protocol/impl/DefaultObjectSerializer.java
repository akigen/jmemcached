package com.project.jmemcached.protocol.impl;

import com.project.jmemcached.exception.JMemcachedException;
import com.project.jmemcached.protocol.ObjectSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DefaultObjectSerializer implements ObjectSerializer {

    @Override
    public byte[] toByteArray(Object object) {
        if (object == null) {
            return null;
        }
        if (!(object instanceof Serializable)) {
            throw new JMemcachedException("Class " + object.getClass().getName() + " should implement java.io.Serializable interface.");
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
            out.writeObject(object);
            out.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new JMemcachedException("Can't convert object to byte array " + e.getMessage(), e);
        }
    }

    @Override
    public Object fromByteArray(byte[] array) {
        if (array == null) {
            return null;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(array));
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new JMemcachedException("Can't convert byte array to object " + e.getMessage(), e);
        }
    }
}
