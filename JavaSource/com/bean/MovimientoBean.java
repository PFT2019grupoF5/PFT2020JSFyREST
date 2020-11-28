package com.bean;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.PersistenceException;

import com.beans.IProductosRemote;
import com.beans.IAlmacenamientosRemote;
import com.beans.IMovimientosRemote;
import com.entities.Almacenamiento;
import com.entities.Producto;
import com.entities.Usuario;
import com.entities.Movimiento;
import com.enumerated.tipoMovimiento;
import com.exception.ServiciosException;

import org.primefaces.context.RequestContext;

@ManagedBean(name="movimientoB")
@SessionScoped
public class MovimientoBean {
	
	@EJB
	private IMovimientosRemote movimientosEJBBean;
	@EJB
	private IProductosRemote productosEJBBean;
	@EJB
	private IAlmacenamientosRemote almacenamientosEJBBean;
	
	private long id;
	private String fecha;
	private double cantidad;
	private String descripcion;
	private double costo;
	private tipoMovimiento tipoMov;
	private Producto producto;
	private Almacenamiento almacenamiento;

	public MovimientoBean() {
	}

	//Para buscar Familia y Usuario en el add
	private long idProducto;
	private long idAlmacenamiento;

	private List<Producto> productos;
	private List<Almacenamiento> almacenamientos;


	
// Getters y Setters de atributos de la entidad

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public long getIdAlmacenamiento() {
		return idAlmacenamiento;
	}

	public void setIdAlmacenamiento(long idAlmacenamiento) {
		this.idAlmacenamiento = idAlmacenamiento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Almacenamiento getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(Almacenamiento almacenamiento) {
		this.almacenamiento = almacenamiento;
	}


	// fin getters y seters de atributos de la entidad	

	
	// para el menu one de productos
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	// para el menu one de almacenamientos
	public List<Almacenamiento> getAlmacenamientos() {
		return almacenamientos;
	}

	public void setAlmacenamientos(List<Almacenamiento> almacenamientos) {
		this.almacenamientos = almacenamientos;
	}

	// este lo agrego para el select del one menu
	public tipoMovimiento[] getTiposDeMovimientos() {
		return tipoMovimiento.values();
	}
	

	public String getAll() throws ServiciosException{
		String paginaDeRetorno = "mostrarListaMovimientos";
		try{
			List<Movimiento> listaMovimientos = movimientosEJBBean.getAllMovimientos(); 
			if ( listaMovimientos.isEmpty()) {
				return null; // ("No existen movimientos")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movimientos");
		}
	}

	public String getMovimientosByDescripcion(String descripcion) throws ServiciosException{
		String paginaDeRetorno = "mostrarListaMovimientos";
		try{
			List<Movimiento> listaMovimientos = movimientosEJBBean.getMovimientosByDescripcion(descripcion); 
			if ( listaMovimientos.isEmpty()) {
				return null; // ("No existen movimientos con descripcion " + descripcion")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movimiento con descripcion " + descripcion);
		}
	}


	
	
	public String getMovimientosEntreFechas(String fecini, String fecfin) throws ServiciosException{
		try{
			try {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));

				Date DfecInicial = sdf.parse(fecini);
				Date DfecFinal = sdf.parse(fecfin);
				//sdf.parse(fecfin);

				System.out.println("EJECUTO EN MOVIMIENTOBEAN");
				System.out.println(fecini);
				System.out.println(fecfin);
				System.out.println(DfecInicial);
				System.out.println(DfecFinal);
				
				List<Movimiento> listaMovimientos = movimientosEJBBean.getMovimientosEntreFecha(DfecInicial, DfecFinal); 
			
				if ( listaMovimientos.isEmpty()) {
					return null; // ("No existen movimientos con fecha entre " + fecini y fecfin)
				} else {
					return "mostrarListaMovimientos";
				}
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudieron obtener movimientos entre Fecha de Inicio " + fecini + " y Fecha de Fin " + fecfin );
		}
		return null;
	}

	
	public String getMovimientosById(Long id) throws ServiciosException{
		try{
			Movimiento movimiento = movimientosEJBBean.getMovimiento(id); 
			if ( movimiento == null ) {
				return null; // (""No existe movimiento con id " + id.toString()")
			} else {
				return "mostrarListaMovimientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movimientos con id " + id.toString());
		}
	}
	
 	public String add(String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, String nombreProducto, String nombreAlmacenamiento){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimiento add ok: ", "Movimiento agregado correctamente");
		String paginaDeRetorno = "mostrarMovimiento";
		try{
			if(fecha.isEmpty() || cantidad<=0 || descripcion.isEmpty() || costo<=0 || tipoMov.toString().isEmpty() || nombreProducto.isEmpty() || nombreAlmacenamiento.isEmpty()) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento add Error: ", "Es necesario ingresar todos los datos requeridos");
			}else if(descripcion.length()>250) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento add Error: ", "Los datos ingresados, superan el largo permitido. Por favor revise sus datos");
			}else if(productosEJBBean.getProductosByNombre(nombreProducto)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento add Error: ", "No se encontró el Producto en la BD. Por favor revise sus datos");
			}else if(almacenamientosEJBBean.getAlmacenamientosByNombre(nombreAlmacenamiento)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento add Error: ", "No se encontró el Almacenamiento en la BD. Por favor revise sus datos");
			}else if(tipoMov==tipoMovimiento.P) {
				// si el movimiento es una Perdida por requerimeinto se controla que haya stock suficiente del productp para registrar la perdida
				if (!productosEJBBean.StocKsuficienteDeProducto(cantidad, nombreProducto)){
					message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento add Error: ", "Stock insuficinete de Producto para registrar la Perdida, por favor revise sus datos");
			}
			}else {
				System.out.println("addMovimiento-descripcion " + descripcion);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date Dfecha = sdf.parse(fecha);
				Movimiento movimiento = new Movimiento(Dfecha, cantidad, descripcion, costo, tipoMov, productosEJBBean.getProductosByNombre(nombreProducto).get(0), almacenamientosEJBBean.getAlmacenamientosByNombre(nombreProducto).get(0));
				movimientosEJBBean.addMovimiento(movimiento);
				}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, Long idProducto, Long idAlmacenamiento){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimiento update ok: ", "Movimiento modificado correctamente");
		String paginaDeRetorno = "mostrarMovimiento";
		try{
			if(fecha.isEmpty() || cantidad<=0 || descripcion.isEmpty() || costo<=0 || tipoMov.toString().isEmpty() || idProducto==0 || idAlmacenamiento==0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento update Error: ", "Es necesario ingresar todos los datos requeridos");
			}else if(descripcion.length()>250) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento update Error: ", "Los datos ingresados, superan el largo permitido. Por favor revise sus datos");
			}else if(productosEJBBean.getProducto(idProducto)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento update Error: ", "No se encontró el Producto en la BD. Por favor revise sus datos");
			}else if(almacenamientosEJBBean.getAlmacenamiento(idAlmacenamiento)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento update Error: ", "No se encontró el Almacenamiento en la BD. Por favor revise sus datos");
			}else if(tipoMov==tipoMovimiento.P) {
				// si el movimiento es una Perdida por requerimeinto no se permiten modificaciones, se debe dar de Baja la perdida e ingresar nuevamente. 
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento update Error: ", "No se permiten realizar Modificaciones de Perdidas. en caso de error se debe Eliminar e ingresar nuevamente la Perdida");
			}else {
	            System.out.println("updateMovimiento-descripcion " + descripcion);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date Dfecha = sdf.parse(fecha);
	            Movimiento movimiento = movimientosEJBBean.getMovimiento(id);
	            movimiento.setFecha(Dfecha);
	            movimiento.setCantidad(cantidad);
	            movimiento.setDescripcion(descripcion);
	            movimiento.setCosto(costo);
	            movimiento.setTipoMov(tipoMov);
	            movimiento.setProducto(productosEJBBean.getProducto(idProducto));
	            movimiento.setAlmacenamiento(almacenamientosEJBBean.getAlmacenamiento(idAlmacenamiento));
				movimientosEJBBean.updateMovimiento(movimiento);
				}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
			return null;
		}
	}
	
	
	public String delete(Long id){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimiento delete ok: ", "Movimiento borrado correctamente");
		String paginaDeRetorno = "mostrarMovimiento";
		try{
			if(id==0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Movimiento delete Error: ", "Es necesario ingresar el id del mmovimiento a borrar");
			}else {
				if(tipoMov==tipoMovimiento.P) {
					// si el movimiento es una Perdida por requerimiento se debe devolver la Cantidad del Producto que se encontraba en la Perdida al Almacenamiento. 
					Movimiento movimiento = movimientosEJBBean.getMovimiento(id);
					producto = movimiento.getProducto();
					producto.setStkTotal(producto.getStkTotal()+movimiento.getCantidad());
					productosEJBBean.updateProducto(producto);
					}
				movimientosEJBBean.removeMovimiento(id);
				}	
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno; /// ojo esto esta mal debe ir a pagina del menu
		}catch(Exception e){
			return null;
		}
	}

	
	@PostConstruct
	public void init() {
		try {
			productos = productosEJBBean.getAllProductos();
			almacenamientos = almacenamientosEJBBean.getAllAlmacenamientos();
		} catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String addxID(String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, long idProducto, long idAlmacenamiento){
		try{
			System.out.println("addMovimiento-descripcion " + descripcion);
			System.out.println("addMovimiento-fecha " + fecha);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date Dfecha = sdf.parse(fecha);
			Movimiento movimiento = new Movimiento(Dfecha, cantidad, descripcion, costo, tipoMov, productosEJBBean.getProducto(idProducto), almacenamientosEJBBean.getAlmacenamiento(idAlmacenamiento));
			movimientosEJBBean.addMovimiento(movimiento);
			return "mostrarMovimiento";
		}catch(Exception e){
			return null;
		}
	}

	
//***********	
	public List<Movimiento> getAllLista() throws ServiciosException{

		try{
			List<Movimiento> listaMovimientos = movimientosEJBBean.getAllMovimientos(); 
			if ( listaMovimientos.isEmpty()) {
				return null; // ("No existen productos")
			} else {
				return listaMovimientos;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movimientos");
		}
	}


	
	public String deleteClase(Movimiento m){
		try{
			movimientosEJBBean.removeMovimiento(m.getId());
			return null; 
		}catch(Exception e){
			return null;
		}
	}
	

}
