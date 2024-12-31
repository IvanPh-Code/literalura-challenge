# 📚 Literalura - Gestión de Libros y Autores

**Literalura** es una aplicación diseñada para gestionar libros y autores utilizando una base de datos robusta y eficiente. Esta aplicación permite buscar libros desde una API externa, registrar libros y autores en una base de datos, y realizar consultas avanzadas basadas en diferentes criterios. ¡Explora el mundo de la literatura con Literalura!

---

## 🚀 Características Principales

1. **Buscar Libros por Título**
   - Conecta con la API de Gutendex para buscar libros por su nombre.
   - Guarda automáticamente los libros encontrados junto con su autor en la base de datos.

2. **Listar Libros Registrados**
   - Muestra todos los libros almacenados en la base de datos con información detallada como título, autor, idioma y número de descargas.

3. **Listar Autores Registrados**
   - Consulta todos los autores registrados, junto con su información personal y los libros asociados.

4. **Listar Autores Vivos en un Año Determinado**
   - Filtra autores que estaban vivos en un año específico.

5. **Listar Libros por Idioma**
   - Filtra los libros por idioma. Idiomas disponibles: Español, Inglés, Francés y Portugués.

6. **Guardar y Asociar Libros y Autores**
   - Guarda libros y sus autores en la base de datos de forma eficiente, evitando duplicados.

---

## 🛠️ Tecnologías Utilizadas

- **Java**: Lenguaje principal para el desarrollo de la aplicación.
- **Spring Boot**: Framework para la gestión de la base de datos y desarrollo web.
- **Hibernate**: ORM para mapear las entidades y realizar consultas a la base de datos.
- **Gutendex API**: API externa para obtener datos de libros.
- **PostgreSQL**: Base de datos relacional utilizada para almacenar libros y autores.

---

## 📋 Requisitos del Sistema

1. **Java JDK 21 o superior**
2. **IntelliJ IDEA** (opcional para desarrollo y ejecución)
3. **PostgreSQL** configurado con las tablas necesarias para libros y autores.
4. **Conexión a internet** para consultar la API externa.

---

## 💡 Cómo Usar la Aplicación

1. **Configuración Inicial**
   - Asegúrate de tener configurada la base de datos PostgreSQL y las dependencias de Maven instaladas.

2. **Ejecutar la Aplicación**
   - Descarga el proyecto y ábrelo en IntelliJ IDEA u otro IDE compatible.
   - Configura las credenciales de la base de datos en el archivo `application.properties`.
   - Ejecuta la clase principal `LiteraluraApplication`.

3. **Navegación en el Menú**
   - Una vez iniciada la aplicación, sigue las opciones del menú para realizar diversas operaciones:
     - `1`: Buscar libros por título y registrarlos en la base de datos.
     - `2`: Listar todos los libros registrados.
     - `3`: Listar autores registrados y sus libros asociados.
     - `4`: Buscar autores vivos en un año específico.
     - `5`: Filtrar libros por idioma.
     - `0`: Salir de la aplicación.

4. **Ejemplo de Entrada y Salida**
   - **Entrada**: Ingresar el título del libro o seleccionar un idioma.
   - **Salida**: Información detallada del libro o lista de libros registrados.

---

## 🎨 Ejemplo de Menú Interactivo

```plaintext
         ----- Bienvenido(a) -----
----------------------------------------------
Elija la Opción a Través de su Número:

1 - Buscar Libro por Título
2 - Listar Libros Registrados
3 - Listar Autores Registrados
4 - Listar Autores Vivos en un Determinado Año
5 - Listar Libros por Idioma

0 - Salir
----------------------------------------------
