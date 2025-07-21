package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("birth_year")
    private Long annoNacimiento;

    @JsonProperty("death_year")
    private Long annoMuerte;


    @OneToMany(mappedBy = "autores",fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        return "Autor{ " + nombre + '\'' + " ------ " +
                 annoNacimiento + "-" +
                annoMuerte +
                '}';
    }
}
