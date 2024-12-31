package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/* las anotaciones @JsonIgnoreProperties y
@JsonAlias para obtener los atributos deseados del cuerpo de respuesta json.*/
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(

        @JsonAlias("count") Integer total,
        @JsonAlias("results") List<DatosLibro> libros
) {
 }
