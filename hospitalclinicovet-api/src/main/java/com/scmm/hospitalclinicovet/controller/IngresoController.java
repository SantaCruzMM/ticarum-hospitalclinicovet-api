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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ingresos", description = "Operaciones relacionadas con ingresos")
@RestController
@RequestMapping("/ingreso")
public class IngresoController {
	
	@Autowired
	private IngresoService ingresoService;

	@Operation(summary = "Obtener todos los ingresos", description = "Devuelve una lista con todos los ingresos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de ingresos recuperado")
    })
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<IngresoDTO>> getAllIngresos() {
		
		List<Ingreso> ingresos = ingresoService.getAllIngresos();
		
		List<IngresoDTO> ingresosDto = IngresoMapper.toListDto(ingresos);
		
		return new ResponseEntity<List<IngresoDTO>>(ingresosDto, HttpStatus.OK);
	}
	
	@Operation(summary = "Crear un nuevo ingreso", description = "Crea un nuevo ingreso a partir de sus datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ingreso creado"),
        @ApiResponse(responseCode = "400", description = "Datos para crear el ingreso incorrectos o incompletos")
    })
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<IngresoDTO> addIngreso(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del nuevo ingreso")
		@RequestBody NuevoIngresoDTO nuevoIngreso) {
		
		Ingreso ingreso = ingresoService.createIngreso(nuevoIngreso.getIdMascota(), nuevoIngreso.getDniResponsable(), nuevoIngreso.getFechaIngreso());
		
		IngresoDTO ingresoDto = IngresoMapper.toDto(ingreso);
		
		return new ResponseEntity<IngresoDTO>(ingresoDto, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Actualizar un ingreso", description = "Actualiza el estado y la fecha de fin de un ingreso de una mascota")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ingreso actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos para actualizar el ingreso incorrectos o incompletos"),
        @ApiResponse(responseCode = "404", description = "Ingreso no encontrado")
    })
	@PutMapping(value = "/{idMascota}/{idIngreso}", consumes = "application/json")
	public ResponseEntity<Void> updateIngreso(@Parameter(description = "ID de la mascota") @PathVariable Long idMascota,
		@Parameter(description = "ID del ingreso") @PathVariable Long idIngreso, 
		@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos a actualizar del ingreso")
			@RequestBody IngresoModificationDTO ingresoModification) {
		
		ingresoService.updateIngreso(idMascota, idIngreso, ingresoModification.getEstado(), ingresoModification.getFinIngreso());
	
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@Operation(summary = "Anular un ingreso", description = "Anula un ingreso activo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ingreso anulado"),
        @ApiResponse(responseCode = "400", description = "Datos para anular el ingreso incorrectos o incompletos"),
        @ApiResponse(responseCode = "404", description = "Ingreso no encontrado")
    })
	@DeleteMapping(value = "/{idIngreso}")
	public ResponseEntity<Void> anulaIngreso(@Parameter(description = "ID del ingreso") @PathVariable Long idIngreso) {
		
		ingresoService.anulaIngreso(idIngreso);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
