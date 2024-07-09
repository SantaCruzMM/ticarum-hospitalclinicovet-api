package com.scmm.hospitalclinicovet.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.repository.IngresoRepository;
import com.scmm.hospitalclinicovet.repository.MascotaRepository;

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
		Mascota mascota = mascotaRepository.findById(idMascota).orElse(null);
		
		if (dniResponsable.compareTo(mascota.getDniResponsable()) != 0) {
			//TODO: GESTIÓN DE ERRORES
		}
		
		Ingreso ingreso = new Ingreso();
		ingreso.setMascota(mascota);
		ingreso.setDniRegistroIngreso(dniResponsable);
		ingreso.setAlta(fechaIngreso);
		
		return ingresoRepository.save(ingreso);
	}

	@Override
	public void updateIngreso(Long idMascota, Long idIngreso, String estadoIngreso, LocalDate finIngreso) {
		Ingreso ingreso = ingresoRepository.findByIdAndMascota(idIngreso, idMascota);
		
		if (estadoIngreso != null) {
			try {
	            EstadoIngreso newEstado = EstadoIngreso.valueOf(estadoIngreso.toUpperCase());
	            ingreso.setEstado(newEstado);
	        } catch (IllegalArgumentException e) {
	            // TODO: GESTIÓN DE ERRORES
	        }
		}
		
		if (finIngreso != null) {
			// Se establece este estado para mantener la consistencia
			// TODO: Se podría lanzar una excepción en caso de que estadoIngreso tenga u valor diferente a FINALIZADO cuando exista una fecha
			ingreso.setEstado(EstadoIngreso.FINALIZADO);
			ingreso.setFin(finIngreso);
		}
		
		ingresoRepository.save(ingreso);
	}

	@Override
	public Ingreso anulaIngreso(Long idIngreso) {
		Ingreso ingreso = ingresoRepository.findById(idIngreso).orElse(null);
		
		ingreso.setEstado(EstadoIngreso.ANULADO);
		
		ingresoRepository.save(ingreso);
		
		return ingreso;
	}
}
