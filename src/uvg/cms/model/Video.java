package model;

public class Video extends Contenido {
    private String url;
    private int duracion;

    //constructor
    public Video(int id, String titulo, String categoria, String autor, String url, int duracion) {
        super(id, titulo, categoria, autor);
        this.url = url;
        this.duracion = duracion;
    }

    @Override
    public void publicar(){
        System.out.println("Video publicado: " + titulo + " por " + autor);
    }

    @Override
    public void visualizar(){
        System.out.println("Reproduciendo video desde: " + url + " (Duración: " + duracion + " segundos)");
    }
    
}
