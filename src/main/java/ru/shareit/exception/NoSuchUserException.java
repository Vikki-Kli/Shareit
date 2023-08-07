package ru.shareit.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String s) {
        super(s);
    }
}
