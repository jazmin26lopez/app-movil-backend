package com.bebedero.msinfraestructura.model;

public enum EstadoTemperatura {
    NORMAL,
    ADVERTENCIA,
    CRITICO;

    public static EstadoTemperatura desdeTemperatura(double temperatura) {
        if (temperatura < 15.0 || temperatura > 30.0) {
            return CRITICO;
        }
        if (temperatura > 21.0) {
            return ADVERTENCIA;
        }
        return NORMAL;
    }
}
