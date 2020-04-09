package com.bean;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.IPedidosRemote;
import com.beans.IUsuariosRemote;
import com.entities.Pedido;
import com.entities.Usuario;
import com.enumerated.estadoPedido;
import com.exception.ServiciosException;

@ManagedBean(name="pedidoB")
@SessionScoped
public class PedidoBean {
	
	@EJB
	private IPedidosRemote pedidosEJBBean;
	@EJB
	private IUsuariosRemote usuariosEJBBean;
	
	private Long id;
	private Date pedfecestim;
	private Date fecha;
	private int pedreccodigo;
	private Date pedrecfecha;
	private String pedreccomentario;
	private estadoPedido pedestado;
	private Usuario usuario;

	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public PedidoBean() {
	}

	private List<Usuario> usuarios;
	private String nomAcceso;

	public pedidoValor[] valorList;
	
	
	public Pedido selected;

	
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNomAcceso() {
		return nomAcceso;
	}

	public void setNomAcceso(String nomAcceso) {
		this.nomAcceso = nomAcceso;
	}


	// para el menu one de usuarios
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	
	// este lo agrego para el select del one menu
	public estadoPedido[] getTiposDePedEstado() {
		return estadoPedido.values();
	}
	

	public String getAll() throws ServiciosException{
		try{
			List<Pedido> listaPedidos = pedidosEJBBean.getAllPedidos(); 
			if ( listaPedidos.isEmpty()) {
				return null; // ("No existen pedidos")
			} else {
				return "mostrarListaPedidos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de pedidos");
		}
	}

	public String getPedidosById(Long id) throws ServiciosException{
		try{
			Pedido pedido = pedidosEJBBean.getPedido(id); 
			if ( pedido == null ) {
				return null; // (""No existe pedido con id " + id.toString()")
			} else {
				return "mostrarListaPedidos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener pedidos con id " + id.toString());
		}
	}
	
	public String add(Date pedfecestim, Date fecha, int pedreccodigo, Date pedrecfecha, String pedreccomentario, estadoPedido pedestado, String nomAcceso){
		try{
			System.out.println("addPedido-pedreccomentario " + pedreccomentario);
			Pedido pedido = new Pedido(pedfecestim, fecha, pedreccodigo, pedrecfecha, pedreccomentario, pedestado, usuariosEJBBean.getUsuariosBynomAcceso(nomAcceso).get(0));
			pedidosEJBBean.addPedido(pedido);
			return "mostrarPedido";
		}catch(Exception e){
			return null;
		}
	}
		
	
	public String update(Long id, Date pedfecestim, Date fecha, int pedreccodigo, Date pedrecfecha, String pedreccomentario, estadoPedido pedestado, Long idUsuario){
		try{
            System.out.println("updatePedido-pedreccomentario " + pedreccomentario);
            Pedido pedido = pedidosEJBBean.getPedido(id);
            pedido.setPedfecestim(pedfecestim);
            pedido.setFecha(pedrecfecha);
            pedido.setPedreccodigo(pedreccodigo);
            pedido.setPedrecfecha(pedrecfecha);
            pedido.setPedreccomentario(pedreccomentario);
            pedido.setPedestado(pedestado);
            pedido.setUsuario(usuariosEJBBean.getUsuario(idUsuario));
			pedidosEJBBean.updatePedido(pedido);
			return "mostrarPedido";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			pedidosEJBBean.removePedido(id);
			return null; /// ojo esto esta mal debe ir a pagina del menu
		}catch(Exception e){
			return null;
		}
	}

	@PostConstruct
	public void init() {
		try {
				usuarios = usuariosEJBBean.getAllUsuarios();
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

	
	
	
	
//A AGREGAR
	
	public String addxID(Date pedfecestim, Date fecha, int pedreccodigo, Date pedrecfecha, String pedreccomentario, estadoPedido pedestado, long idAcceso){
		try{
			System.out.println("addPedido-pedreccomentario " + pedreccomentario);
			Pedido pedido = new Pedido(pedfecestim, fecha, pedreccodigo, pedrecfecha, pedreccomentario, pedestado, usuariosEJBBean.getUsuario(idAcceso));
			pedidosEJBBean.addPedido(pedido);
			return "mostrarPedido";
		}catch(Exception e){
			return null;
		}
	}

	public String deleteClase(Pedido p){
		try{
			pedidosEJBBean.removePedido(p.getId());
			return null; 
		}catch(Exception e){
			return null;
		}
	}

	public void updateClase(Pedido p){
		try{
            
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se modific� el registro correctamente"));
			
            pedidosEJBBean.updatePedido(p);
		}catch(Exception e){
		}
	}
	
	
	
	
	public void onRowEdit(RowEditEvent event) {  
		Pedido ped =(Pedido) event.getObject();
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success : ",  "The row with the id "+ ped.getId() +" has been updated successfully.");
        FacesContext.getCurrentInstance().addMessage(null, message);
   
	}
	
	public void onCellEdit(CellEditEvent event) throws ServiciosException {  
	    Object oldValue = event.getOldValue();  
	    Object newValue = event.getNewValue();  
	    
	    System.out.println(oldValue);
	    System.out.println(newValue);
	 
	    
	    if(!oldValue.equals(newValue)){
	        DataTable table = (DataTable) event.getSource();
	        Pedido pedido = (Pedido) table.getRowData();
	        pedidosEJBBean.updatePedido(pedido);

	        FacesContext.getCurrentInstance().addMessage(null, 
	            new FacesMessage(FacesMessage.SEVERITY_INFO,"Actualizaci�n Correcta", "Valor actualizado: " + newValue));  
	    }
	}
	
	/*
	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
				
		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dato Actualizado. Valor anterior: "+oldValue, "Valor actual: " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	*/

	//A AGREGAR
	
		public pedidoValor[] getValoresList() throws ServiciosException {
			List<Pedido> pedidos = pedidosEJBBean.getAllPedidos();
			 
			 int i = 0;
			 if (pedidos.size()>0) {
				 //Si hay locales, recorro la lista y la cargo
				 valorList = new pedidoValor[pedidos.size()];
				 
				for (Pedido p : pedidos) {
						 valorList[i] = new pedidoValor(p.getId(), p.getFecha(), p.getPedreccomentario());
						 System.out.println(p.getId() + " " + p.getFecha() + " " + p.getPedreccodigo());
						 i=i+1;
				}	 
			 }else{
				valorList[0] = new pedidoValor(0, null, null);
			 	System.out.println("Combo Sin Datos");
			 }
		 	 return valorList;
	   }
		

		 public static class pedidoValor{
				private Long valorID;
				private Date valorFecha;

				private Date valorPedfecestim;
				private int valorPedreccodigo;
				private Date valorPedrecfecha;
				private String valorPedreccomentario;
				private estadoPedido valorPedestado;
				private Usuario valorUsuario;
			 
				
			public pedidoValor(long valorid, Date valorfecha, String valorcomentario){
				this.valorID = valorid;
		    	this.valorFecha = valorfecha;
		    	this.valorPedreccomentario=valorcomentario;
			}
			 
			 
			 public long getValorValue(){
				 return valorID;
			 }
			 public Date getValorLabel(){
				 return valorFecha;
			 }

			 public String getValorComentario(){
				 return valorPedreccomentario;
			 }

		 }

		 
		 
		 
		 
		 
//********		 
		public List<Pedido> getAllLista() throws ServiciosException{
			try{
				List<Pedido> listaPedidos = pedidosEJBBean.getAllPedidos(); 
				if ( listaPedidos.isEmpty()) {
					return null; // ("No existen pedidos")
				} else {
					return listaPedidos;
				}
			}catch(PersistenceException e){
				throw new ServiciosException("No se pudo obtener lista de pedidos");
			}
		}

		public Pedido getSelected() {
		    return selected;
		}
		 
		public void setSelected(Pedido p) {
		    this.selected = p;
		}


		
		public Pedido getPedById(Long id) throws ServiciosException{
			try{
				Pedido pedido = pedidosEJBBean.getPedido(id); 
				if ( pedido == null ) {
					return null; // (""No existe pedido con id " + id.toString()")
				} else {
					return pedido;
				}
			}catch(PersistenceException e){
				throw new ServiciosException("No se pudo obtener pedidos con id " + id.toString());
			}
		}
		



}
