package model;

import java.util.ArrayList;
import java.io.File;

public class RepositorioContenido {
   private ArrayList<Contenido> listaContenidos;

   public RepositorioContenido() {
       listaContenidos = new ArrayList<>();
   }

    //nuevo contenido a la lista
   public void agregarContenido(Contenido contenido){
        listaContenidos.add(contenido);
   }

    //eliminar contenido por id 
   public void eliminarContenido(int id){
        // Buscar por ID, no por índice
        for (int i = 0; i < listaContenidos.size(); i++) {
            if (listaContenidos.get(i).getId() == id) {
                Contenido contenido = listaContenidos.get(i);
                String rutaArchivo = contenido.getFilePath();
                String nombreArchivo = "";

                //comprobar que archivo existe
                if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
                    File archivo = new File(rutaArchivo);    
                    nombreArchivo = archivo.getName();
                
                    if (archivo.exists()) {
                        if (archivo.delete()) {
                            System.out.println("Archivo eliminado: " + archivo.getName());
                        } else {
                            System.out.println("No se pudo eliminar el archivo: " + archivo.getName());
                        }
                    } else {
                        System.out.println("El archivo no existe: " + rutaArchivo);
                    }
                }

                listaContenidos.remove(i);
                System.out.println("Contenido con ID " + id + " y nombre " + nombreArchivo + " eliminado.");
                return;
            }
        }
        System.out.println("ID no válido");
   }

    //obtener todos los contenidos
   public ArrayList<Contenido> obtenerTodos(){
       return listaContenidos;
   }

   //buscar por titulo
   public ArrayList<Contenido> buscarPorTitulo(String titulo){
       ArrayList<Contenido> resultados = new ArrayList<>();

       for(Contenido c : listaContenidos){
           if(c.getTitulo().toLowerCase().contains(titulo.toLowerCase())){
               resultados.add(c);
           }
       }
       return resultados;
   }

   //buscarpor tipo
    public ArrayList<Contenido> filtrarPorTipo(Class<?> tipo) {
        ArrayList<Contenido> resultados = new ArrayList<>();

        for (Contenido c : listaContenidos) {
            if (c.getClass() == tipo) {
                resultados.add(c);
            }
        }

        return resultados;
    }

}
