package br.com.senai.demo_spring_jpa.exception;

public class AutorNotFoundException extends RuntimeException {
    public AutorNotFoundException(String message) {
        super(message);
    }
}
