package user;

public class Administrador extends User {

    public Administrador(String nombre, String email, String usuario, String contraseña) {
        super(nombre, email, usuario, contraseña, "ADMINISTRADOR");
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("=== Permisos del Administrador ===");
        System.out.println("✓ Crear contenido (Artículos, Imágenes, Videos, PDFs)");
        System.out.println("✓ Editar contenido");
        System.out.println("✓ Eliminar contenido");
        System.out.println("✓ Publicar/Despublicar contenido");
        System.out.println("✓ Visualizar todos los contenidos");
        System.out.println("✓ Gestionar usuarios");
        System.out.println("✓ Ver reportes y estadísticas");
        System.out.println("✓ Configurar sistema");
    }

    // Métodos específicos del Administrador
    public void crearUsuario(String nombre, String email, String usuario, String contraseña, String tipoUsuario) {
        System.out.println("Administrador " + this.nombre + " está creando un nuevo usuario: " + usuario);
    }

    public void eliminarUsuario(String usuario) {
        System.out.println("Administrador " + this.nombre + " está eliminando el usuario: " + usuario);
    }

    public void verReportes() {
        System.out.println("Visualizando reportes del sistema...");
    }
}
