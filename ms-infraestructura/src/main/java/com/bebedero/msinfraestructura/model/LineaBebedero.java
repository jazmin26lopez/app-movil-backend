package com.bebedero.msinfraestructura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lineas_bebedero")
public class LineaBebedero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double temperaturaActual;

    private String fechaActualizacion;

    @Enumerated(EnumType.STRING)
    private EstadoTemperatura estado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "galpon_id")
    private Galpon galpon;

    @JsonIgnore
    @OneToMany(mappedBy = "linea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicionTemperatura> mediciones = new ArrayList<>();

    public LineaBebedero() {
    }

    public LineaBebedero(String nombre, Galpon galpon, Double temperaturaActual, String fechaActualizacion) {
        this.nombre = nombre;
        this.galpon = galpon;
        this.temperaturaActual = temperaturaActual;
        this.fechaActualizacion = fechaActualizacion;
        this.estado = EstadoTemperatura.desdeTemperatura(temperaturaActual);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getTemperaturaActual() {
        return temperaturaActual;
    }

    public void setTemperaturaActual(Double temperaturaActual) {
        this.temperaturaActual = temperaturaActual;
        this.estado = EstadoTemperatura.desdeTemperatura(temperaturaActual);
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public EstadoTemperatura getEstado() {
        return estado;
    }

    public void setEstado(EstadoTemperatura estado) {
        this.estado = estado;
    }

    public Galpon getGalpon() {
        return galpon;
    }

    public void setGalpon(Galpon galpon) {
        this.galpon = galpon;
    }

    public Long getGalponId() {
        return galpon != null ? galpon.getId() : null;
    }

    public List<MedicionTemperatura> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<MedicionTemperatura> mediciones) {
        this.mediciones = mediciones;
    }
}
