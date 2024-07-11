package com.scmm.hospitalclinicovet.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.service.impl.MascotaService;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private MascotaService mascotaService;
	
	private Mascota mascotaMock;
	private List<Ingreso> ingresosMascotaMock;
	
	@BeforeEach
	void beforeEach() {
		String dniResponsable = "23456389T";
		mascotaMock = new Mascota(1L, "perro", "bulldog", 5, "390547129375401", dniResponsable, true);
		
		ingresosMascotaMock = new ArrayList<Ingreso>();
		LocalDate fechaInicio1 = LocalDate.of(2022, 4, 27);
		LocalDate fechaFin1 = LocalDate.of(2022, 5, 3);
		ingresosMascotaMock.add(new Ingreso(1L, fechaInicio1, fechaFin1, EstadoIngreso.FINALIZADO, mascotaMock, dniResponsable));
		LocalDate fechaInicio2 = LocalDate.of(2024, 7, 9);
		ingresosMascotaMock.add(new Ingreso(2L, fechaInicio2, null, EstadoIngreso.ALTA, mascotaMock, dniResponsable));
	}
	
	@Test
	public void getMascotaByIdTest() throws Exception {
		// For
		when(mascotaService.getMascotaById(1L)).thenReturn(mascotaMock);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mascota/{idMascota}", 1L)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		String expectedResponse = "{"
				+ "\"id\":1,"
				+ "\"especie\":\"perro\","
				+ "\"raza\":\"bulldog\","
				+ "\"edad\":5,"
				+ "\"codIdentif\":\"390547129375401\","
				+ "\"dniResponsable\":\"23456389T\","
				+ "\"activa\":true"
				+ "}";
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getMascotaByIdNotFoundTest() throws Exception {
		// For
		when(mascotaService.getMascotaById(8L)).thenThrow(MascotaNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mascota/{idMascota}", 8L)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void getIngresosMascotaTest() throws Exception {
		// For
		when(mascotaService.getIngresosMascota(1L)).thenReturn(ingresosMascotaMock);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mascota/{idMascota}/ingreso", 1L)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;

		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		String expectedResponse = "["
				+ "{"
				+ "\"id\":1,"
				+ "\"alta\":\"2022-04-27\","
				+ "\"fin\":\"2022-05-03\","
				+ "\"estado\":\"FINALIZADO\","
				+ "\"mascota\":{\"id\":1,\"especie\":\"perro\",\"raza\":\"bulldog\",\"edad\":5,\"codIdentif\":\"390547129375401\",\"dniResponsable\":\"23456389T\",\"activa\":true},"
				+ "\"dniRegistroIngreso\":\"23456389T\""
				+ "},"
				+ "{"
				+ "\"id\":2,"
				+ "\"alta\":\"2024-07-09\","
				+ "\"fin\":null,"
				+ "\"estado\":\"ALTA\","
				+ "\"mascota\":{\"id\":1,\"especie\":\"perro\",\"raza\":\"bulldog\",\"edad\":5,\"codIdentif\":\"390547129375401\",\"dniResponsable\":\"23456389T\",\"activa\":true},"
				+ "\"dniRegistroIngreso\":\"23456389T\""
				+ "}"
				+ "]";
				
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void getIngresosMascotaNotFoundTest() throws Exception {
		// For
		when(mascotaService.getIngresosMascota(8L)).thenThrow(MascotaNotFoundException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mascota/{idMascota}/ingreso", 8L)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;

		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void addMascotaTest() throws Exception {
		// For
		when(mascotaService.createMascota("perro", "bulldog", 5, "390547129375401", "23456389T"))
			.thenReturn(mascotaMock);
		
		String mascotaJson = "{"
				+ "    \"especie\": \"perro\","
				+ "    \"raza\": \"bulldog\","
				+ "    \"edad\": 5,"
				+ "    \"codIdentif\": \"390547129375401\","
				+ "    \"dniResponsable\": \"23456389T\""
				+ "}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mascota")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mascotaJson)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		String expectedResponse = "{"
				+ "\"id\":1,"
				+ "\"especie\":\"perro\","
				+ "\"raza\":\"bulldog\","
				+ "\"edad\":5,"
				+ "\"codIdentif\":\"390547129375401\","
				+ "\"dniResponsable\":\"23456389T\","
				+ "\"activa\":true"
				+ "}";
		
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void addMascotaInputExceptionTest() throws Exception {
		// For
		when(mascotaService.createMascota("perro", "", 5, "", "23456389T"))
			.thenThrow(InputException.class);
		
		String mascotaJson = "{"
				+ "    \"especie\": \"perro\","
				+ "    \"raza\": \"\","
				+ "    \"edad\": 5,"
				+ "    \"codIdentif\": \"\","
				+ "    \"dniResponsable\": \"23456389T\""
				+ "}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mascota")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mascotaJson)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void bajaMascotaTest() throws Exception {
		// For
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/mascota/{idMascota}", 1L);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

	}
	
	@Test
	public void bajaMascotaNotFoundTest() throws Exception {
		// For
        doThrow(MascotaNotFoundException.class).when(mascotaService).bajaMascota(8L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/mascota/{idMascota}", 8L);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void bajaMascotaAlreadyBajaTest() throws Exception {
		// For
        doThrow(InputException.class).when(mascotaService).bajaMascota(1L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/mascota/{idMascota}", 1L);
		MvcResult result = null;
		
		// When
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
}
