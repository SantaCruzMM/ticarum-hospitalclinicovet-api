package com.scmm.hospitalclinicovet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.scmm.hospitalclinicovet.modelo.EstadoIngreso;
import com.scmm.hospitalclinicovet.modelo.Ingreso;
import com.scmm.hospitalclinicovet.modelo.Mascota;
import com.scmm.hospitalclinicovet.repository.IngresoRepository;
import com.scmm.hospitalclinicovet.repository.MascotaRepository;

@ExtendWith(MockitoExtension.class)
public class MascotaServiceTest {
	@Mock
	private MascotaRepository mascotaRepositoryMock;
	
	@Mock
	private IngresoRepository ingresoRepositoryMock;
	
	@InjectMocks
	private MascotaService mascotaService;
	
	private final Mascota mascotaExample = new Mascota(1L, "perro", "bulldog", 5, "390547129375401", "23456389T", true);
	private final List<Ingreso> ingresosMascotaExample = Arrays.asList(
			new Ingreso(1L, LocalDate.of(2022, 4, 27), LocalDate.of(2022, 5, 3),
					EstadoIngreso.FINALIZADO, mascotaExample, "23456389T"),
			new Ingreso(2L, LocalDate.of(2024, 7, 9), null,
					EstadoIngreso.ALTA, mascotaExample, "23456389T")
        );
	
	@Test
	public void getMascotaByIdTest() {
		when(mascotaRepositoryMock.findById(1L))
			.thenReturn(Optional.of(mascotaExample));


        Mascota mascota = mascotaService.getMascotaById(1L);

        verify(mascotaRepositoryMock, times(1))
        	.findById(1L);
        assertEquals(mascotaExample, mascota);
	}
	
	@Test
	public void getIngresosMascotaTest() {
		when(mascotaRepositoryMock.findById(1L))
			.thenReturn(Optional.of(mascotaExample));
		when(ingresoRepositoryMock.findByMascota(1L))
			.thenReturn(ingresosMascotaExample);
		
		List<Ingreso> ingresosMascota = mascotaService.getIngresosMascota(1L);
		
		verify(mascotaRepositoryMock, times(1))
			.findById(1L);
		verify(ingresoRepositoryMock, times(1))
			.findByMascota(1L);
		assertEquals(ingresosMascotaExample, ingresosMascota);
	}
	
	@Test
	public void createMascotaTest() {
		String especie = "perro";
		String raza = "bulldog";
		int edad = 5;
		String codIdentif= "390547129375401";
		String dniResponsable = "23456389T";
		
		when(mascotaRepositoryMock.save(any(Mascota.class)))
			.thenReturn(mascotaExample);
		
		Mascota mascotaOutput = mascotaService.createMascota(especie, raza, edad, codIdentif, dniResponsable);
		
		verify(mascotaRepositoryMock, times(1))
			.save(any(Mascota.class));
		assertEquals(mascotaExample, mascotaOutput);
	}
	
	@Test
	public void bajaMascotaTest() {
		when(mascotaRepositoryMock.findById(1L))
			.thenReturn(Optional.of(mascotaExample));
		when(mascotaRepositoryMock.save(any(Mascota.class)))
			.thenReturn(mascotaExample);
		when(ingresoRepositoryMock.findByMascota(1L))
		.thenReturn(ingresosMascotaExample);
		when(ingresoRepositoryMock.save(any(Ingreso.class)))
			.thenAnswer(invocation -> {
	            Ingreso ingreso = invocation.getArgument(0);
	            if (ingreso.getEstado().equals(EstadoIngreso.ALTA)
	            		|| ingreso.getEstado().equals(EstadoIngreso.HOSPITALIZACION)) {
	            	ingreso.setEstado(EstadoIngreso.ANULADO);
				}
	            return ingreso;
	        });
		
		mascotaService.bajaMascota(1L);
		
		verify(mascotaRepositoryMock, times(2))
			.findById(1L);
		verify(mascotaRepositoryMock, times(1))
			.save(any(Mascota.class));
		verify(ingresoRepositoryMock, times(1))
			.findByMascota(1L);
		verify(ingresoRepositoryMock, times(1))
			.save(any(Ingreso.class));
	}
}
