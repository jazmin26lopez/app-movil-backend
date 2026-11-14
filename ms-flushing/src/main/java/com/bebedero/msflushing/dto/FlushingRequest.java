
package com.bebedero.msflushing.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class FlushingRequest {

    @NotNull(message = "El ID de la línea es obligatorio")
    @Positive(message = "El ID de la línea debe ser mayor a cero")
    private Long lineaId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser mayor a cero")
    private Long usuarioId;

    private String fechaHora;

    @Size(max = 255, message = "La observación no puede superar los 255 caracteres")
    private String observacion;

    @Size(max = 255, message = "La URI de la foto no puede superar los 255 caracteres")
    private String fotoUri;

    // Constructor vacío
    public FlushingRequest() {
    }

    // Getters y setters
    public Long getLineaId() {
        return lineaId;
    }

    public void setLineaId(Long lineaId) {
        this.lineaId = lineaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFotoUri() {
        return fotoUri;
    }

    public void setFotoUri(String fotoUri) {
        this.fotoUri = fotoUri;
    }
}
