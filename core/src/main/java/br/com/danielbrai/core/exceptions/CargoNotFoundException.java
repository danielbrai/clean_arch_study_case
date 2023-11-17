package br.com.danielbrai.core.exceptions;

public class CargoNotFoundException extends RuntimeException {

    public CargoNotFoundException(String msg) {
        super(msg);
    }
}
