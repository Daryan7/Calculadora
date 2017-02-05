package com.example.juan.theapp.Domain.Exceptions;

public class MediaPlayerException extends Exception {
    public enum ErrorType {
        READ,
        NO_PERMISSION,
        NO_SONGS
    }

    private ErrorType type;

    public MediaPlayerException(ErrorType type) {
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
