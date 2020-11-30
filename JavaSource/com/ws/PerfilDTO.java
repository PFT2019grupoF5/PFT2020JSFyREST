package com.ws;

import com.enumerated.tipoPerfil;

public class PerfilDTO {

	private Long id;
	private tipoPerfil tipoperfil;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public tipoPerfil getTipoperfil() {
		return tipoperfil;
	}
	public void setTipoperfil(tipoPerfil tipoperfil) {
		this.tipoperfil = tipoperfil;
	}

	public PerfilDTO(Long id, tipoPerfil tipoperfil) {
		super();
		this.id = id;
		this.tipoperfil = tipoperfil;
	}
	
}

