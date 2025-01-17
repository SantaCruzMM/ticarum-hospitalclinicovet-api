package com.scmm.hospitalclinicovet.controller.dto.mapper;

import com.scmm.hospitalclinicovet.controller.dto.MascotaDTO;
import com.scmm.hospitalclinicovet.modelo.Mascota;

//Clase para conversiones entre DTO y entidad de mascotas
public class MascotaMapper {
	
	public static MascotaDTO toDto(Mascota mascota) {
		MascotaDTO mascotaDto = new MascotaDTO();
		
		mascotaDto.setId(mascota.getId());
		mascotaDto.setEspecie(mascota.getEspecie());
		mascotaDto.setRaza(mascota.getRaza());
		mascotaDto.setEdad(mascota.getEdad());
		mascotaDto.setCodIdentif(mascota.getCodIdentif());
		mascotaDto.setDniResponsable(mascota.getDniResponsable());
		mascotaDto.setActiva(mascota.isActiva());
		
		return mascotaDto;
	}
	
	public static Mascota toEntity(MascotaDTO mascotaDto) {
		Mascota mascota = new Mascota();
		
		mascota.setId(mascotaDto.getId());
		mascota.setEspecie(mascotaDto.getEspecie());
		mascota.setRaza(mascotaDto.getRaza());
		mascota.setEdad(mascotaDto.getEdad());
		mascota.setCodIdentif(mascotaDto.getCodIdentif());
		mascota.setDniResponsable(mascotaDto.getDniResponsable());
		mascota.setActiva(mascotaDto.isActiva());
		
		return mascota;
	}
}
