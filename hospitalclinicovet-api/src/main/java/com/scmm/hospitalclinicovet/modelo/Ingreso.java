package com.scmm.hospitalclinicovet.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ingreso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "alta", nullable = false)
	private LocalDate alta;
	
	@Column(name = "fin")
	private LocalDate fin;
	
	@Column(name = "estado", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoIngreso estado;
	
	@ManyToOne
    @JoinColumn(name="fk_mascota_id", nullable=false)
	@JsonIgnore
	private Mascota mascota;
	
	@Column(name = "dni_registro_ingreso", nullable = false)
	private String dniRegistroIngreso;

	// Al crear un nuevo ingreso se establece el valor de su estado como "ALTA"
	public Ingreso() {
		estado = EstadoIngreso.ALTA;
	}

	public Ingreso(Long id, LocalDate alta, LocalDate fin, EstadoIngreso estado, Mascota mascota,
			String dniRegistroIngreso) {
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

	public EstadoIngreso getEstado() {
		return estado;
	}

	public void setEstado(EstadoIngreso estado) {
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
