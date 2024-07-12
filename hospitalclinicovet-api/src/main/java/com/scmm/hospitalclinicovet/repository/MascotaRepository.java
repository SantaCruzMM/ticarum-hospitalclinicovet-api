package com.scmm.hospitalclinicovet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scmm.hospitalclinicovet.modelo.Mascota;

// Interfaz para repositorio CRUD de mascotas
@Repository
public interface MascotaRepository extends CrudRepository<Mascota, Long> {

}
