package com.scmm.hospitalclinicovet.service;

import java.time.LocalDate;
import java.util.List;

import com.scmm.hospitalclinicovet.modelo.Ingreso;

// Interfaz que declara las acciones que pueden realizarse sobre un ingreso
public interface IIngresoService {
	
	List<Ingreso> getAllIngresos();
	
	Ingreso createIngreso(Long idMascota, String dniResponsable, LocalDate fechaIngreso);
	
	void updateIngreso(Long idMascota, Long idIngreso, String estadoIngreso, LocalDate finIngreso);
	
	void anulaIngreso(Long idIngreso);
}
