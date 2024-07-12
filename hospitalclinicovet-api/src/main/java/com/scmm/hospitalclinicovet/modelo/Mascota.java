package com.scmm.hospitalclinicovet.modelo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Clase que representa a una mascota
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
	private Integer edad;
	
	@Column(name = "cod_identif", nullable = false)
	private String codIdentif;
	
	@Column(name = "dni_responsable", nullable = false)
	private String dniResponsable;
	
	@Column(name = "activa", nullable = false)
	private boolean activa;

	public Mascota() {
		
	}

	public Mascota(Long id, String especie, String raza, Integer edad, String codIdentif, String dniResponsable, boolean activa) {
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

	@Override
	public int hashCode() {
		return Objects.hash(activa, codIdentif, dniResponsable, edad, especie, id, raza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mascota other = (Mascota) obj;
		return activa == other.activa && Objects.equals(codIdentif, other.codIdentif)
				&& Objects.equals(dniResponsable, other.dniResponsable) && Objects.equals(edad, other.edad)
				&& Objects.equals(especie, other.especie) && Objects.equals(id, other.id)
				&& Objects.equals(raza, other.raza);
	}
}
