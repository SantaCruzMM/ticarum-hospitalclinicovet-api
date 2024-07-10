package com.scmm.hospitalclinicovet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmm.hospitalclinicovet.controller.dto.IngresoDTO;
import com.scmm.hospitalclinicovet.controller.dto.IngresoModificationDTO;
import com.scmm.hospitalclinicovet.controller.dto.NuevoIngresoDTO;
import com.scmm.hospitalclinicovet.controller.dto.mapper.IngresoMapper;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.service.impl.IngresoService;

@RestController
@RequestMapping("/ingreso")
public class IngresoController {
	
	@Autowired
	private IngresoService ingresoService;

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<IngresoDTO>> getAllIngresos() {
		
		List<Ingreso> ingresos = ingresoService.getAllIngresos();
		
		List<IngresoDTO> ingresosDto = IngresoMapper.toListDto(ingresos);
		
		return new ResponseEntity<List<IngresoDTO>>(ingresosDto, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<IngresoDTO> addIngreso(@RequestBody NuevoIngresoDTO nuevoIngreso) {
		
		Ingreso ingreso = ingresoService.createIngreso(nuevoIngreso.getIdMascota(), nuevoIngreso.getDniResponsable(), nuevoIngreso.getFechaIngreso());
		
		IngresoDTO ingresoDto = IngresoMapper.toDto(ingreso);
		
		return new ResponseEntity<IngresoDTO>(ingresoDto, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{idMascota}/{idIngreso}", consumes = "application/json")
	public ResponseEntity<Void> updateIngreso(@PathVariable Long idMascota, @PathVariable Long idIngreso, @RequestBody IngresoModificationDTO ingresoModification) {
		
		ingresoService.updateIngreso(idMascota, idIngreso, ingresoModification.getEstado(), ingresoModification.getFinIngreso());
	
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{idIngreso}")
	public ResponseEntity<Void> anulaIngreso(@PathVariable Long idIngreso) {
		
		ingresoService.anulaIngreso(idIngreso);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
