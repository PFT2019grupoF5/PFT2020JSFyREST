
package com.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name="menuB")
@SessionScoped
public class MenuBean {
	private MenuModel menuButton = new DefaultMenuModel();

	public MenuBean(boolean logueado){


		// Create menuitems required
		
		if (logueado) {
			System.out.println("MENU BEAN Ingresó");
			
			DefaultMenuItem menuCiudades = new DefaultMenuItem("Ciudades");
			menuCiudades.setUrl("/faces/altaCiudad.xhtml");
			this.menuButton.addElement(menuCiudades);
	
			DefaultMenuItem menuFamilias = new DefaultMenuItem("Familias");
			menuFamilias.setUrl("/faces/altaFamilia.xhtml");
			this.menuButton.addElement(menuFamilias);
	
			DefaultMenuItem menuLocales = new DefaultMenuItem("Locales");
			menuLocales.setUrl("/faces/altaLocal.xhtml");
			this.menuButton.addElement(menuLocales);
			
			DefaultMenuItem menuAlmacenamientos = new DefaultMenuItem("Almacenamientos");
			menuAlmacenamientos.setUrl("/faces/altaAlmacenamiento.xhtml");
			this.menuButton.addElement(menuAlmacenamientos);
			
			DefaultMenuItem menuMovimiento = new DefaultMenuItem("Movimientos");
			menuMovimiento.setUrl("/faces/altaMovimiento.xhtml");
			this.menuButton.addElement(menuMovimiento);
			
	
			DefaultMenuItem menuReportes = new DefaultMenuItem("Reportes");
			menuReportes.setUrl("/faces/altaMovimiento.xhtml");
			this.menuButton.addElement(menuReportes);
			}
	}

	public MenuModel getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(MenuModel menuButton) {
		this.menuButton = menuButton;
	}

	public String ajaxAction(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajax Update"));
		return "";
	}

	public String nonAjaxAction(){
		return "";
	}
}