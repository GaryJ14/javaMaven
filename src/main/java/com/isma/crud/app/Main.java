package com.isma.crud.app;
import com.isma.crud.dao.TareaDAO;
import com.isma.crud.dao.UsuarioDAO;
import com.isma.crud.model.Tarea;
import com.isma.crud.model.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Connection;

public class Main {
	 private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
	    private static final TareaDAO tareaDAO = new TareaDAO();
	    private static final Scanner sc = new Scanner(System.in);

	    public static void main(String[] args) {
	        while (true) {
	            System.out.println("\n=== MENU PRINCIPAL ===");
	            System.out.println("1) CRUD Usuarios");
	            System.out.println("2) CRUD Tareas");
	            System.out.println("0) Salir");
	            System.out.print("Opción: ");

	            String op = sc.nextLine().trim();
	            switch (op) {
	                case "1" -> menuUsuarios();
	                case "2" -> menuTareas();
	                case "0" -> { System.out.println("Saliendo..."); return; }
	                default -> System.out.println("Opción inválida.");
	            }
	        }
	    }

	    // ------------------ USUARIOS ------------------
	    private static void menuUsuarios() {
	        while (true) {
	            System.out.println("\n--- CRUD USUARIOS ---");
	            System.out.println("1) Crear");
	            System.out.println("2) Listar");
	            System.out.println("3) Buscar por ID");
	            System.out.println("4) Actualizar (nombre/email)");
	            System.out.println("5) Eliminar");
	            System.out.println("0) Volver");
	            System.out.print("Opción: ");

	            String op = sc.nextLine().trim();
	            switch (op) {
	                case "1" -> crearUsuario();
	                case "2" -> listarUsuarios();
	                case "3" -> buscarUsuario();
	                case "4" -> actualizarUsuario();
	                case "5" -> eliminarUsuario();
	                case "0" -> { return; }
	                default -> System.out.println("Opción inválida.");
	            }
	        }
	    }

	    private static void crearUsuario() {
	        System.out.print("Nombre: ");
	        String nombre = sc.nextLine().trim();
	        System.out.print("Email: ");
	        String email = sc.nextLine().trim();
	        System.out.print("Password: ");
	        String password = sc.nextLine();

	        Usuario u = new Usuario();
	        u.setNombre(nombre);
	        u.setEmail(email);
	        u.setPassword(password);

	        int id = usuarioDAO.crear(u);
	        System.out.println("Usuario creado con ID: " + id);
	    }

	    private static void listarUsuarios() {
	        List<Usuario> lista = usuarioDAO.listar();
	        if (lista.isEmpty()) System.out.println("No hay usuarios.");
	        else lista.forEach(System.out::println);
	    }

	    private static void buscarUsuario() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());
	        Optional<Usuario> u = usuarioDAO.buscarPorId(id);
	        System.out.println(u.map(Object::toString).orElse("No existe usuario con ese ID."));
	    }

	    private static void actualizarUsuario() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());
	        System.out.print("Nuevo nombre: ");
	        String nombre = sc.nextLine().trim();
	        System.out.print("Nuevo email: ");
	        String email = sc.nextLine().trim();

	        boolean ok = usuarioDAO.actualizar(id, nombre, email);
	        System.out.println(ok ? "Actualizado." : "No se actualizó (ID no existe).");
	    }

	    private static void eliminarUsuario() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());

	        boolean ok = usuarioDAO.eliminar(id);
	        System.out.println(ok ? "Eliminado." : "No se eliminó (ID no existe).");
	    }

	    // ------------------ TAREAS ------------------
	    private static void menuTareas() {
	        while (true) {
	            System.out.println("\n--- CRUD TAREAS ---");
	            System.out.println("1) Crear");
	            System.out.println("2) Listar todas");
	            System.out.println("3) Listar por usuario");
	            System.out.println("4) Buscar por ID");
	            System.out.println("5) Actualizar completa");
	            System.out.println("6) Cambiar estado");
	            System.out.println("7) Eliminar");
	            System.out.println("0) Volver");
	            System.out.print("Opción: ");

	            String op = sc.nextLine().trim();
	            switch (op) {
	                case "1" -> crearTarea();
	                case "2" -> listarTareas();
	                case "3" -> listarTareasPorUsuario();
	                case "4" -> buscarTarea();
	                case "5" -> actualizarTarea();
	                case "6" -> cambiarEstado();
	                case "7" -> eliminarTarea();
	                case "0" -> { return; }
	                default -> System.out.println("Opción inválida.");
	            }
	        }
	    }

	    private static void crearTarea() {
	        System.out.print("Título: ");
	        String titulo = sc.nextLine().trim();
	        System.out.print("Descripción: ");
	        String descripcion = sc.nextLine().trim();
	        System.out.print("Estado (PENDIENTE/EN_PROGRESO/COMPLETADA): ");
	        String estado = sc.nextLine().trim();
	        System.out.print("Usuario ID: ");
	        int usuarioId = Integer.parseInt(sc.nextLine());

	        Tarea t = new Tarea();
	        t.setTitulo(titulo);
	        t.setDescripcion(descripcion);
	        t.setEstado(estado);
	        t.setUsuario_id(usuarioId);

	        int id = tareaDAO.crear(t);
	        System.out.println("Tarea creada con ID: " + id);
	    }

	    private static void listarTareas() {
	        List<Tarea> lista = tareaDAO.listar();
	        if (lista.isEmpty()) System.out.println("No hay tareas.");
	        else lista.forEach(System.out::println);
	    }

	    private static void listarTareasPorUsuario() {
	        System.out.print("Usuario ID: ");
	        int usuarioId = Integer.parseInt(sc.nextLine());
	        List<Tarea> lista = tareaDAO.listarPorUsuario(usuarioId);
	        if (lista.isEmpty()) System.out.println("No hay tareas para ese usuario.");
	        else lista.forEach(System.out::println);
	    }

	    private static void buscarTarea() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());
	        Optional<Tarea> t = tareaDAO.buscarPorId(id);
	        System.out.println(t.map(Object::toString).orElse("No existe tarea con ese ID."));
	    }

	    private static void actualizarTarea() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());

	        System.out.print("Nuevo título: ");
	        String titulo = sc.nextLine().trim();
	        System.out.print("Nueva descripción: ");
	        String descripcion = sc.nextLine().trim();
	        System.out.print("Nuevo estado (PENDIENTE/EN_PROGRESO/COMPLETADA): ");
	        String estado = sc.nextLine().trim();
	        System.out.print("Nuevo usuario ID: ");
	        int usuarioId = Integer.parseInt(sc.nextLine());

	        boolean ok = tareaDAO.actualizar(id, titulo, descripcion, estado, usuarioId);
	        System.out.println(ok ? "Actualizada." : "No se actualizó (ID no existe).");
	    }

	    private static void cambiarEstado() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());
	        System.out.print("Nuevo estado (PENDIENTE/EN_PROGRESO/COMPLETADA): ");
	        String estado = sc.nextLine().trim();

	        boolean ok = tareaDAO.cambiarEstado(id, estado);
	        System.out.println(ok ? "Estado actualizado." : "No se actualizó (ID no existe).");
	    }

	    private static void eliminarTarea() {
	        System.out.print("ID: ");
	        int id = Integer.parseInt(sc.nextLine());

	        boolean ok = tareaDAO.eliminar(id);
	        System.out.println(ok ? "Eliminada." : "No se eliminó (ID no existe).");
	    }
}
