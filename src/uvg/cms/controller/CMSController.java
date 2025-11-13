package controller;

import model.*;
import java.io.IOException;

public class CMSController {
    private RepositorioContenido repositorio;

    public CMSController() {
        this.repositorio = new RepositorioContenido();
    }

    // Agregar contenido genérico
    public void agregarContenido(Contenido contenido) {
        repositorio.agregarContenido(contenido);
        System.out.println("Contenido agregado: " + contenido.getTitulo());
    }

    // Crear y agregar artículo
    public void crearArticulo(int id, String titulo, String categoria, String autor, String texto) {
        Articulo articulo = new Articulo(id, titulo, categoria, autor, texto);
        agregarContenido(articulo);
    }

    // Crear y agregar imagen
    public void crearImagen(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        Imagen imagen = new Imagen(id, titulo, categoria, autor, rutaArchivo);
        agregarContenido(imagen);
    }

    // Crear y agregar video (archivo local .mp4/.mkv)
    public void crearVideo(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        Video video = new Video(id, titulo, categoria, autor, rutaArchivo);
        agregarContenido(video);
    }

    // Crear y agregar PDF
    public void crearPDF(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        PDF pdf = new PDF(id, titulo, categoria, autor, rutaArchivo);
        agregarContenido(pdf);
    }

    // Eliminar contenido
    public void eliminarContenido(int id) {
        repositorio.eliminarContenido(id);
    }

    // Obtener todos los contenidos
    public java.util.ArrayList<Contenido> obtenerTodos() {
        return repositorio.obtenerTodos();
    }

    // Buscar por título
    public java.util.ArrayList<Contenido> buscarPorTitulo(String titulo) {
        return repositorio.buscarPorTitulo(titulo);
    }

    // Filtrar por tipo
    public java.util.ArrayList<Contenido> filtrarPorTipo(Class<?> tipo) {
        return repositorio.filtrarPorTipo(tipo);
    }

    // Publicar contenido
    public void publicarContenido(Contenido contenido) {
        contenido.publicar();
        contenido.setPublicado(true);
    }

    // Visualizar contenido
    public void visualizarContenido(Contenido contenido) {
        contenido.visualizar();
    }
}
