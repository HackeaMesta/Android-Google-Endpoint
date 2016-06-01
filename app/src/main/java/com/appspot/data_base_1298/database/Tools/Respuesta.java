package com.appspot.data_base_1298.database.Tools;

/**
 * Created by rk521 on 1/05/16.
 */
public class Respuesta<T> {
    private final T resultado;
    private final Exception exception;

    public Respuesta(T resultado, Exception exception) {
        this.resultado = resultado;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public T getResultado() {
        return resultado;
    }
}