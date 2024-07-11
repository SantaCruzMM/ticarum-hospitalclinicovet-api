package com.scmm.hospitalclinicovet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
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
	public Mascota createMascota(String especie, String raza, Integer edad, String codIdentif, String dniResponsable) {
		validateInputMascota(especie, raza, edad, codIdentif, dniResponsable);
		
		Mascota mascota = new Mascota();
		mascota.setEspecie(especie);
		mascota.setRaza(raza);
		mascota.setEdad(edad);
		mascota.setCodIdentif(codIdentif);
		mascota.setDniResponsable(dniResponsable);
		mascota.setActiva(true);
		
		Mascota mascotaSaved = mascotaRepository.save(mascota);
		
		return mascotaSaved;
	}

	@Override
	public void bajaMascota(Long idMascota) {
		Mascota mascota = mascotaRepository.findById(idMascota).orElseThrow(() -> new MascotaNotFoundException(idMascota));
		
		if (!mascota.isActiva()) {
			throw new InputException("No se puede volver a dar de baja a la mascota con id: " + idMascota);
		}
		
		mascota.setActiva(false);
		
		mascotaRepository.save(mascota);
		
		// Si la mascota tiene algún ingreso activo se anulará
		List<Ingreso> ingresosMascota = getIngresosMascota(idMascota);
		
		for (Ingreso ing: ingresosMascota) {
			if (ing.getEstado().equals(EstadoIngreso.ALTA) || ing.getEstado().equals(EstadoIngreso.HOSPITALIZACION)) {
				ing.setEstado(EstadoIngreso.ANULADO);
				ingresoRepository.save(ing);
			}
		}
	}
	
	// Aux
	private void validateInputMascota(String especie, String raza, Integer edad, String codIdentif, String dniResponsable) {
		if (especie == null || especie.compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo 'especie' para la mascota");
		}
		
		if (raza == null || raza.compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo 'raza' para la mascota");
		}
		
		if (edad == null || edad < 0) {
			throw new InputException("No se ha encontrado el campo 'edad' para la mascota o es menor que 0");
		}
		
		if (codIdentif == null || codIdentif.compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo 'codIdentif' para la mascota");
		}
		
		if (dniResponsable == null || dniResponsable.compareTo("") == 0) {
			throw new InputException("No se ha encontrado el campo 'dniResponsable' para la mascota");
		}
	}
}
