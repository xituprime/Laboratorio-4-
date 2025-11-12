package model;

public class Contenido {
    protected int id;
    protected String titulo;
    protected String categoria;
    protected String autor;

    //constructor
    public Contenido(int id, String titulo, String categoria, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.autor = autor;
    }

    //getters y setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAutor() {
        return autor;
    }

    public void publicar(){
        System.out.println("El contenido '" + titulo + "' ha sido publicado.");
    }

    public void visualizar(){
        System.out.println("Visualizando el contenido: " + titulo);
    }

}
