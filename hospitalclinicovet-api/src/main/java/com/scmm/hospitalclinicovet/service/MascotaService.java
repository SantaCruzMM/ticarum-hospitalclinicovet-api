package com.scmm.hospitalclinicovet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.repository.IngresoRepository;
import com.scmm.hospitalclinicovet.repository.MascotaRepository;

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
		Mascota mascota = mascotaRepository.findById(idMascota).orElse(null);
		
		return mascota;
	}

	@Override
	public List<Ingreso> getIngresosMascota(Long idMascota) {
		List<Ingreso> ingresosMascota = ingresoRepository.findByMascota(idMascota);
		
		return ingresosMascota;
	}

	@Override
	public Mascota createMascota(Mascota mascota) {
		Mascota mascotaSaved = mascotaRepository.save(mascota);
		
		return mascotaSaved;
	}

	@Override
	public Mascota bajaMascota(Long idMascota) {
		Mascota mascota = mascotaRepository.findById(idMascota).orElse(null);
		
		mascota.setActiva(false);
		
		mascotaRepository.save(mascota);
		
		return mascota;
	}
	
	
}
