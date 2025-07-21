package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu {
    String menuText = "Elija la opcion a traves de un numero \n" +
            "***************************\n" +
            "1- Buscar libro por titulo\n" +
            "2- Listar libros registrados\n" +
            "3- Listar autores registrados\n" +
            "4- Listar autores vivos en determinado año\n" +
            "5- Listar libros por idioma\n" +
            "99 -Eliminar data guardada de libros/autores registrados(DEBUG)\n" +
            "0- SALIR";

    Scanner sc = new Scanner(System.in);
    ConsultaAPI consultaAPI = new ConsultaAPI();
    String eleccion = "";
    List<Libro> listadoLibros = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private LibroService libroService;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;


    public void muestraElMenu() {
        do {
            System.out.println(menuText);

            eleccion = sc.nextLine();
            switch (eleccion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                case "2":
                    mostrarLibrosListados();
                    break;
                case "3":
                    mostrarAutoresRegistrados();
                    break;
                case "4":
                    mostrarAutoresVivosPorAño();
                    break;
                case "5":
                    mostrarLibrosPorIdioma();
                    break;
                case "99":
                    eliminarDatosTablas();
                    break;
                case "0":
                    System.out.println("¡Gracias por usar el servicio de LiterAlura!\nCerrando la Aplicacion");
                    break;
                default:
                    System.out.println("El Input es incorrecto, intente de nuevo");
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
            JsonNode results = jsonCompleto.get("results");

//            Conversion a class Libro con el servicio escrito en LibroService
            if (results == null || !results.elements().hasNext()) {
                System.out.println("No se ha encontrado un libro con ese nombre, intente nuevamente");
            } else {
                JsonNode primerLibro = jsonCompleto.get("results").get(0);
                Libro libroEncontrado = libroService.convertirLibro(String.valueOf(primerLibro));

                if (libroRepository.existsByGutendexId(libroEncontrado.getGutendexId())) {
                    System.out.println("El libro ya esta en el registro");
                } else {
                    libroRepository.save(libroEncontrado);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarLibrosListados() {
        libroRepository.findAll().forEach(System.out::println);
    }


    private void mostrarAutoresRegistrados() {
        autorRepository.findAll().forEach(System.out::println);
    }

    private void mostrarAutoresVivosPorAño() {
        System.out.println("Escriba año para buscar autores vivos");
        var añoBuscadoAutoresVivos = sc.nextInt();
        sc.nextLine();
        List<Autor> autoresVivos = autorRepository.vivosEnAnio(añoBuscadoAutoresVivos);
        autoresVivos.forEach(System.out::println);

    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma en el que quiere buscar los libros\n"+
                "en- Ingles\n"+
                "es- Español\n"+
                "pt- Portugués");
        var idioma = sc.nextLine().toLowerCase();
        libroRepository.findByIdiomas(idioma).forEach(System.out::println);
    }

    private void eliminarDatosTablas() {
        libroRepository.deleteAll();
        autorRepository.deleteAll();
        System.out.println("**********************\n"+
                "¡BASE DE DATOS RESETEADA!\n"+
                "**********************");
    }

}
