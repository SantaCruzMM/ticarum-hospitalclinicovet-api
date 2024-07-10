package com.scmm.hospitalclinicovet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scmm.hospitalclinicovet.modelo.Ingreso;

@Repository
public interface IngresoRepository extends CrudRepository<Ingreso, Long> {
	List<Ingreso> findAll();
	
	@Query(value = "SELECT * FROM INGRESO i WHERE i.FK_MASCOTA_ID = ?1",
			nativeQuery = true)
	List<Ingreso> findByMascota(Long idMascota);
	
	@Query(value = "SELECT * FROM INGRESO I WHERE i.id = ?1 AND i.FK_MASCOTA_ID = ?2",
			nativeQuery = true)
	Optional<Ingreso> findByIdAndMascota(Long idIngreso, Long idMascota);
}
