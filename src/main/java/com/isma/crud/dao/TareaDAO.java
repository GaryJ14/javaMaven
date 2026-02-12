package com.isma.crud.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.isma.crud.config.DB;
import com.isma.crud.model.Tarea;
public class TareaDAO {
	public int crear(Tarea t) {
        String sql = "INSERT INTO tareas (titulo, descripcion, estado, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getTitulo());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado());
            ps.setInt(4, t.getUsuario_id());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                throw new RuntimeException("No se pudo obtener el ID generado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tarea", e);
        }
    }

    public List<Tarea> listar() {
        String sql = "SELECT id, titulo, descripcion, estado, usuario_id FROM tareas ORDER BY id";
        List<Tarea> lista = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar tareas", e);
        }
    }

    public List<Tarea> listarPorUsuario(int usuarioId) {
        String sql = "SELECT id, titulo, descripcion, estado, usuario_id FROM tareas WHERE usuario_id = ? ORDER BY id";
        List<Tarea> lista = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar tareas por usuario", e);
        }
    }

    public Optional<Tarea> buscarPorId(int id) {
        String sql = "SELECT id, titulo, descripcion, estado, usuario_id FROM tareas WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar tarea", e);
        }
    }

    public boolean actualizar(int id, String nuevoTitulo, String nuevaDescripcion, String nuevoEstado, int nuevoUsuarioId) {
        String sql = "UPDATE tareas SET titulo = ?, descripcion = ?, estado = ?, usuario_id = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoTitulo);
            ps.setString(2, nuevaDescripcion);
            ps.setString(3, nuevoEstado);
            ps.setInt(4, nuevoUsuarioId);
            ps.setInt(5, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar tarea", e);
        }
    }

    public boolean cambiarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE tareas SET estado = ? WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado", e);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar tarea", e);
        }
    }

    private Tarea map(ResultSet rs) throws SQLException {
        Tarea t = new Tarea();
        t.setId(rs.getInt("id"));
        t.setTitulo(rs.getString("titulo"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setEstado(rs.getString("estado"));
        t.setUsuario_id(rs.getInt("usuario_id"));
        return t;
    }
}
