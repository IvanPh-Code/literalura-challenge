package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Libro> libros = new ArrayList<>();

    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    public Autor(){

    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.nacimineto();
        this.fallecimiento = datosAutor.fallecimineto();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));// Asigna este autor a cada libro
        this.libros = libros; // Actualiza la lista de libros del autor
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    @Override
    public String toString() {
        return " Autor { " +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", fallecimiento=" + fallecimiento +
                ", libros=" + libros +
                '}';
    }
}
