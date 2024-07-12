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
public class IngresoServiceTest {
	@Mock
	private MascotaRepository mascotaRepositoryMock;
	
	@Mock
	private IngresoRepository ingresoRepositoryMock;
	
	@InjectMocks
	private IngresoService ingresoService;
	
	private final Mascota mascotaExample = new Mascota(1L, "perro", "bulldog", 5, "390547129375401", "23456389T", true);
	private final List<Ingreso> ingresosExample = Arrays.asList(
			new Ingreso(1L, LocalDate.of(2022, 4, 27), LocalDate.of(2022, 5, 3),
					EstadoIngreso.FINALIZADO, mascotaExample, "23456389T"),
			new Ingreso(2L, LocalDate.of(2024, 7, 9), null,
					EstadoIngreso.ALTA, mascotaExample, "23456389T")
        );
	private final List<Ingreso> ingresosFinalizadosExample = Arrays.asList(
			new Ingreso(1L, LocalDate.of(2022, 4, 27), LocalDate.of(2022, 5, 3),
					EstadoIngreso.FINALIZADO, mascotaExample, "23456389T"),
			new Ingreso(2L, LocalDate.of(2024, 7, 9), LocalDate.of(2024, 7, 10),
					EstadoIngreso.FINALIZADO, mascotaExample, "23456389T")
        );
	
	@Test
	public void getAllIngresosTest() {
		when(ingresoRepositoryMock.findAll())
			.thenReturn(ingresosExample);
		
		List<Ingreso> ingresos = ingresoService.getAllIngresos();
		
		verify(ingresoRepositoryMock, times(1))
			.findAll();
		assertEquals(ingresosExample, ingresos);
	}
	
	@Test
	public void createIngresoTest() {
		when(mascotaRepositoryMock.findById(1L))
			.thenReturn(Optional.of(mascotaExample));
		when(ingresoRepositoryMock.findByMascota(1L))
			.thenReturn(ingresosFinalizadosExample);
		when(ingresoRepositoryMock.save(any(Ingreso.class)))
			.thenAnswer(invocation -> {
	            Ingreso ingreso = invocation.getArgument(0);
	            ingreso.setId(3L);
	            return ingreso;
	        });
		
		LocalDate fechaIngreso = LocalDate.of(2024, 7, 11);
		Ingreso ingresoExpected = new Ingreso(3L, fechaIngreso, null,
				EstadoIngreso.ALTA, mascotaExample, "23456389T");
		
		Ingreso ingresoOutput = ingresoService.createIngreso(1L, "23456389T", fechaIngreso);
		
		verify(mascotaRepositoryMock, times(1))
		.findById(1L);
		verify(ingresoRepositoryMock, times(1))
		.findByMascota(1L);
		verify(ingresoRepositoryMock, times(1))
		.save(any(Ingreso.class));
		assertEquals(ingresoExpected, ingresoOutput);
	}
	
	@Test
	public void updateIngresoTest() {
		when(ingresoRepositoryMock.findByIdAndMascota(2L, 1L))
			.thenReturn(Optional.of(ingresosExample.get(1)));
		when(ingresoRepositoryMock.save(any(Ingreso.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));
		
		ingresoService.updateIngreso(1L, 2L, "FINALIZADO", LocalDate.of(2024, 7, 12));
		
		verify(ingresoRepositoryMock, times(1))
			.findByIdAndMascota(2L, 1L);
		verify(ingresoRepositoryMock, times(1))
			.save(any(Ingreso.class));
	}
	
	@Test
	public void anulaIngresoTest() {
		when(ingresoRepositoryMock.findById(2L))
			.thenReturn(Optional.of(ingresosExample.get(1)));
		when(ingresoRepositoryMock.save(any(Ingreso.class)))
			.thenAnswer(invocation -> invocation.getArgument(0));
		
		ingresoService.anulaIngreso(2L);
		
		verify(ingresoRepositoryMock, times(1))
			.findById(2L);
		verify(ingresoRepositoryMock, times(1))
			.save(any(Ingreso.class));
	}
}
