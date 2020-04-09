package com.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.enumerated.Segmentacion;

@ManagedBean(name="segmentacionB")
@ApplicationScoped

public class SegmentacionBean {

	public Segmentacion[] getSegmentaciones() {
		return Segmentacion.values();
	}	
}
