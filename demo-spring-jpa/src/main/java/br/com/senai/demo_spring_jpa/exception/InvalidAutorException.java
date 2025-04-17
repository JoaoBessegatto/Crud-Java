package br.com.senai.demo_spring_jpa.exception;

public class InvalidAutorException extends RuntimeException {
    public InvalidAutorException(String message) {
        super(message);
    }
}
