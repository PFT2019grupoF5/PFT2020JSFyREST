 package com.ws;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import com.beans.IProductosRemote;
import com.entities.Producto;
import com.enumerated.Segmentacion;
import com.exception.ServiciosException;

@Stateless
public class ProductoLogicaNegocio {
	
	@EJB
	private IProductosRemote productosEJBBean;

	
	public List<Producto> getAll() throws ServiciosException{
		try{
			List<Producto> listaProductos = productosEJBBean.getAllProductos(); 
			if ( listaProductos.isEmpty()) {
				return null; // ("No existen productos")
			} else {
				return listaProductos;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de productos");
		}
	}


	public List<Producto> getProductosByNombre(String nombre) throws ServiciosException{
		try{
			List<Producto> listaProductos = productosEJBBean.getProductosByNombre(nombre); 
			if ( listaProductos.isEmpty()) {
				return null; // ("No existen productos con nombre " + nombre")
			} else {
				return listaProductos;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener almacenamiento con nombre " + nombre);
		}
	}

	public Producto getProducto(Long id) throws ServiciosException{
		try{
			Producto producto = productosEJBBean.getProducto(id); 
			if ( producto == null ) {
				return null; // (""No existe producto con id " + id.toString()")
			} else {
				return producto;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener productos con id " + id.toString());
		}
}
	
	public String add(Producto producto) throws ServiciosException{
		
		String nombre = producto.getNombre();
		String lote = producto.getLote();
		double precio = producto.getPrecio();
		Date felab = producto.getFelab();
		Date fven = producto.getFven();
		double peso = producto.getPeso();
		double volumen = producto.getVolumen();
		int estiba = producto.getEstiba();
		double stkMin = producto.getStkMin();
		double stkTotal = producto.getStkTotal();
		Segmentacion segmentac = producto.getSegmentac();
		long idUsuario = producto.getUsuario().getId();
		long idFamilia = producto.getFamilia().getId();

		String message = "Producto add ok: Producto agregado correctamente";
		try{
			if(nombre.isEmpty() || lote.isEmpty() || felab==null || fven==null ) {
				// los demas valores se controlan a momento de data entry
				message = "Producto add Error: Es necesario ingresar todos los datos requeridos";
			}else if(nombre.length()>50) {
				message = "Producto add Error: Los datos ingresados, superan el largo permitido. Por favor revise sus datos.";
			}else if(lote.length()>10){
				message = "Producto add Error: El lote debe ser de largo menor o igual a 10 caracteres";
			}else if(peso<=0){
				message = "Producto add Error: El peso no debe ser menor o igual a cero";
			}else if(volumen<=0){
				message = "Producto add Error: El volumen no debe ser menor o igual a cero";
			}else if(stkTotal<0){
				message = "Producto add Error: El stock Total no debe ser menor que cero";
			}else if(stkMin<0){
				message = "Producto add Error: El stock Minimo no debe ser menor que cero";
			}else if(getProductosByNombre(nombre)!=null){
				message = "Producto add Error: Producto ya existente, por favor revise sus datos.";
			}else {
        			if(felab.compareTo(fven)>0){
        				message = "Producto add Error: La fecha de Fabricacion no puede ser posterior a la de Vencimiento";
                   	}
        			else { 
        			System.out.println("addProducto-nombre " + nombre + " " + lote + " " +  precio + " " + felab + " " + fven + " " + peso + " " + volumen + " " + estiba + " " + stkMin + " " + stkTotal + " " + segmentac + " " + idUsuario  + " " + idFamilia);			
        			productosEJBBean.addProducto(producto);
        			}
			}
			return message;
		}catch(Exception e){
        	return null;
        }
		
	}

	public String update(Producto producto) throws ServiciosException{

		Long id = producto.getId();
		String nombre = producto.getNombre();
		String lote = producto.getLote();
		double precio = producto.getPrecio();
		Date felab = producto.getFelab();
		Date fven = producto.getFven();
		double peso = producto.getPeso();
		double volumen = producto.getVolumen();
		int estiba = producto.getEstiba();
		double stkMin = producto.getStkMin();
		double stkTotal = producto.getStkTotal();
		Segmentacion segmentac = producto.getSegmentac();
		long idUsuario = producto.getUsuario().getId();
		long idFamilia = producto.getFamilia().getId();

		String message = "Producto update ok: Producto modificado correctamente";
		try {
			if (getProducto(id) == null) {
				// Se controla que el producto exista para poder modificarlo.
				message = "Producto update Error: Producto no existe en la BD";
			} else if (nombre.isEmpty() || lote.isEmpty() || felab==null || fven==null) {
				// los demas valores se controlan a momento de data entry
				message = "Producto update Error: Es necesario ingresar todos los datos requeridos";
			}else if(nombre.length()>50) {
				message = "Producto add Error: Los datos ingresados, superan el largo permitido. Por favor reise sus datos.";
			}else if(lote.length()>10){
				message = "Producto add Error: El lote debe ser de largo menor o igual a 10 caracteres";
			}else if(peso<=0){
				message = "Producto add Error: El peso no debe ser menor o igual a cero";
			}else if(volumen<=0){
				message = "Producto add Error: El volumen no debe ser menor o igual a cero";
			}else if(stkTotal<0){
				message = "Producto add Error: El stock Total no debe ser menor que cero";
			}else if(stkMin<0){
				message = "Producto add Error: El stock Minimo no debe ser menor que cero";
			} else {
				if (felab.compareTo(fven) > 0) {
					message = "Producto add Error: La fecha de Fabricacion no puede ser posterior a la de Vencimiento";
				} else {
        			System.out.println("updateProducto-nombre " + nombre + " " + lote + " " +  precio + " " + felab + " " + fven + " " + peso + " " + volumen + " " + estiba + " " + stkMin + " " + stkTotal + " " + segmentac + " " + idUsuario  + " " + idFamilia);			
					// se traen datos del producto
					Producto productoAmodificar = productosEJBBean.getProducto(id);
					// Se modifican campos con datos pasados por parametros
					
					// No se permitirá cambiar el Codigo ni la Descripcion del producto
					// productoAmodificar.setNombre(nombre);
					
					productoAmodificar.setLote(lote);
					productoAmodificar.setPrecio(precio);
					productoAmodificar.setFelab(felab);
					productoAmodificar.setFven(fven);
					productoAmodificar.setPeso(peso);
					productoAmodificar.setVolumen(volumen);
					productoAmodificar.setEstiba(estiba);
					productoAmodificar.setStkMin(stkMin);
					productoAmodificar.setStkTotal(stkTotal);
					productoAmodificar.setSegmentac(segmentac);
					productoAmodificar.setUsuario(producto.getUsuario());
					productoAmodificar.setFamilia(producto.getFamilia());
					productosEJBBean.updateProducto(productoAmodificar);
				}
			}
			return message;
		} catch (Exception e) {
			return null;
		}

	}
	
	public String delete(Long id){
		String message = "Producto delete ok: Producto borrado correctamente";
		try{
			if(getProducto(id)==null) {
				// Se controla que el producto exista para poder borrarlo.
				message = "Producto delete Error: Producto no existe en la BD";
			}
			//Producto no debe estar ingresado en Pedidos o en Productos_Almacenamientos ni se haya registrado una Perdida )
			else if( getProducto(id)==null) // hay que crear una funcion de validacion aca
				{ 
				message = "Producto delete Error: Producto no sepuede Eliminar porque existe en X, eliminelo previamente de X para luego Eliminar el mismo";
				// que es X ????
			}else {
					productosEJBBean.removeProducto(id);
			}
			return message;
		}catch(Exception e){
			return null;
		}
	}

}

