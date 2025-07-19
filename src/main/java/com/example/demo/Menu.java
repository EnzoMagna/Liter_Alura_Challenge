package com.example.demo;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    ConsultaAPI consultaAPI = new ConsultaAPI();
    String eleccion = "";
    public void muestraElMenu(){
        do {
            System.out.println("Elija la opcion a traves de un numero \n" +
                    "***************************\n" +
                    "1- Buscar libro por titulo\n" +
                    "2- Listar libros registrados\n" +
                    "3- Listar autores registrados\n" +
                    "4- Listar autores vivos en determinado año\n" +
                    "5- Listar libros por idioma\n" +
                    "0- SALIR");
            eleccion=sc.nextLine();
            switch (eleccion) {case "1":buscarLibroPorTitulo();
                case "0":break;
            }
        } while (!eleccion.equals("0"));


    }

    public void buscarLibroPorTitulo() {

        System.out.println("Ingrese el titulo o autor a buscar");
        String libroBuscado = sc.nextLine();
        try {
            consultaAPI.consultaDeAPI(libroBuscado);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
