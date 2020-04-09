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

import com.beans.IMovis2Remote;
import com.entities.Movi2;
import com.exception.ServiciosException;

@Stateless
@Path("/movis2")
public class Movis2Rest {

	@EJB
	private IMovis2Remote movis2Beans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movi2> getAllMovis2() throws ServiciosException {
		try{
			List<Movi2> listaMovis2 = movis2Beans.getAllMovis2(); 
			return listaMovis2;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movis2");
		}
    }
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movi2 getMovi2(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdMovi2-id " + id.toString() ); 
			Movi2 movi2 = movis2Beans.getMovi2(id);
			return movi2;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movi2 con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Movi2 addMovi2(Movi2 movi2) throws ServiciosException{
        try{
        	//ojo!!! aca cambie porque movi2 no tiene campo nombre
            System.out.println("addMovi2-id " + movi2.getId().toString() );    
            movis2Beans.addMovi2(movi2);
			return movi2;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar movi2");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
      public Movi2 updateMovi2(@PathParam("id") Long id, Movi2 movi2) throws ServiciosException{
        try{
        	//ojo!!! aca cambie porque movi2 no tiene campo nombre
            System.out.println("updateMovi2-id " + movi2.getId().toString() );
            movi2.setId(id);
            movis2Beans.updateMovi2(movi2);
            return movi2;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar movi2");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movi2 deleteMovi2(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteMovi2-id " + id.toString());
			Movi2 movi2 = movis2Beans.getMovi2(id);
			movis2Beans.removeMovi2(id);
			return movi2;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar movi2");
		}
    }
    
}
