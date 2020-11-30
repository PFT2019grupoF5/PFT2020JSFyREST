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

import com.beans.IEntidadLocRemote;
import com.entities.EntidadLoc;
import com.exception.ServiciosException;

@Stateless
@Path("/entidadesLoc2")
public class EntidadLocRest2 {

	@EJB
	private IEntidadLocRemote entidadesLocBeans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntidadLoc> getAllEntidadesLoc() throws ServiciosException {
		try{
			List<EntidadLoc> listaEntidadesLoc = entidadesLocBeans.getAllEntidadesLoc(); 
			return listaEntidadesLoc;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de entidadesLoc");
		}
    }

	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EntidadLoc getEntidadLoc(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdEntidadLoc-id " + id.toString() ); 
			EntidadLoc entidadLoc = entidadesLocBeans.getEntidadLoc(id);
			return entidadLoc;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener entidadLoc con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public void addEntidadLoc(EntidadLoc entidadLoc) throws ServiciosException{
        try{
            System.out.println("addEntidadLoc-nombre " + entidadLoc.getNombre() );    
            entidadesLocBeans.addEntidadLoc(entidadLoc);
			return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar entidadLoc");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	// aca siguiendo la logica del teorico deberia decir solo EntidadLoc entidadLoc como parametro
      public void updateEntidadLoc(@PathParam("id") Long id, EntidadLoc entidadLoc) throws ServiciosException{
        try{
            System.out.println("updateEntidadLoc-nombre " + entidadLoc.getNombre());
            entidadLoc.setId(id);
            entidadesLocBeans.updateEntidadLoc(entidadLoc);
            return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar entidadLoc");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteEntidadLoc(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteEntidadLoc-id " + id.toString());
			entidadesLocBeans.removeEntidadLoc(id);
			return;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar entidadLoc");
		}
    }
    
}
