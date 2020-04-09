package com.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean (name="fechaB")

public class FechaBean {
	private Date fecEstimPed;
	private Date fecha;
	private Date pedrecfecha;

	public Date getFecEstimPed() { return(fecEstimPed);}
	public Date getFecha() { return(fecha);}
	public Date getPedRecFecha() { return(pedrecfecha);}
	
	public void setFecEstimPed(Date fecestimped) {
		this.fecEstimPed = fecestimped;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public void setPedRecFecha(Date pedrecfecha) {
		this.pedrecfecha = pedrecfecha;
	}
}
