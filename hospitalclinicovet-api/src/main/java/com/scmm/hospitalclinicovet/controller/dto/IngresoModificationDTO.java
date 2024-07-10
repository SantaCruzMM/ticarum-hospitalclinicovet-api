package com.scmm.hospitalclinicovet.controller.dto;

import java.time.LocalDate;

public class IngresoModificationDTO {
	private String estado;
	private LocalDate finIngreso;
	
	public IngresoModificationDTO() {
		
	}
	
	public IngresoModificationDTO(String estado, LocalDate finIngreso) {
		this.estado = estado;
		this.finIngreso = finIngreso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFinIngreso() {
		return finIngreso;
	}

	public void setFinIngreso(LocalDate finIngreso) {
		this.finIngreso = finIngreso;
	}
}
