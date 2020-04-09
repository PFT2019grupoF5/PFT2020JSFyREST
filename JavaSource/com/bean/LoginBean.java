package com.bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;






@ManagedBean(name="login")
@SessionScoped
public class LoginBean {


	private String nomAcceso;
	private String contrasena;

	
	public String getNomAcceso() {
		return nomAcceso;
	}

	public void setNomAcceso(String nomAcceso) {
		this.nomAcceso = nomAcceso;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	
	public String autenticar() {
		//Aca va el llamado a buscar el usuario en la DB
		

		return "home";
	}

}
