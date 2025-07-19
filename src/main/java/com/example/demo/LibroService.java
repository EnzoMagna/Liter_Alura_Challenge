package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    private final ObjectMapper mapper = new ObjectMapper();
    public Libro convertirLibro(String json) throws Exception{
        return mapper.readValue(json, Libro.class);
    }
}
