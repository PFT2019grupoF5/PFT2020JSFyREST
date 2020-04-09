package com.bean;

import javax.faces.bean.ManagedBean;
import com.enumerated.tipoLoc;

@ManagedBean(name="tipoLocalB")

public class TipoLocalBean {

	public tipoLoc[] getTipoLocal(){
	    return tipoLoc.values();
	}
}