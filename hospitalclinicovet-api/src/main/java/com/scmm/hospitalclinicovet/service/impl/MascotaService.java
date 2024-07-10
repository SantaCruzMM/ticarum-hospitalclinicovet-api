package com.scmm.hospitalclinicovet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.repository.IngresoRepository;
import com.scmm.hospitalclinicovet.repository.MascotaRepository;
import com.scmm.hospitalclinicovet.service.IMascotaService;

@Service
public class MascotaService  implements IMascotaService {
	
	@Autowired
	private MascotaRepository mascotaRepository;
	
	// Se añade el repositorio de ingresos por inyección de dependencias en lugar del servicio de ingresos
	// para no crear referencias circulares entre servicios.
	@Autowired
	private IngresoRepository ingresoRepository;

	@Override
	public Mascota getMascotaById(Long idMascota) {
		Mascota mascota = mascotaRepository.findById(idMascota).orElseThrow(() -> new MascotaNotFoundException(idMascota));
		
		return mascota;
	}

	@Override
	public List<Ingreso> getIngresosMascota(Long idMascota) {
		// Se busca la mascota para verificar que exista en base de datos, de lo contrario, se lanza una excepción
		mascotaRepository.findById(idMascota).orElseThrow(() -> new MascotaNotFoundException(idMascota));
		List<Ingreso> ingresosMascota = ingresoRepository.findByMascota(idMascota);
		
		return ingresosMascota;
	}

	@Override
	public Mascota createMascota(Mascota mascota) {
		validateMascota(mascota);
		Mascota mascotaSaved = mascotaRepository.save(mascota);
		
		return mascotaSaved;
	}

	@Override
	public Mascota bajaMascota(Long idMascota) {
		Mascota mascota = mascotaRepository.findById(idMascota).orElseThrow(() -> new MascotaNotFoundException(idMascota));
		
		mascota.setActiva(false);
		
		mascotaRepository.save(mascota);
		
		return mascota;
	}
	
	// Aux
	private void validateMascota(Mascota mascota) {
		if (mascota.getEspecie() == null || mascota.getEspecie().compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo \"especie\" para la mascota");
		}
		
		if (mascota.getRaza() == null || mascota.getRaza().compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo \"raza\" para la mascota");
		}
		
		if (mascota.getEdad() == null || mascota.getEdad() < 0) {
			throw new InputException("No se ha encontrado el campo \"edad\" para la mascota o es menor que 0");
		}
		
		if (mascota.getCodIdentif() == null || mascota.getCodIdentif().compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo \"codIdentif\" para la mascota");
		}
		
		if (mascota.getDniResponsable() == null || mascota.getDniResponsable().compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo \"dniResponsable\" para la mascota");
		}
	}
}
