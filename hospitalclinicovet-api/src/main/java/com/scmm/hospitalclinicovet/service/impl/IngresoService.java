package com.scmm.hospitalclinicovet.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.exception.IngresoNotFoundException;
import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.repository.IngresoRepository;
import com.scmm.hospitalclinicovet.repository.MascotaRepository;
import com.scmm.hospitalclinicovet.service.IIngresoService;

@Service
public class IngresoService implements IIngresoService {

	@Autowired
	private IngresoRepository ingresoRepository;
	
	// Se añade el repositorio de mascotas por inyección de dependencias en lugar del servicio de mascotas
	// para no crear referencias circulares entre servicios.
	@Autowired
	private MascotaRepository mascotaRepository;

	@Override
	public List<Ingreso> getAllIngresos() {
		List<Ingreso> ingresos = ingresoRepository.findAll();
		
		return ingresos;
	}

	@Override
	public Ingreso createIngreso(Long idMascota, String dniResponsable, LocalDate fechaIngreso) {
		Mascota mascota = mascotaRepository.findById(idMascota).orElseThrow(() -> new MascotaNotFoundException(idMascota));
		
		if (!mascota.isActiva()) {
			throw new InputException("No se puede ingresar a la mascota con id '" + idMascota + "' porque está dada de baja");
		}
		
		List<Ingreso> ingresosMascota = ingresoRepository.findByMascota(idMascota);
		
		for (Ingreso ing: ingresosMascota) {
			if (ing.getEstado().equals(EstadoIngreso.ALTA) || ing.getEstado().equals(EstadoIngreso.HOSPITALIZACION)) {
				throw new InputException("No se puede ingresar a la mascota con id '" + idMascota + "' porque ya tiene un ingreso activo");
			}
		}
		
		if (dniResponsable.compareTo(mascota.getDniResponsable()) != 0) {
			throw new InputException("El campo 'dniResponsable' no coincide con el dni del responsable de la mascota");
		}
		
		if (fechaIngreso == null) {
			throw new InputException("No se ha encontrado el campo 'fechaIngreso' para el ingreso");
		}
		
		LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = fechaActual.minusYears(mascota.getEdad());
        
        if (fechaIngreso.isBefore(fechaNacimiento)) {
        	throw new InputException("No se puede crear un ingreso con una fecha anterior a la fecha de nacimiento de la mascota");
        }
        
        if (fechaIngreso.isAfter(fechaActual)) {
        	throw new InputException("No se puede crear un ingreso con una fecha posterior a la fecha actual");
        }
		
		// Se establece el valor de estado como "ALTA" por defecto
		Ingreso ingreso = new Ingreso();
		ingreso.setMascota(mascota);
		ingreso.setDniRegistroIngreso(dniResponsable);
		ingreso.setAlta(fechaIngreso);
		
		return ingresoRepository.save(ingreso);
	}

	@Override
	public void updateIngreso(Long idMascota, Long idIngreso, String estadoIngreso, LocalDate finIngreso) {
		Ingreso ingreso = ingresoRepository.findByIdAndMascota(idIngreso, idMascota).orElseThrow(() -> new IngresoNotFoundException("No se ha encontrado el ingreso con ID '" +
																													idIngreso + "' e ID mascota '" + idMascota + "'"));
		
		// Validación de estado
		validateEstadoIngreso(ingreso);
		
		// Actualización
		if (estadoIngreso != null && finIngreso != null) {
			if (EstadoIngreso.FINALIZADO.toString().compareTo(estadoIngreso.toUpperCase()) == 0) {
				if (ingreso.getAlta().isAfter(finIngreso)) {
					throw new InputException("No se puede finalizar un ingreso con una fecha de fin anterior a la fecha de alta");
				}
				ingreso.setEstado(EstadoIngreso.FINALIZADO);
				ingreso.setFin(finIngreso);
			} else {
				throw new InputException("No se puede establecer un estado distinto de 'finalizado' con una fecha de fin de ingreso");
			}
		} else if (estadoIngreso != null) {
            if (EstadoIngreso.FINALIZADO.toString().compareTo(estadoIngreso.toUpperCase()) == 0) {
				throw new InputException("No se puede establecer el estado 'finalizado' sin especificar una fecha de fin de ingreso");
			} else {
				try {
					EstadoIngreso newEstado = EstadoIngreso.valueOf(estadoIngreso.toUpperCase());
					ingreso.setEstado(newEstado);
		        } catch (IllegalArgumentException e) {
		        	throw new InputException("Estado '" + estadoIngreso + "' no válido");
		        }
			}
		} else if (finIngreso != null) {
			if (ingreso.getAlta().isAfter(finIngreso)) {
				throw new InputException("No se puede finalizar un ingreso con una fecha de fin anterior a la fecha de alta");
			}
			// Se establece el estado "finalizado" para mantener la consistencia
			ingreso.setEstado(EstadoIngreso.FINALIZADO);
			ingreso.setFin(finIngreso);
		}
		
		ingresoRepository.save(ingreso);
	}

	@Override
	public void anulaIngreso(Long idIngreso) {
		Ingreso ingreso = ingresoRepository.findById(idIngreso).orElseThrow(() -> new IngresoNotFoundException(idIngreso));
		
		// Validación de estado
		validateEstadoIngreso(ingreso);
		
		ingreso.setEstado(EstadoIngreso.ANULADO);
		
		ingresoRepository.save(ingreso);
	}
	
	//Aux
	private void validateEstadoIngreso(Ingreso ingreso) {
		if (ingreso.getEstado().equals(EstadoIngreso.ANULADO)) {
			throw new InputException("No se puede modificar un ingreso con estado 'anulado'");
		} else if (ingreso.getEstado().equals(EstadoIngreso.FINALIZADO)) {
			throw new InputException("No se puede modificar un ingreso con estado 'finalizado'");
		}
	}
}
