package com.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.beans.IUsuariosRemote;
import com.entities.Usuario;
import com.exception.ServiciosException;

@Stateless
@Path("/usuarios")
public class UsuariosRest {

	@EJB
	private IUsuariosRemote usuariosBeans;
	
	@Path("/check/{user,pass}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkLogin(@PathParam("user") String user, @PathParam("pass") String pass) throws ServiciosException {
		try{
			System.out.println("Usuario recibido"+user);
			System.out.println("pass recibida"+pass);
			//Método que comprueba la existencia del usuario
			Boolean usuario = usuariosBeans.checkUser(user,pass);
			return usuario;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar usuario");
		}
    }
	
	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getAllUsuarios() throws ServiciosException {
		try{
			List<Usuario> listaUsuarios = usuariosBeans.getAllUsuarios(); 
			return listaUsuarios;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de usuarios");
		}
    }

	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdUsuario-id " + id.toString() ); 
			Usuario usuario = usuariosBeans.getUsuario(id);
			return usuario;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener usuario con id " + id.toString());
		}
    }
	
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario addUsuario(Usuario usuario) throws ServiciosException{
        try{
            System.out.println("addUsuario-nombre " + usuario.getNombre() );    
            usuariosBeans.addUsuario(usuario);
			return usuario;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar usuario");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
      public Usuario updateUsuario(@PathParam("id") Long id, Usuario usuario) throws ServiciosException{
        try{
            System.out.println("updateUsuario-nombre " + usuario.getNombre());
            usuario.setId(id);
            usuariosBeans.updateUsuario(usuario);
            return usuario;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar usuario");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario deleteUsuario(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteUsuario-id " + id.toString());
			Usuario usuario = usuariosBeans.getUsuario(id);
			usuariosBeans.removeUsuario(id);
			return usuario;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar usuario");
		}
    }

    
	@GET
    @Path("/getValidoLogin/{nomAcceso, pass}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getUsuario(@PathParam("nomAcceso") String nomAcceso, @PathParam("pass") String pass) throws ServiciosException {
		try{
			System.out.println("getValidoLogin-nomAcceso-pass " + nomAcceso + pass); 
			Usuario usuario= usuariosBeans.getUnUsuarioBynomAcceso(nomAcceso); 
			
			if (usuario != null ) {   //existe el usuario
				if (nomAcceso.equals(usuario.getNomAcceso()) && pass.equals(usuario.getContrasena())) {
					return true; //existe el usuario, y el nomAcceso y contraseña concuerdan
				}else	{	
					return false; //usuario y contraseña no concuerdan, por lo que se devuelve false
				}
		    }else {
		    	return false; // NO existe el usuario
		    }			
			
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener usuario con nomAcceso " + nomAcceso );
		}
    }

}
	
