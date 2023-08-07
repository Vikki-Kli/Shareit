package ru.shareit.exception;

public class NoSuchItemException extends RuntimeException {
    public NoSuchItemException(String s) {
        super(s);
    }
}