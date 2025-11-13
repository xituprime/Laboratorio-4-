package user;

public abstract class User {
    protected String nombre;
    protected String email;
    protected String usuario;
    protected String contraseña;
    protected String rol;

    public User(String nombre, String email, String usuario, String contraseña, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getRol() {
        return rol;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return this.contraseña;
    }

    // Método abstracto
    public abstract void mostrarPermisos();

    @Override
    public String toString() {
        return "Usuario: " + usuario + " (" + rol + ") - " + nombre;
    }
}
