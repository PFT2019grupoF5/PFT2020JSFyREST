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

import com.beans.IPedidosRemote;
import com.entities.Pedido;
import com.exception.ServiciosException;

@Stateless
@Path("/pedidos")
public class PedidosRest {

	@EJB
	private IPedidosRemote pedidosBeans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pedido> getAllPedidos() throws ServiciosException {
		try{
			List<Pedido> listaPedidos = pedidosBeans.getAllPedidos(); 
			return listaPedidos;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de pedidos");
		}
    }
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pedido getPedido(@PathParam("id") Long id) throws ServiciosException {
		try{ 
			System.out.println("getByIdPedido-id " + id.toString() );
			Pedido pedido = pedidosBeans.getPedido(id);
			return pedido;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener pedido con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Pedido addPedido(Pedido pedido) throws ServiciosException{
        try{
            System.out.println("addPedido-id " + pedido.getId().toString() );    
            System.out.println("addPedido-fecha " + pedido.getFecha().toString() );    
            pedidosBeans.addPedido(pedido);
			return pedido;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar pedido");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
      public Pedido updatePedido(@PathParam("id") Long id, Pedido pedido) throws ServiciosException{
        try{
            System.out.println("updatePedido-id " + pedido.getId().toString() );
            pedido.setId(id);
            pedidosBeans.updatePedido(pedido);
            return pedido;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar pedido");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pedido deletePedido(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deletePedido-id " + id.toString());
			Pedido pedido = pedidosBeans.getPedido(id);
			pedidosBeans.removePedido(id);
			return pedido;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar pedido");
		}
    }
    
}
