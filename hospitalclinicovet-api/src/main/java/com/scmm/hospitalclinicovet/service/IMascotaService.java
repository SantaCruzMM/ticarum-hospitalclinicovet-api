package com.scmm.hospitalclinicovet.service;

import java.util.List;

import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;

// Interfaz que declara las acciones que pueden realizarse sobre una mascota
public interface IMascotaService {
	
	Mascota getMascotaById(Long idMascota);
	
	List<Ingreso> getIngresosMascota(Long idMascota);
	
	Mascota createMascota(String especie, String raza, Integer edad, String codIdentif, String dniResponsable);
	
	void bajaMascota(Long idMascota);
}
