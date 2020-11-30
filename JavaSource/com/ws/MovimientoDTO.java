package com.ws;

import java.util.Date;
import com.enumerated.tipoMovimiento;

public class MovimientoDTO {

	private Long id;
	private Date fecha;
	private int cantidad;
	private String descripcion;
	private double costo;
	private tipoMovimiento tipoMov;
	private ProductoDTO producto;
	private AlmacenamientoDTO almacenamiento;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public tipoMovimiento getTipoMov() {
		return tipoMov;
	}
	public void setTipoMov(tipoMovimiento tipoMov) {
		this.tipoMov = tipoMov;
	}
	public ProductoDTO getProducto() {
		return producto;
	}
	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}
	public AlmacenamientoDTO getAlmacenamiento() {
		return almacenamiento;
	}
	public void setAlmacenamiento(AlmacenamientoDTO almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public MovimientoDTO(Long id, Date fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov,
			ProductoDTO producto, AlmacenamientoDTO almacenamiento) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.costo = costo;
		this.tipoMov = tipoMov;
		this.producto = producto;
		this.almacenamiento = almacenamiento;
	}
	
}
