package com.scmm.hospitalclinicovet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scmm.hospitalclinicovet.controller.dto.IngresoDTO;
import com.scmm.hospitalclinicovet.controller.dto.MascotaDTO;
import com.scmm.hospitalclinicovet.controller.dto.mapper.IngresoMapper;
import com.scmm.hospitalclinicovet.controller.dto.mapper.MascotaMapper;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.service.impl.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Mascotas", description = "Operaciones relacionadas con mascotas")
@RestController
@RequestMapping("/mascota")
public class MascotaController {

	@Autowired
	private MascotaService mascotaService;
	
	@Operation(summary = "Obtener una mascota por ID", description = "Devuelve una mascota según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
	@GetMapping(value = "/{idMascota}", produces = "application/json")
	public ResponseEntity<MascotaDTO> getMascotaById(@Parameter(description = "ID de la mascota") @PathVariable Long idMascota) {
		
		Mascota mascota = mascotaService.getMascotaById(idMascota);
		
		MascotaDTO mascotaDto = MascotaMapper.toDto(mascota);
		
		return new ResponseEntity<MascotaDTO>(mascotaDto, HttpStatus.OK);
	}
	
	@Operation(summary = "Obtener una lista de ingresos de una mascota", description = "Devuelve los ingresos de una mascota según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de ingresos de una mascota recuperado"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
	@GetMapping(value = "/{idMascota}/ingreso", produces = "application/json")
	public ResponseEntity<List<IngresoDTO>> getIngresosMascota(@Parameter(description = "ID de la mascota") @PathVariable Long idMascota) {
		
		List<Ingreso> ingresosMascota = mascotaService.getIngresosMascota(idMascota);
		
		List<IngresoDTO> ingresosMascotaDto = IngresoMapper.toListDto(ingresosMascota);
		
		return new ResponseEntity<List<IngresoDTO>>(ingresosMascotaDto, HttpStatus.OK);
		
	}
	
	@Operation(summary = "Crear una nueva mascota", description = "Crea una nueva mascota a partir de sus datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mascota creada"),
        @ApiResponse(responseCode = "400", description = "Datos para crear la mascota incorrectos o incompletos")
    })
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<MascotaDTO> addMascota(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la nueva mascota")
		@RequestBody MascotaDTO mascotaDto) {
		
		Mascota nuevaMascota = mascotaService.createMascota(mascotaDto.getEspecie(), mascotaDto.getRaza(),
														mascotaDto.getEdad(), mascotaDto.getCodIdentif(),
														mascotaDto.getDniResponsable());
		
		MascotaDTO nuevaMascotaDto = MascotaMapper.toDto(nuevaMascota);
		
		return new ResponseEntity<MascotaDTO>(nuevaMascotaDto, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Dar de baja una mascota", description = "Da de baja una mascota según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mascota dada de baja"),
        @ApiResponse(responseCode = "400", description = "Mascota ya dada de baja"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
	@DeleteMapping(value = "/{idMascota}")
	public ResponseEntity<Void> bajaMascota(@Parameter(description = "ID de la mascota") @PathVariable Long idMascota) {
		
		mascotaService.bajaMascota(idMascota);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
