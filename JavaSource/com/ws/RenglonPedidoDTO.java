package com.ws;

public class RenglonPedidoDTO {

	private Long id;
	private int rennro;
	private int rencant;
	private ProductoDTO producto;
	private PedidoDTO pedido;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getRennro() {
		return rennro;
	}
	public void setRennro(int rennro) {
		this.rennro = rennro;
	}
	public int getRencant() {
		return rencant;
	}
	public void setRencant(int rencant) {
		this.rencant = rencant;
	}
	public ProductoDTO getProducto() {
		return producto;
	}
	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}
	public PedidoDTO getPedido() {
		return pedido;
	}
	public void setPedido(PedidoDTO pedido) {
		this.pedido = pedido;
	}

	public RenglonPedidoDTO(Long id, int rennro, int rencant, ProductoDTO producto, PedidoDTO pedido) {
		super();
		this.id = id;
		this.rennro = rennro;
		this.rencant = rencant;
		this.producto = producto;
		this.pedido = pedido;
	}

}
