package com.project.jmemcached.protocol;

import com.project.jmemcached.protocol.model.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Request parser
 */
public interface RequestConverter {

    Request readRequest(InputStream inputStream) throws IOException;

    void writeRequest(OutputStream outputStream, Request request) throws IOException;
}
