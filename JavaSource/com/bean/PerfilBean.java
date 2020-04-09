package com.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import com.beans.IPerfilesRemote;
import com.entities.Perfil;
import com.enumerated.tipoPerfil;
import com.exception.ServiciosException;

@ManagedBean(name="perfilB")
@SessionScoped
public class PerfilBean {

	@EJB
	private IPerfilesRemote perfilesEJBBean;
	
	private Long id;
	private tipoPerfil tipoperfil;
	
	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public PerfilBean() {
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public tipoPerfil getTipoPerfil() {
		return tipoperfil;
	}

	public void setTipoPerfil(tipoPerfil tipoperfil) {
		this.tipoperfil = tipoperfil;
	}

	// este lo agrego para el select del one menu
	public tipoPerfil[] getTiposDePerfil() {
		return tipoPerfil.values();
	}
	
	public String getAll() throws ServiciosException{
		try{
			List<Perfil> listaPerfiles = perfilesEJBBean.getAllPerfiles(); 
			if ( listaPerfiles.isEmpty()) {
				return null; // ("No existen perfiles")
			} else {
				return "mostrarListaPerfiles";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de perfiles");
		}
	}

	public String getPerfilesById(Long id) throws ServiciosException{
		try{
			Perfil perfil = perfilesEJBBean.getPerfil(id); 
			if ( perfil == null ) {
				return null; // (""No existe perfil con id " + id.toString()")
			} else {
				return "mostrarListaPerfiles";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener perfiles con id " + id.toString() );
		}
	}
	
	public String add(tipoPerfil tipoperfil){
		try{
			System.out.println("addPerfil-tipoPerfil " + tipoperfil);
			Perfil perfil = new Perfil(tipoperfil);
			perfilesEJBBean.addPerfil(perfil);
			return "mostrarPerfil";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, tipoPerfil tipoperfil){
		try{
            System.out.println("updatePerfil-tipoPerfil " + tipoperfil);    
            Perfil perfil = perfilesEJBBean.getPerfil(id);
			perfil.setPerfil(tipoperfil);;
			perfilesEJBBean.updatePerfil(perfil);
			return "mostrarPerfil";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			perfilesEJBBean.removePerfil(id);
			return "mostrarPerfil"; /// ojo esto esta mal debe ir a otra pagina del menu, no puede mostrar la perfil que borro
		}catch(Exception e){
			return null;
		}
	}
	
	
}
