package com.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.beans.ICiudadesRemote;
import com.beans.IEntidadLocRemote;
import com.entities.Ciudad;
import com.entities.EntidadLoc;
import com.enumerated.tipoLoc;
import com.exception.ServiciosException;

@ManagedBean(name="entidadLocB")
@SessionScoped
public class EntidadLocBean {
	
	@EJB
	private IEntidadLocRemote entidadesLocEJBBean;
	@EJB
	private ICiudadesRemote ciudadesEJBBean;
	
	private Long id;
	private int codigo;
	private String nombre;
	private String direccion;
	private tipoLoc tipoloc;
	private Ciudad ciudad;

	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public EntidadLocBean() {
	}

	
	private List<Ciudad> ciudades;
	private String nombreCiudad;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public tipoLoc getTipoloc() {
		return tipoloc;
	}

	public void setTipoloc(tipoLoc tipoloc) {
		this.tipoloc = tipoloc;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}


	// para el menu one de ciudades
	
	public List<Ciudad> getCiudades() {
		return ciudades;
	}

	public void setCiudades(List<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}

	
	// este lo agrego para el select del one menu
	public tipoLoc[] getTiposDeLocal() {
		return tipoLoc.values();
	}
	

	public String getAll() throws ServiciosException{
		try{
			List<EntidadLoc> listaEntidadesLoc = entidadesLocEJBBean.getAllEntidadesLoc(); 
			if ( listaEntidadesLoc.isEmpty()) {
				return null; // ("No existen entidadesLoc")
			} else {
				return "mostrarListaEntidadesLoc";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de entidadesLoc");
		}
	}

	public String getEntidadesLocByCodigo(int codigo) throws ServiciosException{
		try{
			List<EntidadLoc> listaEntidadesLoc = entidadesLocEJBBean.getEntidadesLocByCodigo(codigo); 
			if ( listaEntidadesLoc.isEmpty()) {
				return null; // ("No existen entidadesLoc con nombre " + nombre")
			} else {
				return "mostrarListaEntidadesLoc";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener localEnt con codigo " + String.valueOf(codigo));
		}
	}

	public String getEntidadesLocByNombre(String nombre) throws ServiciosException{
		try{
			List<EntidadLoc> listaEntidadesLoc = entidadesLocEJBBean.getEntidadesLocByNombre(nombre); 
			if ( listaEntidadesLoc.isEmpty()) {
				return null; // ("No existen entidadesLoc con nombre " + nombre")
			} else {
				return "mostrarListaEntidadesLoc";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener localEnt con nombre " + nombre);
		}
	}

	public String getEntidadesLocById(Long id) throws ServiciosException{
		try{
			EntidadLoc localEnt = entidadesLocEJBBean.getEntidadLoc(id); 
			if ( localEnt == null ) {
				return null; // (""No existe localEnt con id " + id.toString()")
			} else {
				return "mostrarListaEntidadesLoc";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener entidadesLoc con id " + id.toString());
		}
	}
	
	public String add(int codigo, String nombre, String direccion, tipoLoc tipoloc, String nombreCiudad){
		try{
			System.out.println("addEntidadLoc-codigo " + codigo);
			EntidadLoc localEnt = new EntidadLoc(codigo, nombre, direccion, tipoloc, ciudadesEJBBean.getCiudadesByNombre(nombreCiudad).get(0));
			entidadesLocEJBBean.addEntidadLoc(localEnt);
			return "mostrarEntidadLoc";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, int codigo, String nombre, String direccion, tipoLoc tipoloc, Long idCiudad){
		try{
            System.out.println("updateEntidadLoc-nombre " + nombre);
            EntidadLoc localEnt = entidadesLocEJBBean.getEntidadLoc(id);
            localEnt.setCodigo(codigo);
            localEnt.setNombre(nombre);
            localEnt.setDireccion(direccion);
            localEnt.setTipoloc(tipoloc);
            localEnt.setCiudad(ciudadesEJBBean.getCiudad(idCiudad));
			entidadesLocEJBBean.updateEntidadLoc(localEnt);
			return "mostrarEntidadLoc";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			entidadesLocEJBBean.removeEntidadLoc(id);
			return null; /// ojo esto esta mal debe ir a pagina del menu
		}catch(Exception e){
			return null;
		}
	}

	@PostConstruct
	public void init() {
		try {
				ciudades = ciudadesEJBBean.getAllCiudades();
			} catch (ServiciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

}
