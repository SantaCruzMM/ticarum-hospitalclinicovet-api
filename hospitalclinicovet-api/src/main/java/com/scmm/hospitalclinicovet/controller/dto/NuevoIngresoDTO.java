package com.scmm.hospitalclinicovet.controller.dto;

import java.time.LocalDate;

//Clase que representa un nuevo ingreso para las llamadas de nuestra API
public class NuevoIngresoDTO {
	private Long idMascota;
	private String dniResponsable;
	private LocalDate fechaIngreso;
	
	public NuevoIngresoDTO() {
		
	}
	
	public NuevoIngresoDTO(Long idMascota, String dniResponsable, LocalDate fechaIngreso) {
		this.idMascota = idMascota;
		this.dniResponsable = dniResponsable;
		this.fechaIngreso = fechaIngreso;
	}

	public Long getIdMascota() {
		return idMascota;
	}

	public void setIdMascota(Long idMascota) {
		this.idMascota = idMascota;
	}

	public String getDniResponsable() {
		return dniResponsable;
	}

	public void setDniResponsable(String dniResponsable) {
		this.dniResponsable = dniResponsable;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
}
