package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Long annoNacimiento;
    private Long annoMuerte;

    @ManyToMany(mappedBy = "autores")
    private List<Libro> libros;




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getAnnoNacimiento() {
        return annoNacimiento;
    }

    public void setAnnoNacimiento(Long annoNacimiento) {
        this.annoNacimiento = annoNacimiento;
    }

    public Long getAnnoMuerte() {
        return annoMuerte;
    }

    public void setAnnoMuerte(Long annoMuerte) {
        this.annoMuerte = annoMuerte;
    }
}
