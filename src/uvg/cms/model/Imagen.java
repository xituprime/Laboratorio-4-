package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;

public class Imagen extends Contenido {
    private static String rutaArchivo;

    public Imagen(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        // Constructor
        super(id, titulo, categoria, autor, "uploads/" + new File(rutaArchivo).getName());
        this.rutaArchivo = rutaArchivo;

        if (!esTipoValido(rutaArchivo)) {
            throw new IOException("Tipo de archivo no válido. Solo se permiten .jpg, .jpeg, .png");
        }


        File origen = new File(rutaArchivo);
        File destino = new File(getFilePath());

        // Si no existe el directorio destino, crearlo
        destino.getParentFile().mkdirs();

        // Copiar el archivo al directorio destino
        try {
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al guardar la imagen: " + e.getMessage());
        }
    }

    // Apartado imagen solo recibe archivos de imagen
    private boolean esTipoValido(String ruta) {
        String rutaLower = ruta.toLowerCase();
        return rutaLower.endsWith(".jpg") || rutaLower.endsWith(".jpeg") || rutaLower.endsWith(".png");
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public void publicar(){
        System.out.println("La imagen " + getTitulo() + " por " + getAutor() + " ha sido publicada con éxito.");
    }

    @Override
    public void visualizar() {
        File archivo = new File(getFilePath());
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
                System.out.println("Visualizando imagen: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("No se pudo abrir la imagen: " + e.getMessage());
            }
        } else {
            System.err.println("El archivo de imagen no existe: " + getFilePath());
        }
    }
    
}
