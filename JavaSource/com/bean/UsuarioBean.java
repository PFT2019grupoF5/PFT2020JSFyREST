package com.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.PersistenceException;

import com.beans.IPerfilesRemote;
import com.beans.IUsuariosRemote;
import com.entities.Perfil;
import com.enumerated.tipoPerfil;
import com.entities.Usuario;
import com.exception.ServiciosException;

import org.primefaces.context.RequestContext;

@ManagedBean(name="usuarioB")
@SessionScoped

public class UsuarioBean {
	
	@EJB
	private IUsuariosRemote usuariosEJBBean;
	@EJB
	private IPerfilesRemote perfilesEJBBean;
	


	private Long id;
	private String nombre;
	private String apellido;
	private String nomAcceso;
	private String contrasena;
	private String correo;
	private Perfil perfil;
	
	private boolean loged;
	
	public usuarioValor[] valorList;
	
	private List<Perfil> perfiles;
	private String nombrePerfil;


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

	public boolean getLoged() {
		return loged;
	}

	public void setLoged(boolean loged) {
		this.loged = loged;
	}
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getNombrePerfil() {
		return nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}


	// para el menu one de perfiles

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	
	// este lo agrego para el select del one menu
	public tipoPerfil[] getTiposDePerfiles() {
		return tipoPerfil.values();
	}
	

	public String getAll() throws ServiciosException{
		String paginaDeRetorno = "mostrarListaUsuarios";
		try{
			List<Usuario> listaUsuarios = usuariosEJBBean.getAllUsuarios(); 
			if ( listaUsuarios.isEmpty()) {
				return null; // ("No existen usuarios")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de usuarios");
		}
	}

	public String getUsuariosBynomAcceso(String nomAcceso) throws ServiciosException{
		String paginaDeRetorno = "mostrarListaUsuarios";
		try{
			List<Usuario> listaUsuarios = usuariosEJBBean.getUsuariosBynomAcceso(nomAcceso); 
			if ( listaUsuarios.isEmpty()) {
				this.loged=false;
				return null; // ("No existen usuarios con nomAcceso " + nomAcceso")
			} else {
				this.loged=true;
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener usuario con nomAcceso " + nomAcceso);
		}
	}

	public String getUsuariosById(Long id) throws ServiciosException{
		String paginaDeRetorno = "mostrarListaUsuarios";
		try{
			Usuario usuario = usuariosEJBBean.getUsuario(id); 
			
			if ( usuario == null ) {
				return null; // (""No existe usuario con id " + id.toString()")
			} else {
				return paginaDeRetorno;
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener usuarios con id " + id.toString());
		}
	}
	
	public String add(String nombre, String apellido, String nomAcceso, String contrasena, String correo, String nombrePerfil){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario add ok: ", "Usuario agregado correctamente");
		String paginaDeRetorno = "mostrarUsuario";
		try{
			if(nombre.isEmpty() || nombre.length()>50) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "El Nombre debe ser no vacio y menor o igual a 50 caracteres");
			}else if(apellido.isEmpty() || apellido.length()>50) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "El Apellido debe ser no vacio y menor o igual a 50 caracteres");
			}else if(nomAcceso.isEmpty() || nomAcceso.length()>20){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "El nomAcceso debe ser no vacio y menor o igual a 20 caracteres");
			}else if(getUsuariosBynomAcceso(nomAcceso)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "El nomAcceso ya existe en la BD, ingrese otro nomAcceso");
			}else if(contrasena.length()<8 || contrasena.length()>16){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "La contraseña debe ser entre 8 y 16 caracteres");
			}else if(correo.isEmpty() || correo.length()>50){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario add Error: ", "El correo debe ser no vacio y menor o igual a 50 caracteres");
			}else {
				System.out.println("addUsuario-nomAcceso " + nomAcceso);
				Usuario usuario = new Usuario(nombre, apellido, nomAcceso, contrasena, correo, perfilesEJBBean.getPerfilesByNombre(nombrePerfil).get(0));
				usuariosEJBBean.addUsuario(usuario);
				}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
			return null;
		}
	}

	public String update(Long id, String nombre, String apellido, String nomAcceso, String contrasena, String correo, Long idPerfil){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario update ok: ", "Usuario agregado correctamente");
		String paginaDeRetorno = "mostrarUsuario";
		try{
			if(nombre.isEmpty() || nombre.length()>50) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "El Nombre debe ser no vacio y menor o igual a 50 caracteres");
			}else if(apellido.isEmpty() || apellido.length()>50) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "El Apellido debe ser no vacio y menor o igual a 50 caracteres");
			}else if(nomAcceso.isEmpty() || nomAcceso.length()>20){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "El nomAcceso debe ser no vacio y menor o igual a 20 caracteres");
			}else if(getUsuariosBynomAcceso(nomAcceso)!=null){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "El nomAcceso ya existe en la BD, ingrese otro nomAcceso");
			}else if(contrasena.length()<8 || contrasena.length()>16){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "La contraseña debe ser entre 8 y 16 caracteres");
			}else if(correo.isEmpty() || correo.length()>50){
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario update Error: ", "El correo debe ser no vacio y menor o igual a 50 caracteres");
			}else {
	            System.out.println("updateUsuario-nomAcceso " + nomAcceso);
	            Usuario usuario = usuariosEJBBean.getUsuario(id);
	            usuario.setNombre(nombre);
	            usuario.setApellido(apellido);
	            usuario.setNomAcceso(nomAcceso);
	            usuario.setContrasena(contrasena);
	            usuario.setCorreo(correo);
	            usuario.setPerfil(perfilesEJBBean.getPerfil(idPerfil));
				usuariosEJBBean.updateUsuario(usuario);
				}
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
			return null;
		}
		
	}
	
	public String delete(Long id){
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario del ok: ", "Usuario borrado correctamente");
		String paginaDeRetorno = "mostrarUsuario"; // OJO CHEQUEAR ESTO ! SI TIENE DATOSPARA MOSTRAR !
		try{
			usuariosEJBBean.removeUsuario(id);
			FacesContext.getCurrentInstance().addMessage(null, message);
			return paginaDeRetorno;
		}catch(Exception e){
			return null;
		}
	}

	@PostConstruct
	public void init() {
		try{
			perfiles = perfilesEJBBean.getAllPerfiles();
		}catch (ServiciosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	
	
	
// A AGREGAR	

	public Usuario getUnUsuarioBynomAcceso(String nomAcceso) throws ServiciosException{
		try{
			Usuario usuario = usuariosEJBBean.getUnUsuarioBynomAcceso(nomAcceso); 
			if (usuario == null) {
				this.loged=false;
				return null; // ("No existen usuarios con nomAcceso " + nomAcceso")
			} else {
				this.loged=true;
				return usuario;
				
			}
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener usuario con nomAcceso " + nomAcceso);
		}
	}

	
	public void getUsuarioLoged(ActionEvent actionEvent) throws ServiciosException{
		
		//System.out.println(user + " " +pass);
		RequestContext context = RequestContext.getCurrentInstance();
	    FacesMessage mensaje = null;
		
		try{
			Usuario usuario= usuariosEJBBean.getUnUsuarioBynomAcceso(nomAcceso); 
			
			if ( usuario == null ) {
			      loged = false;
			      mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");

//				return null; // (""No existe usuario con nomAcceso seleccionado 

			} else {
				
				
				if (contrasena.equals(usuario.getContrasena()) && (nomAcceso.equals(usuario.getNomAcceso()))) {
					
					loged = true;
					mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", usuario.getNombre() + " " + usuario.getApellido());
					System.out.println("USUARIO CORRECTO");

				} else {
				      loged = false;
				      mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
				      System.out.println("USUARIO INCORRECTO");
				}
					
				FacesContext.getCurrentInstance().addMessage(null, mensaje);
			    context.addCallbackParam("estaLogeado", loged);
			    
			    if (loged) {
			    	
			    	
			    	Perfil perfilLogueado;
			    	//perfilLogueado = 
			    	//String PL;
			    	perfilLogueado = usuario.getPerfil();
			    	tipoPerfil tipoPerfilLogueado;
			    	tipoPerfilLogueado = perfilLogueado.getPerfil();
			    	
			    	
			    	//String pl= perfilLogueado.getPerfil().;
			    	//System.out.print(perfilLogueado.getPerfil().valueOf(nombrePerfil));
			    	
			    	
			    	if (tipoPerfil.ADMINISTRADOR.equals(tipoPerfilLogueado)) {
			    	      context.addCallbackParam("view", "MenuADM.xhtml");
			    	}else
				    	if (tipoPerfil.SUPERVISOR.equals(tipoPerfilLogueado)){
				    	      context.addCallbackParam("view", "MenuSUP.xhtml");
			    	}else
			    		if (tipoPerfil.OPERARIO.equals(tipoPerfilLogueado)){
				    	      context.addCallbackParam("view", "MenuOPE.xhtml");
			    		}
			    	}		
				
					
//					MenuBean menu = new MenuBean(true);
//					return usuario.getPerfil().getPerfil().toString();
//				}else {
//					
//					return null;
			}
			
		}catch(PersistenceException e){
			System.out.println("USUARIO NO EXISTE");

			throw new ServiciosException("No se pudo obtener datos del usuario mediante Nombre de Usuario " + nomAcceso);
		}
	}

	
	
	
	
	
//A AGREGAR
	
	public usuarioValor[] getValoresList() throws ServiciosException {
		List<Usuario> usuarios = usuariosEJBBean.getAllUsuarios();
		 
		 int i = 0;
		 if (usuarios.size()>0) {
			 //Si hay locales, recorro la lista y la cargo
			 valorList = new usuarioValor[usuarios.size()];
			 
			for (Usuario u : usuarios) {
					 valorList[i] = new usuarioValor(u.getId(), u.getNomAcceso());
					 System.out.println(u.getId() + " " + u.getNomAcceso());
					 i=i+1;
			}	 
		 }else{
			valorList[0] = new usuarioValor(0, "Sin Datos");
		 	System.out.println("Combo Sin Datos");
		 }
	 	 return valorList;
   }
	

	 public static class usuarioValor{
		 public long valorID;
		 public String valorNombre;
		 public String valorApellido;
		 public String valorNomAcceso;
		 public String valorContrasena;
		 public String valorCorreo;
		 public Perfil valorPerfil;
		 
		 
		 public usuarioValor(long valorValue, String valorLabel){
			 this.valorID = valorValue;
			 this.valorNomAcceso= valorLabel;
		 }
		 public long getValorValue(){
			 return valorID;
		 }
		 public String getValorLabel(){
			 return valorNomAcceso;
		 }

	 }
	
}
