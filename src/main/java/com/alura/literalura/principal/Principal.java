package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository repository;


    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void mostrarElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                             ----- Bienvenido(a) -----
                    ----------------------------------------------
                    Elija la Opcion a Través de su Numero:
                    
                    1 - Buscar Libro por Titulo
                    2 - Listar Libros Registrados
                    3 - Listar Autores Registrados
                    4 - Listar Autores Vivos En un Determinado Año
                    5 - Listar Libros por Idioma
                    6 -- 
                    
                    0 - Salir
                    ----------------------------------------------
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnDeterminadoAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");

            }
        }
    }

    public void listarLibrosPorIdioma() {

        // Muestra la lista de idiomas disponibles
        System.out.println("""
            ---------------------------------------------------
            Ingrese el idioma para buscar los libros:
            
            es - español
            en - inglés
            fr - francés
            pt - portugués
            ---------------------------------------------------
            """);

        System.out.print("Escriba el código del idioma: ");
        String idiomaSeleccionado = teclado.nextLine().trim().toLowerCase();

        // Llamar al método para buscar libros por idioma
        buscarLibrosPorIdioma(idiomaSeleccionado);
    }

    private void buscarLibrosPorIdioma(String idioma) {
        // Verifica que el idioma sea válido
        List<String> idiomasValidos = List.of("es", "en", "fr", "pt");

        if (!idiomasValidos.contains(idioma)) {
            System.out.println("Idioma no válido. Por favor, ingrese uno de los siguientes: es, en, fr, pt.");
            return;
        }

        // Buscar libros en el repositorio
        List<Libro> libros = repository.buscarLibrosPorIdioma(Idioma.valueOf(idioma.toUpperCase()));

        // Mostrar resultados
        if (libros.isEmpty()) {
            System.out.printf("No hay libros registrados en el idioma %s.\n", idioma.toUpperCase());
        } else {

            libros.forEach(l -> System.out.printf("""
                
                ----------- LIBRO -----------
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                -----------------------------
                
                """,
                    l.getTitulo(),
                    l.getAutor() != null ? l.getAutor().getNombre() : "Desconocido",
                    idioma.toUpperCase(),
                    l.getDescargas()
            ));
        }
    }

    private void listarAutoresVivosEnDeterminadoAño() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
        try {
            var fecha = Integer.valueOf(teclado.nextLine());
            List<Autor> autores = repository.buscarAutoresVivosEnDeterminadoAño(fecha);
            if (!autores.isEmpty()) {
                System.out.println();
                autores.forEach(a -> System.out.println(
                        "Autor: " + a.getNombre() +
                                "\nFecha de Nacimiento: " + a.getNacimiento() +
                                "\nFecha de Fallecimiento: " + a.getFallecimiento() +
                                "\nLibros: " + a.getLibros().stream()
                                .map(l -> l.getTitulo()).collect(Collectors.toList()) + "\n"
                ));
            } else {
                System.out.println("No hay autores vivos en el año registrado");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un año válido " + e.getMessage());
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = repository.findAll();
        System.out.println();
        autores.forEach(l -> System.out.println(
                "Autor: " + l.getNombre() +
                        "\nFecha de Nacimiento: " + l.getNacimiento() +
                        "\nFecha de Fallecimiento: " + l.getFallecimiento() +
                        "\nLibros: " + l.getLibros().stream()
                        .map(t -> t.getTitulo()).collect(Collectors.toList()) + "\n"
        ));
    }

    private void listarLibrosRegistrados() {
        // Buscar todos los libros usando el repositorio correcto
        List<Libro> libros = repository.buscarTodosLosLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        // Mostrar la información de cada libro
        libros.forEach(libro -> {
            System.out.println("""
        \n----------- LIBRO -----------
        Título: %s
        Autor: %s
        Idioma: %s
        Número de descargas: %d
        -------------------------------
        """.formatted(
                    libro.getTitulo(),
                    libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido",
                    libro.getIdioma(),
                    libro.getDescargas()
            ));
        });
    }

    public void buscarLibroPorTitulo() {

        System.out.println("Ingrese el nombre del libro que desea buscar ");
        var nombre = teclado.nextLine();

        // Obtener datos de la API
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombre.replace(" ", "+").toLowerCase());
        if (json == null || json.isEmpty()) {
            System.out.println("No se encontraron resultados para el título ingresado.");
            return;
        }

        // Convertir JSON a Datos
        var datos = conversor.obtenerDatos(json, Datos.class);

        // Buscar el primer libro válido
        Optional<DatosLibro> libroBuscado = datos.libros().stream().findFirst();
        if (libroBuscado.isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        // Guardar libro y autor en la base de datos
        try {
            guardarLibroYAutorEnBd(libroBuscado.get());
        } catch (Exception e) {
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }

        // Mostrar información del libro
        mostrarInformacionLibro(libroBuscado.get());
    }

    /**
     * Muestra la información de un libro.
     */
    private void mostrarInformacionLibro(DatosLibro libro) {
        System.out.println("""
        \n----------- LIBRO -----------
        Título: %s
        Autor: %s
        Idioma: %s
        Número de descargas: %d
        -------------------------------
        """.formatted(
                libro.titulo(),
                libro.autores().stream().map(a -> a.nombre()).findFirst().orElse("Desconocido"),
                libro.idiomas().stream().collect(Collectors.joining(", ")),
                libro.descargas()
        ));
    }

    /**
     * Guarda el libro y su autor en la base de datos.
     */
    private void guardarLibroYAutorEnBd(DatosLibro datosLibro) {
        // Convertir DatosLibro a modelo Libro
        Libro nuevoLibro = new Libro(datosLibro);

        // Verificar si el libro ya existe en la base de datos
        Optional<Libro> libroExistente = repository.buscarLibroPorNombre(datosLibro.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya se encuentra guardado en la base de datos.");
            System.out.println(" y No se puede registrar el mismo libro mas de una vez");
            return;
        }

        // Procesar autor
        Autor guardarAutor = datosLibro.autores().stream()
                .map(Autor::new)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No se encontró información del autor en los datos."));
        Optional<Autor> autorEnBd = repository.buscarAutorPorNombre(guardarAutor.getNombre());

        Autor autor;
        if (autorEnBd.isPresent()) {
            autor = autorEnBd.get();
            System.out.println("El autor ya está guardado en la base de datos.");
        } else {
            autor = guardarAutor;
            repository.save(autor);
            System.out.println("Autor guardado correctamente en la base de datos.");
        }

        // Asociar el libro al autor y guardar
        autor.addLibro(nuevoLibro); // Asegúrate de tener este método en la clase Autor
        repository.save(autor);
        System.out.println("El libro y el autor se guardaron correctamente en la base de datos.");
    }

}








