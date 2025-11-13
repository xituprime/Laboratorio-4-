package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;

public class Video extends Contenido{

    //constructor usando ruta local de archivo (mp4/mkv)
    public Video(int id, String titulo, String categoria, String autor, String rutaArchivo) throws IOException {
        super(id, titulo, categoria, autor, "uploads/" + new File(rutaArchivo).getName());

        if (!esTipoValido(rutaArchivo)) {
            throw new IOException("Tipo de archivo no válido. Solo se permiten .mp4 y .mkv");
        }

        File origen = new File(rutaArchivo);
        File destino = new File(getFilePath());
        destino.getParentFile().mkdirs();

        try {
            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al guardar el video: " + e.getMessage());
        }
    }

    private boolean esTipoValido(String ruta) {
        String lower = ruta.toLowerCase();
        return lower.endsWith(".mp4") || lower.endsWith(".mkv");
    }

    @Override
    public void publicar(){
        System.out.println("Video publicado: " + getTitulo() + " por " + getAutor());
    }

    @Override
    public void visualizar(){
        File archivo = new File(getFilePath());
        if (archivo.exists()) {
            try {
                Desktop.getDesktop().open(archivo);
                System.out.println("Reproduciendo video: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("No se pudo abrir el video: " + e.getMessage());
            }
        } else {
            System.err.println("El archivo de video no existe: " + getFilePath());
        }
    }
}
