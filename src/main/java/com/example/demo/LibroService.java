package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LibroService {

    @Autowired
    AutorRepository autorRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public Libro convertirLibro(String json) throws Exception {
        JsonNode nodo = mapper.readTree(json);

        Libro libro = new Libro();
        libro.setTitle(nodo.get("title").asText());
        libro.setDescargas(nodo.get("download_count").asLong());
        libro.setGutendexId(nodo.get("id").asLong());

        JsonNode autores = nodo.get("authors");
        String nombreAutor = (autores != null && autores.size() > 0)
                ? autores.get(0).get("name").asText()
                : "Autor desconocido";

        JsonNode idiomas = nodo.get("languages");
        String lenguaje = (idiomas != null && idiomas.size() > 0)
                ? idiomas.get(0).asText()
                : "Idioma desconocido";

//            Autor autor = new Autor();
//            autor.setNombre(nombreAutor);
//            autor.setAnnoNacimiento(autores.get(0).get("birth_year").asLong());
//            autor.setAnnoMuerte(autores.get(0).get("death_year").asLong());

        Optional<Autor> autorOptional = autorRepository.findByNombre(nombreAutor);

        Autor autor = autorOptional.orElseGet(() -> {
            Autor nuevo = new Autor();
            nuevo.setNombre(nombreAutor);
            nuevo.setAnnoNacimiento(autores.get(0).get("birth_year").asLong());
            nuevo.setAnnoMuerte(autores.get(0).get("death_year").asLong());
            return autorRepository.save(nuevo);
        });
            libro.setAutores(autor);
            libro.setIdiomas(lenguaje);
            return libro;


    }


}
