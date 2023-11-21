package br.com.danielbrai.core.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String msg) {
        super(msg);
    }
}
