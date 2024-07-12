package com.scmm.hospitalclinicovet.controller.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.scmm.hospitalclinicovet.controller.dto.IngresoDTO;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;

// Clase para conversiones entre DTO y entidad de ingresos
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
	
	public static List<IngresoDTO> toListDto(List<Ingreso> ingresos) {
		return ingresos.stream().map(IngresoMapper::toDto).collect(Collectors.toList());
	}
}
