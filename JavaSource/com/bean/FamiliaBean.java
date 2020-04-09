package com.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;

import com.bean.UsuarioBean.usuarioValor;
import com.beans.IFamiliasRemote;
import com.entities.Familia;
import com.entities.Perfil;
import com.entities.Usuario;
import com.exception.ServiciosException;

@ManagedBean(name="familiaB")
@SessionScoped
public class FamiliaBean {

	@EJB
	private IFamiliasRemote familiasEJBBean;
	
	private Long id;
	private String nombre;
	private String descrip;
	private String incompat;
	
	//20200121 Debe tener constructor x defecto cuando uso este tipo de dato con json de entrada en un método
	public FamiliaBean() {
	}
	
	public familiaValor[] valorList;

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

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getIncompat() {
		return incompat;
	}

	public void setIncompat(String incompat) {
		this.incompat = incompat;
	}

	
	public String getAll() throws ServiciosException{
		try{
			List<Familia> listaFamilias = familiasEJBBean.getAllFamilias(); 
			if ( listaFamilias.isEmpty()) {
				return null; // ("No existen familias")
			} else {
				return "mostrarListaFamilias";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de familias");
		}
	}

	public String getFamiliasByNombre(String nombre) throws ServiciosException{
		try{
			List<Familia> listaFamilias = familiasEJBBean.getFamiliasByNombre(nombre); 
			if ( listaFamilias.isEmpty()) {
				return null; // ("No existen familias con nombre " + nombre")
			} else {
				return "mostrarListaFamilias";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener familia con nombre " + nombre);
		}
	}

	public String getFamiliasById(Long id) throws ServiciosException{
		try{
			Familia familia = familiasEJBBean.getFamilia(id); 
			if ( familia == null ) {
				return null; // (""No existe familia con id " + id.toString()")
			} else {
				return "mostrarListaFamilias";
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener familias con id " + id.toString());
		}
	}
	
	public String add(String nombre, String descrip, String incompat){
		try{
			System.out.println("addFamilia-nombre " + nombre);
			Familia familia = new Familia(nombre, descrip, incompat);
			familiasEJBBean.addFamilia(familia);
			return "mostrarFamilia";
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String nombre, String descrip, String incompat){
		try{
            System.out.println("updateFamilia-nombre " + nombre);    
            Familia familia = familiasEJBBean.getFamilia(id);
            familia.setNombre(nombre);
			familia.setDescrip(descrip);
			familia.setIncompat(incompat);
			familiasEJBBean.updateFamilia(familia);
			return "mostrarFamilia";
		}catch(Exception e){
			return null;
		}
	}
	
	public String delete(Long id){
		try{
			familiasEJBBean.removeFamilia(id);
			return "mostrarFamilia"; /// ojo esto esta mal debe ir a otra pagina del menu, no puede mostrar la familia que borro
		}catch(Exception e){
			return null;
		}
	}

	
	
	
	
	//A AGREGAR
	
		public familiaValor[] getValoresList() throws ServiciosException {
			List<Familia> familias = familiasEJBBean.getAllFamilias();
			 
			 int i = 0;
			 if (familias.size()>0) {
				 //Si hay locales, recorro la lista y la cargo
				 valorList = new familiaValor[familias.size()];
				 
				for (Familia u : familias) {
						 valorList[i] = new familiaValor(u.getId(), u.getNombre());
						 System.out.println(u.getId() + " " + u.getNombre());
						 i=i+1;
				}	 
			 }else{
				valorList[0] = new familiaValor(0, "Sin Datos");
			 	System.out.println("Combo Sin Datos");
			 }
		 	 return valorList;
	   }
		

		 public static class familiaValor{
				private Long valorID;
				private String  valorNombre;
				private String  valordescrip;
				private String  valorincompat;
			 
			 
			 public familiaValor(long valorValue, String valorLabel){
				 this.valorID = valorValue;
				 this.valorNombre= valorLabel;
			 }
			 public long getValorValue(){
				 return valorID;
			 }
			 public String getValorLabel(){
				 return valorNombre;
			 }

		 }
		
	
}
