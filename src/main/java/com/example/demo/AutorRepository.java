package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.annoNacimiento <= :anio AND (a.annoMuerte IS NULL OR a.annoMuerte > :anio)")
    List<Autor> vivosEnAnio(@Param("anio") int anio);

}
