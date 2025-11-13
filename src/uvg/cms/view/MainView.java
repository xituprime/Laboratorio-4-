package view;

import controller.CMSController;
import model.*;
import user.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainView extends JFrame {
    private CMSController controller;
    private JComboBox<String> tipoCombo;
    private User currentUser;
    private java.util.ArrayList<User> users;
    private JTextField tituloField;
    private JTextField categoriaField;
    private JTextField autorField;
    private JTextArea textoArea;
    private JTextField archivoField;
    private JButton examinarBtn;
    private JButton visualizarBtn;
    private JList<String> listaPublicado;
    private JList<String> listaNoPublicado;
    private DefaultListModel<String> listModelPublicado;
    private DefaultListModel<String> listModelNoPublicado;
    private JButton publicarBtn;

    public MainView() {
        controller = new CMSController();
        users = new java.util.ArrayList<>();
        // usuario por defecto: admin/admin
        users.add(new Administrador("Administrador", "admin@local", "admin", "admin"));
        currentUser = null; // nadie logueado al inicio
        initUI();
    }

    private void initUI() {
        setTitle("CMS - Gestión de Contenidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top bar: login / user info
        JPanel topBar = new JPanel(new BorderLayout());
        JPanel authPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel userLabel = new JLabel("No user");
        JButton loginBtn = new JButton("Login/Register");
        loginBtn.addActionListener(e -> showAuthDialog(userLabel));
        authPanel.add(userLabel);
        authPanel.add(loginBtn);
        topBar.add(authPanel, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // Panel izquierdo - Formulario
        JPanel formPanel = crearFormulario();

        // Panel derecho - Lista
        JPanel listPanel = crearPanelLista();

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(listPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 600));
        panel.setBorder(BorderFactory.createTitledBorder("Crear Contenido"));

        // Tipo
        panel.add(new JLabel("Tipo:"));
        tipoCombo = new JComboBox<>(new String[]{"Artículo", "Imagen", "Video", "PDF"});
        tipoCombo.addActionListener(e -> actualizarFormulario());
        panel.add(tipoCombo);
        panel.add(Box.createVerticalStrut(10));

        // Título
        panel.add(new JLabel("Título:"));
        tituloField = new JTextField();
        panel.add(tituloField);
        panel.add(Box.createVerticalStrut(5));

        // Categoría
        panel.add(new JLabel("Categoría:"));
        categoriaField = new JTextField();
        panel.add(categoriaField);
        panel.add(Box.createVerticalStrut(5));

        // Autor (automático desde usuario logueado)
        panel.add(new JLabel("Autor (automático):"));
        autorField = new JTextField();
        autorField.setEditable(false);  // No editable, se usa del usuario logueado
        panel.add(autorField);
        panel.add(Box.createVerticalStrut(10));

        // Texto (artículo)
        panel.add(new JLabel("Texto:"));
        textoArea = new JTextArea(4, 20);
        textoArea.setLineWrap(true);
        JScrollPane scrollTexto = new JScrollPane(textoArea);
        panel.add(scrollTexto);
        panel.add(Box.createVerticalStrut(5));

        // Archivo (imagen/pdf)
        panel.add(new JLabel("Archivo:"));
        JPanel archivoPanel = new JPanel(new BorderLayout());
        archivoField = new JTextField();
        examinarBtn = new JButton("Examinar");
        examinarBtn.addActionListener(e -> seleccionarArchivo());
        archivoPanel.add(archivoField, BorderLayout.CENTER);
        archivoPanel.add(examinarBtn, BorderLayout.EAST);
        panel.add(archivoPanel);
        panel.add(Box.createVerticalStrut(5));

        // (El campo de archivo sirve también para videos)


        // Botón crear
        JButton crearBtn = new JButton("Crear Contenido");
        crearBtn.addActionListener(e -> crearContenido());
        panel.add(crearBtn);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel crearPanelLista() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Contenidos"));

        // Panel con dos secciones: Publicado y No Publicado
        JPanel contentsPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Sección Publicado
        JPanel pubPanel = new JPanel(new BorderLayout(5, 5));
        pubPanel.setBorder(BorderFactory.createTitledBorder("Publicado"));
        listModelPublicado = new DefaultListModel<>();
        listaPublicado = new JList<>(listModelPublicado);
        JScrollPane scrollPublicado = new JScrollPane(listaPublicado);
        pubPanel.add(scrollPublicado, BorderLayout.CENTER);

        // Sección No Publicado
        JPanel noPublicPanel = new JPanel(new BorderLayout(5, 5));
        noPublicPanel.setBorder(BorderFactory.createTitledBorder("No Publicado"));
        listModelNoPublicado = new DefaultListModel<>();
        listaNoPublicado = new JList<>(listModelNoPublicado);
        JScrollPane scrollNoPublicado = new JScrollPane(listaNoPublicado);
        noPublicPanel.add(scrollNoPublicado, BorderLayout.CENTER);

        contentsPanel.add(pubPanel);
        contentsPanel.add(noPublicPanel);
        panel.add(contentsPanel, BorderLayout.CENTER);

        // Configurar selección: solo 1 selección total entre las dos listas
        listaPublicado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaNoPublicado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaPublicado.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (listaPublicado.getSelectedIndex() != -1) {
                    listaNoPublicado.clearSelection();
                    if (visualizarBtn != null) visualizarBtn.setEnabled(true);
                } else {
                    if (visualizarBtn != null) visualizarBtn.setEnabled(false);
                }
            }
        });

        listaNoPublicado.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (listaNoPublicado.getSelectedIndex() != -1) {
                    listaPublicado.clearSelection();
                    if (visualizarBtn != null) visualizarBtn.setEnabled(true);
                } else {
                    if (visualizarBtn != null) visualizarBtn.setEnabled(false);
                }
            }
        });

        // Botones de acción
        JPanel botonesPanel = new JPanel(new FlowLayout());
        
        publicarBtn = new JButton("Publicar");
        publicarBtn.addActionListener(e -> publicar(listaNoPublicado, listaPublicado));
        publicarBtn.setEnabled(currentUser != null && "ADMINISTRADOR".equals(currentUser.getRol()));
        
        visualizarBtn = new JButton("Visualizar");
        visualizarBtn.setEnabled(false);
        visualizarBtn.addActionListener(e -> visualizar(listaPublicado, listaNoPublicado));
        
        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.addActionListener(e -> eliminar());
        
        JButton actualizarBtn = new JButton("Actualizar");
        actualizarBtn.addActionListener(e -> { actualizarLista(); listaPublicado.clearSelection(); listaNoPublicado.clearSelection(); });

        botonesPanel.add(publicarBtn);
        botonesPanel.add(visualizarBtn);
        botonesPanel.add(eliminarBtn);
        botonesPanel.add(actualizarBtn);

        panel.add(botonesPanel, BorderLayout.SOUTH);

        actualizarLista();
        listaPublicado.clearSelection();
        listaNoPublicado.clearSelection();
        return panel;
    }

    private void actualizarFormulario() {
        String tipo = (String) tipoCombo.getSelectedItem();
        boolean esArticulo = tipo.equals("Artículo");
        boolean esImagen = tipo.equals("Imagen");
        boolean esVideo = tipo.equals("Video");
        boolean esPDF = tipo.equals("PDF");

        textoArea.setEnabled(esArticulo);
        archivoField.setEnabled(esImagen || esPDF || esVideo);
        examinarBtn.setEnabled(esImagen || esPDF || esVideo);
    }

    private void seleccionarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void showAuthDialog(JLabel userLabel) {
        JDialog dialog = new JDialog(this, "Login / Register", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);

        JTabbedPane tabs = new JTabbedPane();

        // Register panel
        JPanel reg = new JPanel();
        reg.setLayout(new BoxLayout(reg, BoxLayout.Y_AXIS));
        JTextField regName = new JTextField();
        JTextField regEmail = new JTextField();
        JTextField regUser = new JTextField();
        JPasswordField regPass = new JPasswordField();
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"ADMINISTRADOR", "EDITOR"});
        reg.add(new JLabel("Nombre:")); reg.add(regName);
        reg.add(new JLabel("Email:")); reg.add(regEmail);
        reg.add(new JLabel("Usuario:")); reg.add(regUser);
        reg.add(new JLabel("Contraseña:")); reg.add(regPass);
        reg.add(new JLabel("Rol:")); reg.add(roleCombo);
        JButton btnReg = new JButton("Registrar");
        btnReg.addActionListener(e -> {
            String nombre = regName.getText().trim();
            String email = regEmail.getText().trim();
            String usuario = regUser.getText().trim();
            String pass = new String(regPass.getPassword());
            String rol = (String) roleCombo.getSelectedItem();
            if (nombre.isEmpty() || usuario.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User u;
            if ("ADMINISTRADOR".equals(rol)) {
                u = new Administrador(nombre, email, usuario, pass);
            } else {
                u = new Editor(nombre, email, usuario, pass);
            }
            users.add(u);
            JOptionPane.showMessageDialog(dialog, "Usuario registrado: " + usuario, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar formulario de registro
            regName.setText("");
            regEmail.setText("");
            regUser.setText("");
            regPass.setText("");
        });
        reg.add(btnReg);

        // Login panel
        JPanel log = new JPanel();
        log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
        JTextField logUser = new JTextField();
        JPasswordField logPass = new JPasswordField();
        log.add(new JLabel("Usuario:")); log.add(logUser);
        log.add(new JLabel("Contraseña:")); log.add(logPass);
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> {
            String usuario = logUser.getText().trim();
            String pass = new String(logPass.getPassword());
            boolean found = false;
            for (User u : users) {
                if (u.getUsuario().equals(usuario) && u.getContraseña().equals(pass)) {
                    currentUser = u;
                    userLabel.setText(u.getUsuario() + " (" + u.getRol() + ")");
                    autorField.setText(u.getNombre());  // Actualizar campo de autor con el nombre del usuario
                    if (publicarBtn != null) {
                        publicarBtn.setEnabled("ADMINISTRADOR".equals(u.getRol()));
                    }
                    JOptionPane.showMessageDialog(dialog, "Login exitoso como " + u.getUsuario(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    found = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(dialog, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dialog.dispose();
            }
        });
        log.add(btnLogin);

        tabs.add("Registrar", reg);
        tabs.add("Login", log);

        dialog.add(tabs);
        dialog.setVisible(true);
    }

    private void crearContenido() {
        try {
            // Verificar que hay usuario logueado
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Debe iniciar sesión para crear contenido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tipo = (String) tipoCombo.getSelectedItem();
            String titulo = tituloField.getText().trim();
            String categoria = categoriaField.getText().trim();
            // Usar el nombre del usuario logueado como autor
            String autor = currentUser.getNombre();

            if (titulo.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = controller.obtenerTodos().size();

            switch (tipo) {
                case "Artículo":
                    String texto = textoArea.getText().trim();
                    if (texto.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el texto del artículo", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.crearArticulo(id, titulo, categoria, autor, texto);
                    break;
                case "Imagen":
                    String rutaImg = archivoField.getText().trim();
                    if (rutaImg.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Seleccione una imagen", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.crearImagen(id, titulo, categoria, autor, rutaImg);
                    break;
                case "Video":
                    String rutaVideo = archivoField.getText().trim();
                    if (rutaVideo.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Seleccione un archivo de video (.mp4/.mkv)", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.crearVideo(id, titulo, categoria, autor, rutaVideo);
                    break;
                case "PDF":
                    String rutaPdf = archivoField.getText().trim();
                    if (rutaPdf.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Seleccione un PDF", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.crearPDF(id, titulo, categoria, autor, rutaPdf);
                    break;
            }

            JOptionPane.showMessageDialog(this, "Contenido creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            actualizarLista();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLista() {
        // limpiar selección antes de recargar
        if (listaPublicado != null) listaPublicado.clearSelection();
        if (listaNoPublicado != null) listaNoPublicado.clearSelection();

        listModelPublicado.clear();
        listModelNoPublicado.clear();
        java.util.ArrayList<Contenido> contenidos = controller.obtenerTodos();
        for (Contenido c : contenidos) {
            String tipo = c.getClass().getSimpleName();
            // Usar ID como identificador único
            String item = "[ID:" + c.getId() + "] [" + tipo + "] " + c.getTitulo() + " - " + c.getAutor();
            if (c.isPublicado()) {
                listModelPublicado.addElement(item);
            } else {
                listModelNoPublicado.addElement(item);
            }
        }
        // limpiar selección después de recargar
        if (listaPublicado != null) listaPublicado.clearSelection();
        if (listaNoPublicado != null) listaNoPublicado.clearSelection();
    }

    private void publicar(JList<String> listaNoPublicado, JList<String> listaPublicado) {
        String selected = listaNoPublicado.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Extraer ID del string: [ID:X]
        int id = Integer.parseInt(selected.split("\\[ID:")[1].split("\\]")[0]);
        java.util.ArrayList<Contenido> contenidos = controller.obtenerTodos();
        for (Contenido c : contenidos) {
            if (c.getId() == id) {
                controller.publicarContenido(c);
                JOptionPane.showMessageDialog(this, "Publicado: " + c.getTitulo(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarLista();
                return;
            }
        }
    }

    private void visualizar(JList<String> listaPublicado, JList<String> listaNoPublicado) {
        // Intentar primero con la lista de publicado
        String selectedPub = listaPublicado.getSelectedValue();
        String selectedNoPub = listaNoPublicado.getSelectedValue();
        String selected = (selectedPub != null) ? selectedPub : selectedNoPub;
        
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extraer ID del string: [ID:X]
        int id = Integer.parseInt(selected.split("\\[ID:")[1].split("\\]")[0]);
        java.util.ArrayList<Contenido> contenidos = controller.obtenerTodos();
        for (Contenido c : contenidos) {
            if (c.getId() == id) {
                if (c instanceof Articulo) {
                    Articulo a = (Articulo) c;
                    mostrarArticulo(a);
                } else {
                    controller.visualizarContenido(c);
                }
                // limpiar selección tras visualizar
                if (listaPublicado != null) listaPublicado.clearSelection();
                if (listaNoPublicado != null) listaNoPublicado.clearSelection();
                return;
            }
        }
    }

    private void mostrarArticulo(Articulo a) {
        JDialog dialog = new JDialog(this, "Artículo - " + a.getTitulo(), true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titulo = new JLabel(a.getTitulo());
        titulo.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        JTextArea texto = new JTextArea(a.getTexto());
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setEditable(false);
        JScrollPane scroll = new JScrollPane(texto);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel meta = new JPanel(new GridLayout(1,2));
        meta.add(new JLabel("Autor: " + a.getAutor()));
        meta.add(new JLabel("Categoría: " + a.getCategoria()));
        panel.add(meta, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void eliminar() {
        // Buscar selección en ambas listas
        String selected = null;
        if (listaPublicado.getSelectedValue() != null) {
            selected = listaPublicado.getSelectedValue();
        } else if (listaNoPublicado.getSelectedValue() != null) {
            selected = listaNoPublicado.getSelectedValue();
        }
        
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un contenido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extraer ID del string: [ID:X]
        int id = Integer.parseInt(selected.split("\\[ID:")[1].split("\\]")[0]);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar contenido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.eliminarContenido(id);
            actualizarLista();
            JOptionPane.showMessageDialog(this, "Contenido eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        tituloField.setText("");
        categoriaField.setText("");
        autorField.setText("");
        textoArea.setText("");
        archivoField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView());
    }
}
