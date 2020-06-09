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

import com.beans.IMovimientosRemote;
import com.entities.Movimiento;
import com.exception.ServiciosException;

@Stateless
@Path("/movimientos")
public class MovimientosRest {

	@EJB
	private IMovimientosRemote movimientosBeans;

	@GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movimiento> getAllMovimientos() throws ServiciosException {
		try{
			List<Movimiento> listaMovimientos = movimientosBeans.getAllMovimientos(); 
			return listaMovimientos;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener lista de movimientos");
		}
    }
	
	@GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movimiento getMovimiento(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("getByIdMovimiento-id " + id.toString() ); 
			Movimiento movimiento = movimientosBeans.getMovimiento(id);
			return movimiento;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo obtener movimiento con id " + id.toString());
		}
    }
		
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Movimiento addMovimiento(Movimiento movimiento) throws ServiciosException{
        try{
            System.out.println("addMovimiento-id " + movimiento.getDescripcion() );    
            System.out.println("addMovimiento-Fecha  " + movimiento.getFecha().toString());    
            movimientosBeans.addMovimiento(movimiento);
			return movimiento;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo agregar movimiento");
        }
    }
	
    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
      public Movimiento updateMovimiento(@PathParam("id") Long id, Movimiento movimiento) throws ServiciosException{
        try{
            System.out.println("updateMovimiento-id " + movimiento.getId().toString() );
            movimiento.setId(id);
            movimientosBeans.updateMovimiento(movimiento);
            return movimiento;
        }catch(PersistenceException e){
            e.printStackTrace();
            throw new ServiciosException("No se pudo modificar movimiento");
        }
    }
    
    
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Movimiento deleteMovimiento(@PathParam("id") Long id) throws ServiciosException {
		try{
			System.out.println("deleteMovimiento-id " + id.toString());
			Movimiento movimiento = movimientosBeans.getMovimiento(id);
			movimientosBeans.removeMovimiento(id);
			return movimiento;
		}catch(PersistenceException e){
			throw new ServiciosException("No se pudo borrar movimiento");
		}
    }
    
}
