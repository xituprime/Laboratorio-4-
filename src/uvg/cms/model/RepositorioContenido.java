package model;
import java.util.ArrayList;

public class RepositorioContenido {
   private ArrayList<Contenido> listaContenidos;

   public RepositorioContenido() {
       listaContenidos = new ArrayList<>();
   }

    //nuevo contenido a ka lista
   public void agregarContenido(Contenido contenido){
        listaContenidos.add(contenido);
   }

    //eliminar contenido por id 
   public void eliminarContenido(int id){
        if (id >=  && id < listaContenidos.size()) {
            listaContenidos.remove(id);
        }else{
            System.out.println("ID no válido");
        }
   }

    //obtener todos los contenidos
   public ArrayList<Contenido> obtenerTodos(){
       return listaContenidos;
   }
}
