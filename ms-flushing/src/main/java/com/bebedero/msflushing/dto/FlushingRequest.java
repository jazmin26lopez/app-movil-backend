
package com.bebedero.msflushing.dto;

import jakarta.validation.constraints.NotNull;

public class FlushingRequest {

    @NotNull(message = "El ID de la línea es obligatorio")
    private Long lineaId;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    private String fechaHora;
    private String observacion;
    private String fotoUri;

    public FlushingRequest() {
    }

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
