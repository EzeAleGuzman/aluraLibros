package com.aluraCursos.aluraLibros.Model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;
    private String idioma;
    private Integer numeroDeDescargas;

    public Libro(DatosLibros datosLibro) {
        this.titulo = datosLibro.titulo();
        // Manejo del autor faltante
        Optional<DatosAutor> autorOpt = datosLibro.autor().stream().findFirst();
        if (autorOpt.isPresent()) {
            this.autor = new Autor(autorOpt.get());
        } else {
            // Asignar un autor predeterminado o lanzar una excepción
            this.autor = new Autor("Autor Desconocido"); // Ejemplo de autor predeterminado
            // throw new Exception("Libro sin autor"); // Ejemplo de excepción
        }

        // Manejo del idioma faltante
        if (!datosLibro.idioma().isEmpty()) {
            this.idioma = datosLibro.idioma().get(0);
        } else {
            this.idioma = "Idioma desconocido"; // Ejemplo de idioma predeterminado
        }

        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }


    public Libro() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return " \n"+
                "----- Libro -----\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Numero de descargas: " + numeroDeDescargas+ "\n" +
                "--------------------";
    }
}
