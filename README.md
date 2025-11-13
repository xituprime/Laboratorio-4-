# 📑 UVG CMS - Sistema de Gestión de Contenidos

Un sistema completo de gestión de contenidos desarrollado en Java con interfaz gráfica Swing.

## ✨ Características Principales

### Tipos de Contenido
- **📝 Artículos**: Contenido de texto con título, categoría, autor y contenido
- **🖼️ Imágenes**: Soporta formatos JPG, JPEG, PNG
- **🎬 Videos**: URLs de videos con duración configurable
- **📄 PDFs**: Almacenamiento y gestión de documentos PDF

### Funcionalidades
- ✅ Crear, editar y eliminar contenido
- ✅ Publicar contenido
- ✅ Visualizar contenido
- ✅ Búsqueda por título
- ✅ Filtrado por tipo de contenido
- ✅ Interfaz gráfica intuitiva y moderna

## 🏗️ Estructura del Proyecto

```
src/uvg/cms/
├── Main.java                          # Punto de entrada
├── controller/
│   └── CMSController.java             # Controlador principal
├── view/
│   └── MainView.java                  # Interfaz gráfica
├── model/
│   ├── Publicable.java                # Interfaz
│   ├── Contenido.java                 # Clase abstracta base
│   ├── Articulo.java                  # Artículos de texto
│   ├── Imagen.java                    # Imágenes
│   ├── Video.java                     # Videos
│   ├── PDF.java                       # Documentos PDF
│   └── RepositorioContenido.java      # Gestión de contenidos
└── user/
    ├── User.java                      # Usuario abstracto
    ├── Administrador.java             # Rol administrador
    └── Editor.java                    # Rol editor
```

## 🚀 Cómo Usar

### Compilar el proyecto
```bash
cd src
javac -d ../bin uvg/cms/**/*.java uvg/cms/*.java
```

### Ejecutar la aplicación
```bash
cd bin
java uvg.cms.Main
```

## 📋 Interfaz de Usuario

### Panel Izquierdo - Creación de Contenido
1. Selecciona el tipo de contenido (Artículo, Imagen, Video, PDF)
2. Completa los campos obligatorios:
   - **Título**: Nombre del contenido
   - **Categoría**: Categoría del contenido
   - **Autor**: Autor del contenido
3. Según el tipo:
   - **Artículo**: Ingresa el texto del artículo
   - **Imagen**: Selecciona un archivo JPG, JPEG o PNG
   - **Video**: Ingresa URL y duración en segundos
   - **PDF**: Selecciona un archivo PDF
4. Haz clic en "✨ Crear Contenido"

### Panel Derecho - Gestión de Contenidos
- **Lista**: Visualiza todos los contenidos creados
- **Búsqueda**: Busca contenidos por título
- **Acciones**:
  - 📤 **Publicar**: Publica el contenido seleccionado
  - 👁️ **Visualizar**: Abre el contenido
  - 🗑️ **Eliminar**: Elimina el contenido (con confirmación)

## 👥 Roles de Usuario

### Administrador
- Crear, editar, eliminar todo contenido
- Gestionar usuarios
- Ver reportes y estadísticas
- Configurar sistema

### Editor
- Crear contenido
- Editar su propio contenido
- Publicar su contenido
- No puede gestionar otros usuarios

## 🔧 Características Técnicas

### Arquitectura MVC
- **Model**: Clases de dominio (Contenido, Articulo, Imagen, Video, PDF)
- **View**: Interfaz gráfica MainView
- **Controller**: CMSController

### Manejo de Archivos
- Almacenamiento automático en carpeta `uploads/`
- Validación de tipos de archivo
- Eliminación segura de archivos

### Herencia y Polimorfismo
- Jerarquía de clases Contenido
- Interfaz Publicable
- Métodos abstractos y polimórficos

## 📝 Ejemplo de Uso

```java
// Crear un administrador
Administrador admin = new Administrador("Juan", "juan@uvg.edu.gt", "juan_admin", "pass123");
admin.mostrarPermisos();

// Crear un editor
Editor editor = new Editor("María", "maria@uvg.edu.gt", "maria_ed", "pass456");
editor.mostrarPermisos();
```

## 🐛 Problemas Solucionados

✅ Error en `Imagen.java` - Variable static no utilizada correctamente  
✅ Error en `RepositorioContenido.java` - Variable `archivo` no resuelta  
✅ Creación de clase `PDF.java`  
✅ Implementación completa del `CMSController`  
✅ Interfaz gráfica funcional con validaciones  

## 📦 Dependencias

- Java 8+
- Swing (incluido en JDK)
- Ninguna librería externa requerida

## 👨‍💻 Autor
Laboratorio 4 - POO UVG

## 📄 Licencia
MIT
