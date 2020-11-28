package com.bean;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import com.beans.IProductosRemote;
import com.beans.IFamiliasRemote;
import com.beans.IUsuariosRemote;
import com.entities.Familia;
import com.entities.Usuario;
import com.entities.Producto;
import com.enumerated.Segmentacion;
import com.exception.ServiciosException;

import org.primefaces.context.RequestContext;


@ManagedBean(name="productoB")
@SessionScoped
public class ProductoBean {
	
	@EJB
	private IProductosRemote productosEJBBean;
	@EJB
	private IFamiliasRemote familiasEJBBean;
	@EJB
	private IUsuariosRemote usuariosEJBBean;
	
	private Long id;
	private String nombre;
	private String lote;
	private double precio;
	private String felab;
	private String fven;
	private double peso;
	private double volumen;
	private int estiba;
	private double stkMin;
	private double stkTotal;
	private Segmentacion segmentac;
	private Usuario usuario;
	private Familia familia;
	
	public ProductoBean() {
	}

	//Para buscar Familia y Usuario en el add
	private long idUsuario;
	private long idFamilia;
	
	public productoValor[] valorList;

	private List<Producto> listaProducto;
	private List<Familia> listaFamilia;
	private List<Usuario> listaUsuario;
	
// Getters y Setters de atributos de la entidad
	
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

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(long idFamilia) {
		this.idFamilia = idFamilia;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getFelab() {
		return felab;
	}

	public void setFelab(String felab) {
		this.felab = felab;
	}

	public String getFven() {
		return fven;
	}

	public void setFven(String fven) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

// fin getters y seters de atributos de la entidad	
	
	
	
	public String getAll() throws ServiciosException{
		String paginaDeRetorno = "mostrarListaProductos";
		try{
			List<Producto> listaProductos = productosEJBBean.getAllProductos(); 
			if ( listaProductos.isEmpty()) {
				return null; // ("No existen productos")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de productos");
		}
	}


	public String getProductosByNombre(String nombre) throws ServiciosException{
		String paginaDeRetorno = "mostrarListaProductos";
		try{
			List<Producto> listaProductos = productosEJBBean.getProductosByNombre(nombre); 
			if ( listaProductos.isEmpty()) {
				return null; // ("No existen productos con nombre " + nombre")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener almacenamiento con nombre " + nombre);
		}
	}

	public String getProductosById(Long id) throws ServiciosException{
		String paginaDeRetorno = "mostrarListaProductos";
		try{
			Producto producto = productosEJBBean.getProducto(id); 
			if ( producto == null ) {
				return null; // (""No existe producto con id " + id.toString()")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener productos con id " + id.toString());
		}
}
	
	public String add(String nombre, String lote, double precio, String felab, String fven, double peso, double volumen, int estiba, double stkMin, double stkTotal, Segmentacion segmentac, long idUsuario, long idFamilia) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto add ok: ", "Producto agregado correctamente");
		String paginaDeRetorno = "mostrarProducto";
		try{
			if(nombre.isEmpty() || lote.isEmpty() || felab.isEmpty() || fven.isEmpty() ) {
				// los demas valores se controlan a momento de data entry
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "Es necesario ingresar todos los datos requeridos");
			}else if(nombre.length()>50) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "Los datos ingresados, superan el largo permitido. Por favor reise sus datos.");
			}else if(lote.length()>10){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "El lote debe ser de largo menor o igual a 10 caracteres");
			}else if(peso<=0){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "El peso no debe ser menor o igual a cero");
			}else if(volumen<=0){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "El volumen no debe ser menor o igual a cero");
			}else if(stkTotal<0){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "El stock Total no debe ser menor que cero");
			}else if(stkMin<0){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "El stock Minimo no debe ser menor que cero");
			}else if(getProductosByNombre(nombre)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "Producto ya existente, por favor revise sus datos.");
			}else {
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        			Date Dfelab = sdf.parse(felab);
        			Date Dfven = sdf.parse(fven);
        			if(Dfelab.compareTo(Dfven)>0){
        				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "La fecha de Fabricacion no puede ser posterior a la de Vencimiento");
                   	}
        			else { 
        			System.out.println("addProducto-nombre " + nombre);		
        			System.out.println("addProducto-nombre " + nombre + " " + lote + " " +  precio + " " + felab + " " + fven + " " + peso + " " + volumen + " " + estiba + " " + stkMin + " " + stkTotal + " " + segmentac + " " + idUsuario  + " " + idFamilia);			
        			Producto producto = new Producto(nombre, lote, precio, Dfelab, Dfven, peso, volumen, estiba, stkMin, stkTotal, segmentac, usuariosEJBBean.getUsuario(idUsuario) , familiasEJBBean.getFamilia(idFamilia));
        			productosEJBBean.addProducto(producto);
        			}
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
        	return null;
        }
		
	}

	public String update(Long id, String nombre, String lote, double precio, String felab, String fven, double peso, double volumen, int estiba, double stkMin, double stkTotal, Segmentacion segmentac, Long idUsuario, Long idFamilia) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto update ok: ",	"Producto modificado correctamente");
		String paginaDeRetorno = "mostrarProducto";
		try {
			if (getProductosById(id) == null) {
				// Se controla que el producto exista para poder modificarlo.
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "Producto no existe en la BD");
			} else if (nombre.isEmpty() || lote.isEmpty() || felab.isEmpty() || fven.isEmpty()) {
				// los demas valores se controlan a momento de data entry
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "Es necesario ingresar todos los datos requeridos");
			} else if (lote.length() > 10) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "El lote debe ser de largo menor o igual a 10 caracteres");
			} else if (peso <= 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "El peso no debe ser menor o igual a cero");
			} else if (volumen <= 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "El volumen no debe ser menor o igual a cero");
			} else if (stkTotal < 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "El stock Total no debe ser menor que cero");
			} else if (stkMin < 0) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "El stock Minimo no debe ser menor que cero");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date Dfelab = sdf.parse(felab);
				Date Dfven = sdf.parse(fven);
				if (Dfelab.compareTo(Dfven) > 0) {
					message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "La fecha de Fabricacion no puede ser posterior a la de Vencimiento");
				} else {
					System.out.println("updateProducto-nombre " + nombre);
					// se traen datos del producto
					Producto producto = productosEJBBean.getProducto(id);
					// Se modifican campos con datos pasados por parametros
					
					// No se permitirá cambiar el Codigo ni la Descripcion del producto
					// producto.setNombre(nombre);
					
					producto.setLote(lote);
					producto.setPrecio(precio);
					producto.setFelab(Dfelab);
					producto.setFven(Dfven);
					producto.setPeso(peso);
					producto.setVolumen(volumen);
					producto.setEstiba(estiba);
					producto.setStkMin(stkMin);
					producto.setStkTotal(stkTotal);
					producto.setSegmentac(segmentac);
					producto.setUsuario(usuariosEJBBean.getUsuario(idUsuario));
					producto.setFamilia(familiasEJBBean.getFamilia(idFamilia));
					productosEJBBean.updateProducto(producto);
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		} catch (Exception e) {
			return null;
		}

	}
	
	public String delete(Long id){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto delete ok: ", "Producto borrado correctamente");
		String paginaDeRetorno = "mostrarProducto"; // chequear si es ok la pagina de retorno!!! o debe volver a menu principal index
		try{
			if(getProductosById(id)==null) {
				// Se controla que el producto exista para poder borrarlo.
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto update Error: ", "Producto no existe en la BD");
			}
			//Producto no debe estar ingresado en Pedidos o en Productos_Almacenamientos ni se haya registrado una Perdida )
			else if( getProductosById(id)==null) { 
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Producto add Error: ", "Producto no sepuede Eliminar porque existe en X, eliminelo previamente de X para luego Eliminar el mismo");
				// que es X ????
			}else {
					productosEJBBean.removeProducto(id);
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno; /// ojo esto esta mal debe ir a otra pagina del menu, no puede mostrar el producto que borro
		}catch(Exception e){
			return null;
		}
	}

	

  	@PostConstruct
 
	public void init() {
		try {
			listaProducto = productosEJBBean.getAllProductos();
			listaFamilia  = familiasEJBBean.getAllFamilias();
			listaUsuario = usuariosEJBBean.getAllUsuarios();
		} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }


  	public productoValor[] getValoresList() throws ServiciosException {
 
		List<Producto> productos = productosEJBBean.getAllProductos();
		 
		 int i = 0;
		 if (productos.size()>0) {
			 //Si hay productos, recorro la lista y la cargo
			 valorList = new productoValor[productos.size()];
			 
			for (Producto a : productos) {
					 valorList[i] = new productoValor(a.getId(), a.getNombre());
					 System.out.println(a.getId() + " " + a.getNombre());
					 i=i+1;
			}	 
		 }else{
			valorList[0] = new productoValor(0, "Sin Datos");
		 	System.out.println("Combo Sin Datos");
		 }
	 	 return valorList;
   }
	

	
	 public static class productoValor{
			private Long valorID;
			private String valorNombre;
			private String valorLote;
			private double valorPrecio;
			private String valorfelab;
			private String valorfven;
			private double valorpeso;
			private double valorvolumen;
			private int valorestiba;
			private double valorstkMin;
			private double valorstkTotal;
			private Segmentacion valorsegmentac;
			private Usuario valorusuario;
			private Familia valorfamilia;

		 
		 public productoValor(long valorValue, String valorLabel){
			 this.valorID = valorValue;
			 this.valorNombre = valorLabel;
		 }
		 public long getValorValue(){
			 return valorID;
		 }
		 public String getValorLabel(){
			 return valorNombre;
		 }
	 }


	public IProductosRemote getProductosEJBBean() {
		return productosEJBBean;
	}


	public void setProductosEJBBean(IProductosRemote productosEJBBean) {
		this.productosEJBBean = productosEJBBean;
	}
	
	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public List<Familia> getListaFamilia() {
		return listaFamilia;
	}

	public void setListaFamilia(List<Familia> listaFamilia) {
		this.listaFamilia = listaFamilia;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public productoValor[] getValorList() {
		return valorList;
	}

	public void setValorList(productoValor[] valorList) {
		this.valorList = valorList;
	}
	
	// este lo agrego para el select del one menu
	public Segmentacion[] getTiposdeSegmentacion() {
		return Segmentacion.values();
	}
 

	public String deleteClase(Producto p){
		try{
			productosEJBBean.removeProducto(p.getId());
			return null; 
		}catch(Exception e){
			return null;
		}
	}

	
//***********	
	public List<Producto> getAllLista() throws ServiciosException{

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

	
	
	
}

