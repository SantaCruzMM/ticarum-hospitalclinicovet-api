package com.scmm.hospitalclinicovet.service;

import java.util.List;

import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;

public interface IMascotaService {
	
	Mascota getMascotaById(Long idMascota);
	
	List<Ingreso> getIngresosMascota(Long idMascota);
	
	Mascota createMascota(String especie, String raza, Integer edad, String codIdentif, String dniResponsable);
	
	Mascota bajaMascota(Long idMascota);
}
