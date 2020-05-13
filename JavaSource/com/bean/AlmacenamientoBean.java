package com.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.IEntidadLocRemote;
import com.beans.IAlmacenamientosRemote;
import com.entities.EntidadLoc;
import com.entities.Almacenamiento;
import com.exception.ServiciosException;

@ManagedBean(name="almacenamientoB")
@SessionScoped
public class AlmacenamientoBean {
	
	@EJB
	private IAlmacenamientosRemote almacenamientosEJBBean;
	@EJB
	private IEntidadLocRemote entidadesLocEJBBean;
	
	private Long id;
	private int volumen;
	private String nombre;
	private double costoop;
	private double capestiba;
	private double cappeso;
	private EntidadLoc entidadLoc;

	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un metodo
	public AlmacenamientoBean() {
	}

	public localValor[] valorList;

	private List<EntidadLoc> listaEntLoc;
	private String nombreEntidadLoc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getCostoop() {
		return costoop;
	}

	public void setCostoop(double costoop) {
		this.costoop = costoop;
	}

	public double getCapestiba() {
		return capestiba;
	}

	public void setCapestiba(double capestiba) {
		this.capestiba = capestiba;
	}

	public double getCappeso() {
		return cappeso;
	}

	public void setCappeso(double cappeso) {
		this.cappeso = cappeso;
	}

	public EntidadLoc getEntidadLoc() {
		return entidadLoc;
	}

	public void setEntidadLoc(EntidadLoc entidadLoc) {
		this.entidadLoc = entidadLoc;
	}

	public String getNombreEntidadLoc() {
		return nombreEntidadLoc;
	}

	public void setNombreEntidadLoc(String nombreEntidadLoc) {
		this.nombreEntidadLoc = nombreEntidadLoc;
	}


	// para el menu one de entidadesLoc
	
	public List<EntidadLoc> getListaEntLoc() {
		return listaEntLoc;
	}

	public void setListaEntLoc(List<EntidadLoc> listaEntLoc) {
		this.listaEntLoc = listaEntLoc;
	}
	

	
/*	// este lo agrego para el select del one menu
	public tipoLoc[] getTiposDeLocal() {
		return tipoLoc.values();
	}
*/	

	public String getAll() throws ServiciosException{
		try{
			List<Almacenamiento> listaAlmacenamientos = almacenamientosEJBBean.getAllAlmacenamientos(); 
			if ( listaAlmacenamientos.isEmpty()) {
				return null; // ("No existen almacenamientos")
			} else {
				return "mostrarListaAlmacenamientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de almacenamientos");
		}
	}


	/*	
	public String getAlmacenamientosByCodigo() throws ServiciosException{
		try{
			List<Almacenamiento> listaAlmacenamientos = almacenamientosEJBBean.getAlmacenamientosByCodigo(codigo); 
			if ( listaAlmacenamientos.isEmpty()) {
				return null; // ("No existen almacenamientos con nombre " + nombre")
			} else {
				return "mostrarListaAlmacenamientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener almacenamiento con codigo " + String.valueOf(codigo));
		}
	}
*/
	public String getAlmacenamientosByNombre(String nombre) throws ServiciosException{
		try{
			List<Almacenamiento> listaAlmacenamientos = almacenamientosEJBBean.getAlmacenamientosByNombre(nombre); 
			if ( listaAlmacenamientos.isEmpty()) {
				return null; // ("No existen almacenamientos con nombre " + nombre")
			} else {
				return "mostrarListaAlmacenamientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener almacenamiento con nombre " + nombre);
		}
	}

	public String getAlmacenamientosById(Long id) throws ServiciosException{
		try{
			Almacenamiento almacenamiento = almacenamientosEJBBean.getAlmacenamiento(id); 
			if ( almacenamiento == null ) {
				return null; // (""No existe almacenamiento con id " + id.toString()")
			} else {
				return "mostrarListaAlmacenamientos";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener almacenamientos con id " + id.toString());
		}
	}
	
	public String add(int volumen, String nombre, double costoop, double capestiba, double cappeso, String nombreEntidadLoc){
		try{
			System.out.println("addAlmacenamiento-nombre " + nombre);
			Almacenamiento almacenamiento = new Almacenamiento(volumen, nombre, costoop, capestiba, cappeso, entidadesLocEJBBean.getEntidadesLocByNombre(nombreEntidadLoc).get(0));
			almacenamientosEJBBean.addAlmacenamiento(almacenamiento);
			return "mostrarAlmacenamiento";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, int volumen, String nombre, double costoop, double capestiba, double cappeso, Long idEntidadLoc){
		try{
            System.out.println("updateAlmacenamiento-nombre " + nombre);
            Almacenamiento almacenamiento = almacenamientosEJBBean.getAlmacenamiento(id);
            almacenamiento.setVolumen(volumen);
            almacenamiento.setNombre(nombre);
            almacenamiento.setCostoop(costoop);
            almacenamiento.setCapestiba(capestiba);
            almacenamiento.setCappeso(cappeso);
            almacenamiento.setEntidadLoc(entidadesLocEJBBean.getEntidadLoc(idEntidadLoc));
			almacenamientosEJBBean.updateAlmacenamiento(almacenamiento);
			return "mostrarAlmacenamiento";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			almacenamientosEJBBean.removeAlmacenamiento(id);
			return null; /// ojo esto esta mal debe ir a pagina del menu
		}catch(Exception e){
			return null;
		}
	}

	@PostConstruct
	public void init() {
		try {
			listaEntLoc = entidadesLocEJBBean.getAllEntidadesLoc();
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

	

	
	// A AGREGAR	
		

	
	public localValor[] getValoresList() throws ServiciosException {
		List<Almacenamiento> almacenamientos = almacenamientosEJBBean.getAllAlmacenamientos();
		 
		 int i = 0;
		 if (almacenamientos.size()>0) {
			 //Si hay almacenamientos, recorro la lista y la cargo
			 valorList = new localValor[almacenamientos.size()];
			 
			for (Almacenamiento a : almacenamientos) {
					 valorList[i] = new localValor(a.getId(), a.getNombre());
					 System.out.println(a.getId() + " " + a.getNombre());
					 i=i+1;
			}	 
		 }else{
			valorList[0] = new localValor(0, "Sin Datos");
		 	System.out.println("Combo Sin Datos");
		 }
	 	 return valorList;
   }
	

	
	
	 public static class localValor{
			private Long valorID;
			private int volumen;
			private String valorNombre;
			private double costoop;
			private double capestiba;
			private double cappeso;
			private EntidadLoc entidadLoc;

		 
		 public localValor(long valorValue, String valorLabel){
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
}

