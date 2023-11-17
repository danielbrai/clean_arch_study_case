package br.com.danielbrai.core.exceptions;

public class MaximumCapacityExceededException extends RuntimeException {

    public MaximumCapacityExceededException(String msg) {
        super(msg);
    }
}
