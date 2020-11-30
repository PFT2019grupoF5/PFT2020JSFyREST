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

import com.beans.IRenglonPedidosRemote;
import com.entities.RenglonPedido;
import com.exception.ServiciosException;

@Stateless
@Path("/renglonPedidos2")
public class RenglonPedidosRest2 {

	@EJB
	private IRenglonPedidosRemote renglonPedidosBeans;
	
	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RenglonPedido> getAllRenglonPedidos() throws ServiciosException {
		try{
			List<RenglonPedido> listaRenglonPedidos = renglonPedidosBeans.getAllRenglonPedidos(); 
			return listaRenglonPedidos;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de renglonPedidos");
		}
    }
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public RenglonPedido getRenglonPedido(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdRenglonPedido-id " + id.toString() ); 
			RenglonPedido renglonPedido = renglonPedidosBeans.getRenglonPedido(id);
			return renglonPedido;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener renglonPedido con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public void addRenglonPedido(RenglonPedido renglonPedido) throws ServiciosException{
        try{
        	//ojo!!! aca cambie porque renglonPedido no tiene campo nombre
            System.out.println("addRenglonPedido-id " + renglonPedido.getId().toString() );    
            renglonPedidosBeans.addRenglonPedido(renglonPedido);
			return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar renglonPedido");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	// aca siguiendo la logica del teorico deberia decir solo RenglonPedido renglonPedido como parametro
      public void updateRenglonPedido(@PathParam("id") Long id, RenglonPedido renglonPedido) throws ServiciosException{
        try{
        	//ojo!!! aca cambie porque renglonPedido no tiene campo nombre
            System.out.println("updateRenglonPedido-id " + renglonPedido.getId().toString() );
            renglonPedido.setId(id);
            renglonPedidosBeans.updateRenglonPedido(renglonPedido);
            return;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar renglonPedido");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteRenglonPedido(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteRenglonPedido-id " + id.toString());
			renglonPedidosBeans.removeRenglonPedido(id);
			return;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar renglonPedido");
		}
    }
    
}