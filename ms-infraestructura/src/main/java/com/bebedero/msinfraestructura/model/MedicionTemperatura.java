package com.bebedero.msinfraestructura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "mediciones_temperatura")
public class MedicionTemperatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperatura;

    private String fechaHora;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "linea_id")
    private LineaBebedero linea;

    public MedicionTemperatura() {
    }

    public MedicionTemperatura(Double temperatura, String fechaHora, LineaBebedero linea) {
        this.temperatura = temperatura;
        this.fechaHora = fechaHora;
        this.linea = linea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LineaBebedero getLinea() {
        return linea;
    }

    public void setLinea(LineaBebedero linea) {
        this.linea = linea;
    }

    public Long getLineaId() {
        return linea != null ? linea.getId() : null;
    }
}
