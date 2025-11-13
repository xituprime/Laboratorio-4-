package model;

public class Articulo extends Contenido{
    private String texto;

    //constructor
    public Articulo(int id, String titulo, String categoria, String autor, String texto) {
        super(id, titulo, categoria, autor);
        this.texto = texto;
    }

    @Override
    public void publicar(){
        System.out.println("Artículo publicado: " + titulo + " por " + autor);
    }

    @Override
    public void visualizar(){
        System.out.println("Mostrando contenido del artículo:\n" + texto);
    }

    public String getTexto() {
        return texto;
    }
}
