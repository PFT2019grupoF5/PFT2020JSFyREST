package com.ws;

public class UsuarioDTO {

	private Long id;
	private String nombre;
	private String apellido;
	private String nomAcceso;
	private String contrasena;
	private String correo;
	private PerfilDTO perfil;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNomAcceso() {
		return nomAcceso;
	}
	public void setNomAcceso(String nomAcceso) {
		this.nomAcceso = nomAcceso;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public PerfilDTO getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}
	
	public UsuarioDTO(Long id, String nombre, String apellido, String nomAcceso, String contrasena, String correo,
			com.ws.PerfilDTO perfil) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nomAcceso = nomAcceso;
		this.contrasena = contrasena;
		this.correo = correo;
		this.perfil = perfil;
	}
	
}
