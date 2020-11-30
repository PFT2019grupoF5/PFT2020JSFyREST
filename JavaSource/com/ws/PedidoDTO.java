package com.ws;

import java.util.Date;
import com.enumerated.estadoPedido;

public class PedidoDTO {

	private Long id;
	private Date pedfecestim;
	private Date fecha;
	private int pedreccodigo;
	private Date pedrecfecha;
	private String pedreccomentario;
	private estadoPedido pedestado;
	private UsuarioDTO usuario;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getPedfecestim() {
		return pedfecestim;
	}
	public void setPedfecestim(Date pedfecestim) {
		this.pedfecestim = pedfecestim;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getPedreccodigo() {
		return pedreccodigo;
	}
	public void setPedreccodigo(int pedreccodigo) {
		this.pedreccodigo = pedreccodigo;
	}
	public Date getPedrecfecha() {
		return pedrecfecha;
	}
	public void setPedrecfecha(Date pedrecfecha) {
		this.pedrecfecha = pedrecfecha;
	}
	public String getPedreccomentario() {
		return pedreccomentario;
	}
	public void setPedreccomentario(String pedreccomentario) {
		this.pedreccomentario = pedreccomentario;
	}
	public estadoPedido getPedestado() {
		return pedestado;
	}
	public void setPedestado(estadoPedido pedestado) {
		this.pedestado = pedestado;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public PedidoDTO(Long id, Date pedfecestim, Date fecha, int pedreccodigo, Date pedrecfecha, String pedreccomentario,
			estadoPedido pedestado, UsuarioDTO usuario) {
		super();
		this.id = id;
		this.pedfecestim = pedfecestim;
		this.fecha = fecha;
		this.pedreccodigo = pedreccodigo;
		this.pedrecfecha = pedrecfecha;
		this.pedreccomentario = pedreccomentario;
		this.pedestado = pedestado;
		this.usuario = usuario;
	}

}
