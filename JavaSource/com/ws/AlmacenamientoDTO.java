package com.ws;

public class AlmacenamientoDTO {

	private Long id;
	private int volumen;
	private String nombre;
	private double costoop;
	private double capestiba;
	private double cappeso;
	private EntidadLocDTO entidadLoc;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVolumen() {
		return volumen;
	}
	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getCostoop() {
		return costoop;
	}
	public void setCostoop(double costoop) {
		this.costoop = costoop;
	}
	public double getCapestiba() {
		return capestiba;
	}
	public void setCapestiba(double capestiba) {
		this.capestiba = capestiba;
	}
	public double getCappeso() {
		return cappeso;
	}
	public void setCappeso(double cappeso) {
		this.cappeso = cappeso;
	}
	public EntidadLocDTO getEntidadLoc() {
		return entidadLoc;
	}
	public void setEntidadLoc(EntidadLocDTO entidadLoc) {
		this.entidadLoc = entidadLoc;
	}

	public AlmacenamientoDTO(Long id, int volumen, String nombre, double costoop, double capestiba, double cappeso,
			EntidadLocDTO entidadLoc) {
		super();
		this.id = id;
		this.volumen = volumen;
		this.nombre = nombre;
		this.costoop = costoop;
		this.capestiba = capestiba;
		this.cappeso = cappeso;
		this.entidadLoc = entidadLoc;
	}

}
