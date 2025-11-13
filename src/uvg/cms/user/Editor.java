package user;

public class Editor extends User {

    public Editor(String nombre, String email, String usuario, String contraseña) {
        super(nombre, email, usuario, contraseña, "EDITOR");
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("=== Permisos del Editor ===");
        System.out.println("✓ Crear contenido (Artículos, Imágenes, Videos, PDFs)");
        System.out.println("✓ Editar su propio contenido");
        System.out.println("✓ Eliminar su propio contenido");
        System.out.println("✓ Publicar su contenido");
        System.out.println("✗ Ver contenido de otros usuarios");
        System.out.println("✗ Gestionar usuarios");
        System.out.println("✗ Configurar sistema");
    }

    // Métodos específicos del Editor
    public void crearContenidoPropio(String titulo, String tipo) {
        System.out.println("Editor " + this.nombre + " está creando un nuevo " + tipo + ": " + titulo);
    }

    public void editarContenido(String idContenido) {
        System.out.println("Editor " + this.nombre + " está editando el contenido ID: " + idContenido);
    }

    public void publicarContenido(String idContenido) {
        System.out.println("Editor " + this.nombre + " está publicando el contenido ID: " + idContenido);
    }
}
