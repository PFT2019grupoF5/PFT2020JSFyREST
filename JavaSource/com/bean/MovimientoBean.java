package com.bean;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.IProductosRemote;
import com.beans.IAlmacenamientosRemote;
import com.beans.IMovimientosRemote;
import com.entities.Almacenamiento;
import com.entities.Producto;
import com.entities.Movimiento;
import com.enumerated.tipoMovimiento;
import com.exception.ServiciosException;

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
		try{
			List<Movimiento> listaMovimientos = movimientosEJBBean.getAllMovimientos(); 
			if ( listaMovimientos.isEmpty()) {
				return null; // ("No existen movimientos")
			} else {
				return "mostrarListaMovimientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movimientos");
		}
	}

	public String getMovimientosByDescripcion(String descripcion) throws ServiciosException{
		try{
			List<Movimiento> listaMovimientos = movimientosEJBBean.getMovimientosByDescripcion(descripcion); 
			if ( listaMovimientos.isEmpty()) {
				return null; // ("No existen movimientos con descripcion " + descripcion")
			} else {
				return "mostrarListaMovimientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movimiento con descripcion " + descripcion);
		}
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
		try{
			System.out.println("addMovimiento-descripcion " + descripcion);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date Dfecha = sdf.parse(fecha);
			Movimiento movimiento = new Movimiento(Dfecha, cantidad, descripcion, costo, tipoMov, productosEJBBean.getProductosByNombre(nombreProducto).get(0), almacenamientosEJBBean.getAlmacenamientosByNombre(nombreProducto).get(0));
			movimientosEJBBean.addMovimiento(movimiento);
			return "mostrarMovimiento";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, Long idProducto, Long idAlmacenamiento){
		try{
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
			return "mostrarMovimiento";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			movimientosEJBBean.removeMovimiento(id);
			return null; /// ojo esto esta mal debe ir a pagina del menu
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
