package com.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.ICiudadesRemote;
import com.entities.Ciudad;
import com.exception.ServiciosException;

@ManagedBean(name="ciudadB")
@SessionScoped
public class CiudadBean {

	@EJB
	private ICiudadesRemote ciudadesEJBBean;
	
	private Long id;
	private String nombre;
	
	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public CiudadBean() {
	}
	
	public ciudadValor[] valorList;

	
	
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


	public String getAll() throws ServiciosException{
		try{
			List<Ciudad> listaCiudades = ciudadesEJBBean.getAllCiudades(); 
			if ( listaCiudades.isEmpty()) {
				return null; // ("No existen ciudades")
			} else {
				return "mostrarListaCiudades";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de ciudades");
		}
	}

	public String getCiudadesByNombre(String nombre) throws ServiciosException{
		try{
			List<Ciudad> listaCiudades = ciudadesEJBBean.getCiudadesByNombre(nombre); 
			if ( listaCiudades.isEmpty()) {
				return null; // ("No existen ciudades con nombre " + nombre")
			} else {
				return "mostrarListaCiudades";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener ciudad con nombre " + nombre );
		}
	}

	public String getCiudadesById(Long id) throws ServiciosException{
		try{
			Ciudad ciudad = ciudadesEJBBean.getCiudad(id); 
			if ( ciudad == null ) {
				return null; // (""No existe ciudad con id " + id.toString()")
			} else {
				return "mostrarListaCiudades";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener ciudades con id " + id.toString() );
		}
	}
	
	public String add(String nombre){
		try{
			System.out.println("addCiudad-nombre " + nombre);
			Ciudad ciudad = new Ciudad(nombre);
			ciudadesEJBBean.addCiudad(ciudad);
			return "mostrarCiudad";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String nombre){
		try{
            System.out.println("updateCiudad-nombre " + nombre);    
            Ciudad ciudad = ciudadesEJBBean.getCiudad(id);
			ciudad.setNombre(nombre);
			ciudadesEJBBean.updateCiudad(ciudad);
			return "mostrarCiudad";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			ciudadesEJBBean.removeCiudad(id);
			return "mostrarCiudad"; /// ojo esto esta mal debe ir a otra pagina del menu, no puede mostrar la ciudad que borro
		}catch(Exception e){
			return null;
		}
	}

	
	
	
	
	// A AGREGAR	

	
	public ciudadValor[] getValoresList() throws ServiciosException {
		List<Ciudad> ciudades = ciudadesEJBBean.getAllCiudades();
		 
		 int i = 0;
		 if (ciudades.size()>0) {
			 //Si hay ciudades, recorro la lista y la cargo
			 valorList = new ciudadValor[ciudades.size()];
			 
			for (Ciudad a : ciudades) {
					 valorList[i] = new ciudadValor(a.getId(), a.getNombre());
					 System.out.println(a.getId() + " " + a.getNombre());
					 i=i+1;
			}	 
		 }else{
			valorList[0] = new ciudadValor(0, "Sin Datos");
		 	System.out.println("Combo Sin Datos");
		 }
	 	 return valorList;
   }
	

	
	
	 public static class ciudadValor{
			private Long valorID;
			private String valorNombre;

		 
		 public ciudadValor(long valorValue, String valorLabel){
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
