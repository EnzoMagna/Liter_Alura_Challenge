package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    Scanner sc = new Scanner(System.in);
    ConsultaAPI consultaAPI = new ConsultaAPI();
    String eleccion = "";
    List<Libro> listadoLibros = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private LibroService libroService;
    @Autowired
    private LibroRepository libroRepository;


    public void muestraElMenu() {
        do {
            System.out.println("Elija la opcion a traves de un numero \n" +
                    "***************************\n" +
                    "1- Buscar libro por titulo\n" +
                    "2- Listar libros registrados\n" +
                    "3- Listar autores registrados\n" +
                    "4- Listar autores vivos en determinado año\n" +
                    "5- Listar libros por idioma\n" +
                    "0- SALIR");
            eleccion = sc.nextLine();
            switch (eleccion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                case "2":
                    mostrarLibrosListados();
                    break;

                case "0":
                    break;
            }
        } while (!eleccion.equals("0"));


    }

    public void buscarLibroPorTitulo() {

        System.out.println("Ingrese el titulo o autor a buscar");
        String libroBuscado = URLEncoder.encode(sc.nextLine(), StandardCharsets.UTF_8);
        try {
//            LLamada a la API con la busqueda del usuario
            String devolucion = consultaAPI.consultaDeAPI(libroBuscado);
            //Busqueda de solo el primer resultado en el array de Libros
            JsonNode jsonCompleto = mapper.readTree(devolucion);
            JsonNode primerLibro = jsonCompleto.get("results").get(0);

//            Conversion a class Libro con el servicio escrito en LibroService
            Libro libroEncontrado = libroService.convertirLibro(String.valueOf(primerLibro));
            libroRepository.save(libroEncontrado);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarLibrosListados() {
        System.out.println(libroRepository.findAll());
    }
}
