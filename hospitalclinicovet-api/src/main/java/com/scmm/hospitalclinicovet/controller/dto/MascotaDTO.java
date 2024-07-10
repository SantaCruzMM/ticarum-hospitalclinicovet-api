package com.scmm.hospitalclinicovet.controller.dto;

public class MascotaDTO {
	private Long id;
	private String especie;
	private String raza;
	private int edad;
	private String codIdentif;
	private String dniResponsable;
	
	public MascotaDTO() {
		
	}
	
	public MascotaDTO(Long id, String especie, String raza, int edad, String codIdentif, String dniResponsable) {
		this.id = id;
		this.especie = especie;
		this.raza = raza;
		this.edad = edad;
		this.codIdentif = codIdentif;
		this.dniResponsable = dniResponsable;
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
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
}
