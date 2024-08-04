package com.example.transacciones.config;

public class Exception400 extends RuntimeException{
    public Exception400(String errorBody) {
        super(errorBody);
    }
}
