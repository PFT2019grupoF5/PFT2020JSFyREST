package com.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.IProductosRemote;
import com.beans.IAlmacenamientosRemote;
import com.beans.IMovis2Remote;
import com.entities.Almacenamiento;
import com.entities.Producto;
import com.entities.Movi2;
import com.enumerated.tipoMovimiento;
import com.exception.ServiciosException;

@ManagedBean(name="movi2B")
@SessionScoped
public class Movi2Bean {
	
	@EJB
	private IMovis2Remote movis2EJBBean;
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

	private List<Producto> productos;
	private long idProducto;
	private List<Almacenamiento> almacenamientos;
	private long idAlmacenamiento;

	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public Movi2Bean() {
	}


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
	public tipoMovimiento[] getTiposDeMovis2() {
		return tipoMovimiento.values();
	}
	

	public String getAll() throws ServiciosException{
		try{
			List<Movi2> listaMovis2 = movis2EJBBean.getAllMovis2(); 
			if ( listaMovis2.isEmpty()) {
				return null; // ("No existen movis2")
			} else {
				return "mostrarListaMovis2";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movis2");
		}
	}

	public String getMovis2ByDescripcion(String descripcion) throws ServiciosException{
		try{
			List<Movi2> listaMovis2 = movis2EJBBean.getMovis2ByDescripcion(descripcion); 
			if ( listaMovis2.isEmpty()) {
				return null; // ("No existen movis2 con descripcion " + descripcion")
			} else {
				return "mostrarListaMovis2";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movi2 con descripcion " + descripcion);
		}
	}

	public String getMovis2ById(Long id) throws ServiciosException{
		try{
			Movi2 movi2 = movis2EJBBean.getMovi2(id); 
			if ( movi2 == null ) {
				return null; // (""No existe movi2 con id " + id.toString()")
			} else {
				return "mostrarListaMovis2";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movis2 con id " + id.toString());
		}
	}
	
	public String add(String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, String nombreProducto, String nombreAlmacenamiento){
		try{
			System.out.println("addMovi2-descripcion " + descripcion);

			try {
	            DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
				Movi2 movi2 = new Movi2(sourceFormat.parse(fecha), cantidad, descripcion, costo, tipoMov, productosEJBBean.getProductosByNombre(nombreProducto).get(0), almacenamientosEJBBean.getAlmacenamientosByNombre(nombreProducto).get(0));
				movis2EJBBean.addMovi2(movi2);
			}catch (Exception e){
	            e.printStackTrace();
	        }
			return "mostrarMovi2";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, Long idProducto, Long idAlmacenamiento){
		try{
            System.out.println("updateMovi2-descripcion " + descripcion);
            DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
            Movi2 movi2 = movis2EJBBean.getMovi2(id);
            movi2.setFecha(sourceFormat.parse(fecha));
            movi2.setCantidad(cantidad);
            movi2.setDescripcion(descripcion);
            movi2.setCosto(costo);
            movi2.setTipoMov(tipoMov);
            movi2.setProducto(productosEJBBean.getProducto(idProducto));
            movi2.setAlmacenamiento(almacenamientosEJBBean.getAlmacenamiento(idAlmacenamiento));
			movis2EJBBean.updateMovi2(movi2);
			return "mostrarMovi2";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			movis2EJBBean.removeMovi2(id);
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
			System.out.println("addMovi2-descripcion " + descripcion);

			try {
	            DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");
				Movi2 movi2 = new Movi2(sourceFormat.parse(fecha), cantidad, descripcion, costo, tipoMov, productosEJBBean.getProducto(idProducto), almacenamientosEJBBean.getAlmacenamiento(idAlmacenamiento));
				movis2EJBBean.addMovi2(movi2);
			}catch (Exception e){
	            e.printStackTrace();
	        }
			return "mostrarMovi2";
		}catch(Exception e){
			return null;
		}
	}

	
//***********	
	public List<Movi2> getAllLista() throws ServiciosException{

		try{
			List<Movi2> listaMovis2 = movis2EJBBean.getAllMovis2(); 
			if ( listaMovis2.isEmpty()) {
				return null; // ("No existen productos")
			} else {
				return listaMovis2;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movis2");
		}
	}


	
	public String deleteClase(Movi2 m){
		try{
			movis2EJBBean.removeMovi2(m.getId());
			return null; 
		}catch(Exception e){
			return null;
		}
	}
	

}
