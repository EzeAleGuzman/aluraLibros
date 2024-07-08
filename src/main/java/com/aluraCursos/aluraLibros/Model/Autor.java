package com.aluraCursos.aluraLibros.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autorId;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Libro> libros;

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.nacimiento();
        this.fechaFallecimiento = datosAutor.muerte();
    }

    public Autor(){}

    public Autor(String autorDesconocido) {
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return " \n"+
                "----- Autor -----\n" +
                "Autor: " + nombre + "\n" +
                "Año D Nacimiento: " + fechaNacimiento + "\n" +
                "Año de fallecimiento: " + fechaFallecimiento+ "\n" +
                "libros: " + libros.stream().map(Libro::getTitulo).collect(Collectors.toList()) + "\n" +
                "--------------------";
    }
}
