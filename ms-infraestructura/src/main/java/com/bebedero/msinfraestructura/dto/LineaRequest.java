package com.bebedero.msinfraestructura.dto;

public record LineaRequest(String nombre, Long galponId, Double temperaturaActual, String fechaActualizacion) {
}
