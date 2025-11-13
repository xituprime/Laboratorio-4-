package model;

public abstract class Contenido implements Publicable {
    protected int id;
    protected String titulo;
    protected String categoria;
    protected String autor;
    protected String filePath;
    protected boolean publicado;

    //constructor
    public Contenido(int id, String titulo, String categoria, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.autor = autor;
        this.filePath = null;
        this.publicado = false;
    }

    public Contenido(int id, String titulo, String categoria, String autor, String filePath) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.autor = autor;
        this.filePath = filePath;
        this.publicado = false;
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

    public String getFilePath() {
        return filePath;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    @Override
    public abstract void publicar();

    @Override
    public abstract void visualizar();

}
