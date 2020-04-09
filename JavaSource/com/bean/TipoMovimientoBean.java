package com.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import com.enumerated.tipoMovimiento;

@ManagedBean(name="tipomovimientoB")
@ApplicationScoped

public class TipoMovimientoBean {

	public tipoMovimiento[] getTipoMovimiento() {
		return tipoMovimiento.values();
	}	
}
