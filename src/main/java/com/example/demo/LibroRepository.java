package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByGutendexId(Long gutendexId);

    List<Libro> findByIdiomas(String idiomas);
}
