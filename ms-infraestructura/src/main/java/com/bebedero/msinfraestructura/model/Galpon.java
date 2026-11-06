package com.bebedero.msinfraestructura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "galpones")
public class Galpon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "granja_id")
    private Granja granja;

    @JsonIgnore
    @OneToMany(mappedBy = "galpon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaBebedero> lineas = new ArrayList<>();

    public Galpon() {
    }

    public Galpon(String nombre, Granja granja) {
        this.nombre = nombre;
        this.granja = granja;
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

    public Granja getGranja() {
        return granja;
    }

    public void setGranja(Granja granja) {
        this.granja = granja;
    }

    // Se expone como granjaId (y no como objeto anidado) para calzar
    // con el modelo plano que ya usa la app Android.
    public Long getGranjaId() {
        return granja != null ? granja.getId() : null;
    }

    public List<LineaBebedero> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaBebedero> lineas) {
        this.lineas = lineas;
    }
}
