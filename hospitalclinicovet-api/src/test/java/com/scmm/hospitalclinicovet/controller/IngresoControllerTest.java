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

import com.scmm.hospitalclinicovet.exception.IngresoNotFoundException;
import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.service.impl.IngresoService;

@WebMvcTest(IngresoController.class)
public class IngresoControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private IngresoService ingresoService;
	
	private Mascota mascotaMock;
	private List<Ingreso> ingresosMock;
	
	@BeforeEach
	void beforeEach() {
		String dniResponsable = "23456389T";
		mascotaMock = new Mascota(1L, "perro", "bulldog", 5, "390547129375401", dniResponsable, true);
		
		ingresosMock = new ArrayList<Ingreso>();
		LocalDate fechaInicio1 = LocalDate.of(2022, 4, 27);
		LocalDate fechaFin1 = LocalDate.of(2022, 5, 3);
		ingresosMock.add(new Ingreso(1L, fechaInicio1, fechaFin1, EstadoIngreso.FINALIZADO, mascotaMock, dniResponsable));
		LocalDate fechaInicio2 = LocalDate.of(2024, 7, 9);
		ingresosMock.add(new Ingreso(2L, fechaInicio2, null, EstadoIngreso.ALTA, mascotaMock, dniResponsable));
	}
	
	@Test
	public void getAllIngresosTest() throws Exception {
		// For
		when(ingresoService.getAllIngresos()).thenReturn(ingresosMock);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ingreso")
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
	public void addIngresoTest() throws Exception {
		// For
		LocalDate fechaIngreso = LocalDate.of(2024, 7, 9);
		String nuevoIngresoJson = "{"
				+ "    \"idMascota\": 1,"
				+ "    \"dniResponsable\": \"23456389T\","
				+ "    \"fechaIngreso\": \"2024-07-09\""
				+ "}";
		
		when(ingresoService.createIngreso(1L, "23456389T", fechaIngreso)).thenReturn(ingresosMock.get(1));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ingreso")
				.contentType(MediaType.APPLICATION_JSON)
				.content(nuevoIngresoJson)
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
				+ "\"id\":2,"
				+ "\"alta\":\"2024-07-09\","
				+ "\"fin\":null,"
				+ "\"estado\":\"ALTA\","
				+ "\"mascota\":{\"id\":1,\"especie\":\"perro\",\"raza\":\"bulldog\",\"edad\":5,\"codIdentif\":\"390547129375401\",\"dniResponsable\":\"23456389T\",\"activa\":true},"
				+ "\"dniRegistroIngreso\":\"23456389T\""
				+ "}";
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void addIngresoInputExceptionTest() throws Exception {
		// For
		String nuevoIngresoJson = "{"
				+ "    \"idMascota\": 1,"
				+ "    \"dniResponsable\": \"23456389T\""
				+ "}";
		
		when(ingresoService.createIngreso(1L, "23456389T", null)).thenThrow(InputException.class);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ingreso")
				.contentType(MediaType.APPLICATION_JSON)
				.content(nuevoIngresoJson)
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
	public void updateIngresoTest() throws Exception {
		// For
		String ingresoModificationJson = "{"
				+ "    \"estado\": \"FINALIZADO\","
				+ "    \"finIngreso\": \"2024-07-15\""
				+ "}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/ingreso/{idMascota}/{idIngreso}", 1L, 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(ingresoModificationJson);
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
	public void updateIngresoNotFoundTest() throws Exception {
		// For
		String ingresoModificationJson = "{"
				+ "    \"estado\": \"FINALIZADO\","
				+ "    \"finIngreso\": \"2024-07-15\""
				+ "}";
		
		LocalDate fechaFinIngreso = LocalDate.of(2024, 7, 15);
        doThrow(IngresoNotFoundException.class).when(ingresoService).updateIngreso(1L, 35L, "FINALIZADO", fechaFinIngreso);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/ingreso/{idMascota}/{idIngreso}", 1L, 35L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(ingresoModificationJson);
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
	public void updateIngresoInputExceptionTest() throws Exception {
		// For
		String ingresoModificationJson = "{"
				+ "    \"estado\": \"FINALIZADO\""
				+ "}";
        doThrow(InputException.class).when(ingresoService).updateIngreso(1L, 2L, "FINALIZADO", null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/ingreso/{idMascota}/{idIngreso}", 1L, 2L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(ingresoModificationJson);
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
	public void anulaIngresoTest() throws Exception {
		// For
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ingreso/{idIngreso}", 2L);
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
	public void anulaIngresoNotFoundTest() throws Exception {
		// For
        doThrow(IngresoNotFoundException.class).when(ingresoService).anulaIngreso(35L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ingreso/{idIngreso}", 35L);
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
	public void anulaIngresoInputExceptionTest() throws Exception {
		// For
        doThrow(InputException.class).when(ingresoService).anulaIngreso(1L);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ingreso/{idIngreso}", 1L);
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
