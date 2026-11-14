package com.bebedero.msinfraestructura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "granjas")
public class Granja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "granja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Galpon> galpones = new ArrayList<>();

    public Granja() {
    }

    public Granja(String nombre) {
        this.nombre = nombre;
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

    public List<Galpon> getGalpones() {
        return galpones;
    }

    public void setGalpones(List<Galpon> galpones) {
        this.galpones = galpones;
    }
}
