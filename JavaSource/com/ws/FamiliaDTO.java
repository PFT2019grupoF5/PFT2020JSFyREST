package com.ws;

public class FamiliaDTO {

	private Long id;
	private String nombre;
	private String descrip;
	private String incompat;
	
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
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getIncompat() {
		return incompat;
	}
	public void setIncompat(String incompat) {
		this.incompat = incompat;
	}

	public FamiliaDTO(Long id, String nombre, String descrip, String incompat) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descrip = descrip;
		this.incompat = incompat;
	}
	
}
