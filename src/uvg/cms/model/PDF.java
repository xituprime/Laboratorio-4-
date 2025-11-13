package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;

public class PDF extends Contenido {

    public PDF(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        // Constructor
        super(id, titulo, categoria, autor, "uploads/" + new File(rutaArchivo).getName());

        if (!esTipoValido(rutaArchivo)) {
            throw new IOException("Tipo de archivo no válido. Solo se permiten .pdf");
        }

        File origen = new File(rutaArchivo);
        File destino = new File(getFilePath());

        // Si no existe el directorio destino, crearlo
        destino.getParentFile().mkdirs();

        // Copiar el archivo al directorio destino
        try {
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
        }
    }

    // Apartado PDF solo recibe archivos PDF
    private boolean esTipoValido(String ruta) {
        String rutaLower = ruta.toLowerCase();
        return rutaLower.endsWith(".pdf");
    }

    @Override
    public void publicar() {
        System.out.println("El documento PDF " + getTitulo() + " por " + getAutor() + " ha sido publicado con éxito.");
    }

    @Override
    public void visualizar() {
        File archivo = new File(getFilePath());
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
                System.out.println("Visualizando PDF: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("No se pudo abrir el PDF: " + e.getMessage());
            }
        } else {
            System.err.println("El archivo PDF no existe: " + getFilePath());
        }
    }
}
