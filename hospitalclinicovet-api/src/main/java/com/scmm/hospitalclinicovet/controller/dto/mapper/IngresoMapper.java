package com.scmm.hospitalclinicovet.controller.dto.mapper;

import com.scmm.hospitalclinicovet.controller.dto.IngresoDTO;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;

public class IngresoMapper {
	
	public static IngresoDTO toDto(Ingreso ingreso) {
		IngresoDTO ingresoDto = new IngresoDTO();
		
		ingresoDto.setId(ingreso.getId());
		ingresoDto.setAlta(ingreso.getAlta());
		ingresoDto.setFin(ingreso.getFin());
		ingresoDto.setEstado(ingreso.getEstado().toString());
		ingresoDto.setMascota(ingreso.getMascota());
		ingresoDto.setDniRegistroIngreso(ingreso.getDniRegistroIngreso());
		
		return ingresoDto;
	}
	
	public static Ingreso toEntity(IngresoDTO ingresoDto) {
		Ingreso ingreso = new Ingreso();
		
		ingreso.setId(ingresoDto.getId());
		ingreso.setAlta(ingresoDto.getAlta());
		ingreso.setFin(ingresoDto.getFin());
		ingreso.setEstado(EstadoIngreso.valueOf(ingresoDto.getEstado()));
		ingreso.setMascota(ingresoDto.getMascota());
		ingreso.setDniRegistroIngreso(ingresoDto.getDniRegistroIngreso());
		
		return ingreso;
	}
}
