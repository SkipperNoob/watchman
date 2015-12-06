package com.github.skippernoob.watchman.sync.exc;

public class SyncServiceException extends RuntimeException {
    public SyncServiceException(String message) {
        super(message);
    }

    public SyncServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
