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

import com.beans.IPerfilesRemote;
import com.entities.Perfil;
import com.exception.ServiciosException;

@Stateless
@Path("/perfiles2")
public class PerfilesRest2 {

	@EJB
	private IPerfilesRemote perfilesBeans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Perfil> getAllPerfiles() throws ServiciosException {
		try{
			List<Perfil> listaPerfiles = perfilesBeans.getAllPerfiles(); 
			return listaPerfiles;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de perfiles");
		}
    }
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Perfil getPerfil(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdPerfil-id " + id.toString() ); 
			Perfil perfil = perfilesBeans.getPerfil(id);
			return perfil;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener perfil con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public void addPerfil(Perfil perfil) throws ServiciosException{
        try{
            System.out.println("addPerfil-TipoPerfil " + perfil.getPerfil().toString() );    
            perfilesBeans.addPerfil(perfil);
			return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar perfil");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	// aca siguiendo la logica del teorico deberia decir solo Perfil perfil como parametro
      public void updatePerfil(@PathParam("id") Long id, Perfil perfil) throws ServiciosException{
        try{
            System.out.println("addPerfil-TipoPerfil " + perfil.getPerfil().toString() );    
            perfil.setId(id);
            perfilesBeans.updatePerfil(perfil);
            return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar perfil");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePerfil(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deletePerfil-id " + id.toString());
			perfilesBeans.removePerfil(id);
			return;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar perfil");
		}
    }
    
}
