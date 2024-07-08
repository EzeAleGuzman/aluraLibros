package com.aluraCursos.aluraLibros.Repository;

import com.aluraCursos.aluraLibros.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

    Libro findByTituloIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);

    List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();
}
