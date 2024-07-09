package com.scmm.hospitalclinicovet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scmm.hospitalclinicovet.modelo.Mascota;

@Repository
public interface MascotaRepository extends CrudRepository<Mascota, Long> {

}
