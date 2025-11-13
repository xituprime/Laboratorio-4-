package model;

public abstract class Contenido {
    protected int id;
    protected String titulo;
    protected String categoria;
    protected String autor;
    protected String filePath;

    //constructor
    public Contenido(int id, String titulo, String categoria, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.autor = autor;
        this.filePath = filePath;
    }

    public Contenido(int id2, String titulo2, String categoria2, String autor2, String string) {
        
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

    public abstract String publicar();

    public abstract String visualizar();

}
