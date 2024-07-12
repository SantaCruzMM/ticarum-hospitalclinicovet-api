package com.scmm.hospitalclinicovet.controller.dto;

import java.time.LocalDate;

import com.scmm.hospitalclinicovet.modelo.Mascota;

// Clase que representa un ingreso para las llamadas de nuestra API
public class IngresoDTO {
	private Long id;
	private LocalDate alta;
	private LocalDate fin;
	private String estado;
	private Mascota mascota;
	private String dniRegistroIngreso;
	
	public IngresoDTO() {
		
	}
	
	public IngresoDTO(Long id, LocalDate alta, LocalDate fin, String estado, Mascota mascota, String dniRegistroIngreso) {
		this.id = id;
		this.alta = alta;
		this.fin = fin;
		this.estado = estado;
		this.mascota = mascota;
		this.dniRegistroIngreso = dniRegistroIngreso;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getAlta() {
		return alta;
	}
	public void setAlta(LocalDate alta) {
		this.alta = alta;
	}
	public LocalDate getFin() {
		return fin;
	}
	public void setFin(LocalDate fin) {
		this.fin = fin;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Mascota getMascota() {
		return mascota;
	}
	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}
	public String getDniRegistroIngreso() {
		return dniRegistroIngreso;
	}
	public void setDniRegistroIngreso(String dniRegistroIngreso) {
		this.dniRegistroIngreso = dniRegistroIngreso;
	}
}
