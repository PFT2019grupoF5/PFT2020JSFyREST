package com.ws;

import com.enumerated.tipoLoc;

public class EntidadLocDTO {

	private Long id;
	private int codigo;
	private String nombre;
	private String direccion;
	private tipoLoc tipoloc;
	private CiudadDTO ciudad;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public tipoLoc getTipoloc() {
		return tipoloc;
	}
	public void setTipoloc(tipoLoc tipoloc) {
		this.tipoloc = tipoloc;
	}
	public CiudadDTO getCiudad() {
		return ciudad;
	}
	public void setCiudad(CiudadDTO ciudad) {
		this.ciudad = ciudad;
	}
	
	public EntidadLocDTO(Long id, int codigo, String nombre, String direccion, tipoLoc tipoloc, CiudadDTO ciudad) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombre = nombre;
		this.direccion = direccion;
		this.tipoloc = tipoloc;
		this.ciudad = ciudad;
	}
	
}
