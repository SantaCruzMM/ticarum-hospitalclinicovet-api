package com.scmm.hospitalclinicovet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scmm.hospitalclinicovet.modelo.Ingreso;

@Repository
public interface IngresoRepository extends CrudRepository<Ingreso, Long> {

}
