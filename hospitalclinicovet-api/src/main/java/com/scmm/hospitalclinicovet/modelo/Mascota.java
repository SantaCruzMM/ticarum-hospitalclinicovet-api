package com.scmm.hospitalclinicovet.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mascota {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "especie", nullable = false)
	private String especie;
	
	@Column(name = "raza", nullable = false)
	private String raza;
	
	@Column(name = "edad", nullable = false)
	private int edad;
	
	@Column(name = "codIdentif", nullable = false)
	private String codIdentif;
	
	@Column(name = "dniResponsable", nullable = false)
	private String dniResponsable;

	public Mascota() {
		
	}

	public Mascota(Long id, String especie, String raza, int edad, String codIdentif, String dniResponsable) {
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
