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

import com.beans.IFamiliasRemote;
import com.entities.Familia;
import com.exception.ServiciosException;

@Stateless
@Path("/familias2")
public class FamiliasRest2 {

	@EJB
	private IFamiliasRemote familiasBeans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Familia> getAllFamilias() throws ServiciosException {
		try{
			List<Familia> listaFamilias = familiasBeans.getAllFamilias(); 
			return listaFamilias;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de familias");
		}
    }

	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Familia getFamilia(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdFamilia-id " + id.toString() ); 
			Familia familia = familiasBeans.getFamilia(id);
			return familia;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener familia con id " + id.toString());
		}
    }
	
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public void addFamilia(Familia familia) throws ServiciosException{
        try{
            System.out.println("addFamilia-nombre " + familia.getNombre() );    
            familiasBeans.addFamilia(familia);
			return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar familia");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	// aca siguiendo la logica del teorico deberia decir solo Familia familia como parametro
      public void updateFamilia(@PathParam("id") Long id, Familia familia) throws ServiciosException{
        try{
            System.out.println("updateFamilia-nombre " + familia.getNombre());
            familia.setId(id);
            familiasBeans.updateFamilia(familia);
            return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar familia");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteFamilia(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteFamilia-id " + id.toString());
			familiasBeans.removeFamilia(id);
			return;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar familia");
		}
    }
    
}
