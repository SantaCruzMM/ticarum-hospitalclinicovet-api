package com.scmm.hospitalclinicovet.controller.dto;

//Clase que representa una mascota para las llamadas de nuestra API
public class MascotaDTO {
	private Long id;
	private String especie;
	private String raza;
	private Integer edad;
	private String codIdentif;
	private String dniResponsable;
	private boolean activa;
	
	public MascotaDTO() {
		
	}
	
	public MascotaDTO(Long id, String especie, String raza, Integer edad, String codIdentif, String dniResponsable, boolean activa) {
		this.id = id;
		this.especie = especie;
		this.raza = raza;
		this.edad = edad;
		this.codIdentif = codIdentif;
		this.dniResponsable = dniResponsable;
		this.activa = activa;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	public Integer getEdad() {
		return edad;
	}
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	public String getCodIdentif() {
		return codIdentif;
	}
	public void setCodIdentif(String codIdentif) {
		this.codIdentif = codIdentif;
	}
	public String getDniResponsable() {
		return dniResponsable;
	}
	public void setDniResponsable(String dniResponsable) {
		this.dniResponsable = dniResponsable;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
}
