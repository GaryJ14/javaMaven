package com.isma.crud.model;

public class Tarea {
	private Integer id;
	private String titulo;
	private String descripcion;
	private String estado;
	private Integer usuario_id;
	
	public Tarea() {}
	
	public Tarea(Integer id, String titulo, String descripcion, String estado, Integer usuario_id){
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.usuario_id = usuario_id;
	}
 	// Getter y Setter de id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter y Setter de titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter y Setter de descripcion
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter y Setter de estado
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Getter y Setter de usuario_id
    public Integer getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Integer usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    @Override
    public String toString() {
        return "Tarea{id=" + id + ", titulo='" + titulo + "', descripcion='" + descripcion + ",'estado='" + estado + "'Usuario='" + usuario_id + "',}";
    }
}
