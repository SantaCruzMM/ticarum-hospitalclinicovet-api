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


@RestController
@RequestMapping("/mascota")
public class MascotaController {

	@Autowired
	private MascotaService mascotaService;
	
	@GetMapping(value = "/{idMascota}", produces = "application/json")
	public ResponseEntity<MascotaDTO> getMascotaById(@PathVariable Long idMascota) {
		
		Mascota mascota = mascotaService.getMascotaById(idMascota);
		
		MascotaDTO mascotaDto = MascotaMapper.toDto(mascota);
		
		return new ResponseEntity<MascotaDTO>(mascotaDto, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{idMascota}/ingreso", produces = "application/json")
	public ResponseEntity<List<IngresoDTO>> getIngresosMascota(@PathVariable Long idMascota) {
		
		List<Ingreso> ingresosMascota = mascotaService.getIngresosMascota(idMascota);
		
		List<IngresoDTO> ingresosMascotaDto = IngresoMapper.toListDto(ingresosMascota);
		
		return new ResponseEntity<List<IngresoDTO>>(ingresosMascotaDto, HttpStatus.OK);
		
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<MascotaDTO> addMascota(@RequestBody MascotaDTO mascotaDto) {
		
		Mascota nuevaMascota = mascotaService.createMascota(mascotaDto.getEspecie(), mascotaDto.getRaza(),
														mascotaDto.getEdad(), mascotaDto.getCodIdentif(),
														mascotaDto.getDniResponsable());
		
		MascotaDTO nuevaMascotaDto = MascotaMapper.toDto(nuevaMascota);
		
		return new ResponseEntity<MascotaDTO>(nuevaMascotaDto, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{idMascota}")
	public ResponseEntity<Void> bajaMascota(@PathVariable Long idMascota) {
		
		mascotaService.bajaMascota(idMascota);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
