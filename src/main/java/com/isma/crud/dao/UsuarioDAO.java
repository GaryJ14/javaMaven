package com.isma.crud.dao;
import com.isma.crud.config.DB;
import com.isma.crud.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class UsuarioDAO {
	// CREATE: inserta y devuelve el id generado
    public int crear(Usuario u) {
        String sql = "INSERT INTO usuarios(nombre, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                throw new RuntimeException("No se pudo obtener el ID generado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    // READ: listar todos
    public List<Usuario> listar() {
        String sql = "SELECT id, nombre, email, password FROM usuarios ORDER BY id";
        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                lista.add(u);
            }
            return lista;

        } catch (Exception e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }
    }

    // READ: buscar por id
    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT id, nombre, email, password FROM usuarios WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return Optional.of(u);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por id", e);
        }
    }

    // UPDATE: actualiza nombre y email por id
    public boolean actualizar(int id, String nuevoNombre, String nuevoEmail) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ? WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoNombre);
            ps.setString(2, nuevoEmail);
            ps.setInt(3, id);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    // DELETE: elimina por id
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
}
