package com.project.jmemcached.exception;

public class JMemcachedConfigException extends JMemcachedException {
    public JMemcachedConfigException(String message) {
        super(message);
    }

    public JMemcachedConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
