
package com.bebedero.msflushing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flushings")
public class Flushing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lineaId;
    private Long usuarioId;
    private String fechaHora;
    private String observacion;
    private String fotoUri;

    // Constructor vacío requerido por JPA
    public Flushing() {
    }

    // Constructor para crear un flushing
    public Flushing(Long lineaId, Long usuarioId,
                    String fechaHora, String observacion,
                    String fotoUri) {
        this.lineaId = lineaId;
        this.usuarioId = usuarioId;
        this.fechaHora = fechaHora;
        this.observacion = observacion;
        this.fotoUri = fotoUri;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
