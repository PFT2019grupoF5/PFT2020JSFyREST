package com.ws;

import java.util.Date;

import com.enumerated.Segmentacion;

public class ProductoDTO {
	
	private Long id;
	private String nombre;
	private String lote;
	private double precio;
	private Date felab;
	private Date fven;
	private double peso;
	private double volumen;
	private int estiba;
	private double stkMin;
	private double stkTotal;
	private Segmentacion segmentac;
	private UsuarioDTO usuario;
	private FamiliaDTO familia;

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
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Date getFelab() {
		return felab;
	}
	public void setFelab(Date felab) {
		this.felab = felab;
	}
	public Date getFven() {
		return fven;
	}
	public void setFven(Date fven) {
		this.fven = fven;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getVolumen() {
		return volumen;
	}
	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}
	public int getEstiba() {
		return estiba;
	}
	public void setEstiba(int estiba) {
		this.estiba = estiba;
	}
	public double getStkMin() {
		return stkMin;
	}
	public void setStkMin(double stkMin) {
		this.stkMin = stkMin;
	}
	public double getStkTotal() {
		return stkTotal;
	}
	public void setStkTotal(double stkTotal) {
		this.stkTotal = stkTotal;
	}
	public Segmentacion getSegmentac() {
		return segmentac;
	}
	public void setSegmentac(Segmentacion segmentac) {
		this.segmentac = segmentac;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public FamiliaDTO getFamilia() {
		return familia;
	}
	public void setFamilia(FamiliaDTO familia) {
		this.familia = familia;
	}

	public ProductoDTO(Long id, String nombre, String lote, double precio, Date felab, Date fven, double peso,
			double volumen, int estiba, double stkMin, double stkTotal, Segmentacion segmentac, UsuarioDTO usuario,
			FamiliaDTO familia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.lote = lote;
		this.precio = precio;
		this.felab = felab;
		this.fven = fven;
		this.peso = peso;
		this.volumen = volumen;
		this.estiba = estiba;
		this.stkMin = stkMin;
		this.stkTotal = stkTotal;
		this.segmentac = segmentac;
		this.usuario = usuario;
		this.familia = familia;
	}
	
}
