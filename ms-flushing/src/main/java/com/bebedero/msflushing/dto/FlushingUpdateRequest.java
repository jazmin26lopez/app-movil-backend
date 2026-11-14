
package com.bebedero.msflushing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FlushingUpdateRequest {

    @NotBlank(message = "La observación es obligatoria")
    @Size(max = 255, message = "La observación no puede superar los 255 caracteres")
    private String observacion;

    public FlushingUpdateRequest() {
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
