package com.aluraCursos.aluraLibros;




import com.aluraCursos.aluraLibros.Principal.Principal;
import com.aluraCursos.aluraLibros.Repository.IAutorRepository;
import com.aluraCursos.aluraLibros.Repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibrosAplicacion implements CommandLineRunner {

    @Autowired
    private ILibroRepository libroRepository;
    @Autowired
    private IAutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LibrosAplicacion.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal menu = new Principal(libroRepository, autorRepository);
        menu.muestraElMenu();
    }


}
