package com.aluraCursos.aluraLibros.Principal;

import com.aluraCursos.aluraLibros.Model.Autor;
import com.aluraCursos.aluraLibros.Model.Datos;
import com.aluraCursos.aluraLibros.Model.DatosLibros;
import com.aluraCursos.aluraLibros.Model.Libro;
import com.aluraCursos.aluraLibros.Repository.IAutorRepository;

import com.aluraCursos.aluraLibros.Repository.ILibroRepository;
import com.aluraCursos.aluraLibros.Service.ConsumoApi;
import com.aluraCursos.aluraLibros.Service.ConsumoApi;
import com.aluraCursos.aluraLibros.Service.ConvierteDatos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.io.IOException;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";

    private ILibroRepository libroRepository;
    private IAutorRepository autorRepository;

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


        public void muestraElMenu() throws UnsupportedEncodingException {
            var opcion = -1;
            do {
                var menu = """
                                            
                                            
                        *** Bienvenido a Libros-Alura ***
                        ************* MENU **************
                        *********************************
                                    
                        1- Buscar libro por titulo
                        2- Listar libros registrados
                        3- Listar autores registrados
                        4- Listar autores vivos en un determinado año
                        5- Listar libros por idioma
                        6- Listar Libros Mas Descargados
                        0- Salir
                        """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;

                case 2:
                    VerLibros();
                    break;
                case 3 :
                    verAutores();
                    break;
                case 4:
                    VerAutorPorAño();
                    break;
                case 5:
                    LibroPorIdioma();
                    break;
                    case 6:
                        LibrosMasDescargados()   ;
                        break;

                    case 0:
                        System.out.println("Saliendo del sistema");
                        break;
                    default:
                        System.out.println("Opcion invalida intente nuevamente.");
                }

                try {
                    opcion = teclado.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Ingrese un número válido.");
                    teclado.nextLine(); // Limpia el búfer del escáner para evitar bucles infinitos
                    opcion = -1; // Establece una opción no válida para forzar otra selección de menú
                }

            } while (opcion != 0);
        }




    private void buscarLibro() throws UnsupportedEncodingException {
        System.out.println("Ingrese el titulo del libro: ");
        var nombreLibro = teclado.nextLine();
        var nombreLibroEncoded = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString());
        var url = URL_BASE + nombreLibroEncoded.replace(" ","+");
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibroEncoded.replace(" ", "+"));
        System.out.println(url);
        System.out.println(json);
        List<DatosLibros> datosLibro = conversor.obtenerDatos(json, Datos.class).results();
        Optional<DatosLibros> libroBuscado = datosLibro.stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibroEncoded.toLowerCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            var libroEncontrado = libroBuscado.get();

            //Verificacion si el libro ya esta en la base de datos
            Libro libroExistente = libroRepository.findByTituloIgnoreCase(libroEncontrado.titulo());

            if (libroExistente != null) {
                System.out.println("El libro " + libroExistente.getTitulo() + " ya esta registrado");
            } else {
                var libro = new Libro(libroEncontrado);
                libroRepository.save(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void VerLibros() {
        List<Libro> libros = libroRepository.findAll();
        try{
            if(libros.isEmpty()){
                System.out.println("No Tienes libros registrados...");
            }else {
            for (Libro libro : libros){
                System.out.println(libro.toString());
            }
        }
        }catch (Exception e){
            System.out.println("Error" + e);
        }

    }

    private void verAutores() {
        List<Autor> autores = autorRepository.findAll();
        try{
            if(autores.isEmpty()){
                System.out.println("No Tienes autores registrados...");
            }else {
                for (Autor autor : autores){
                    System.out.println(autor.toString());
                }
            }
        }catch (Exception e){
            System.out.println("Error" + e);
        }

    }

    private void VerAutorPorAño() {
        System.out.println("Ingrese el año en el que estuvo vivo su autor: ");
        var año = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autores = autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(año,año);
        for (Autor autor : autores){
            System.out.println(autor.toString());
        }
    }

    private void LibroPorIdioma(){
        System.out.println("""
                Ingrese el idioma para buscar los libros
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        String idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        for (Libro libro : libros){
            System.out.println(libro.toString());
        }
    }

    private void LibrosMasDescargados() {

        List<Libro> libros = libroRepository.findTop5ByOrderByNumeroDeDescargasDesc();

        if (libros.isEmpty()) {
            System.out.println("No hay libros descargados aun");
        } else {
            System.out.println("Libros mas descargados:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

}
