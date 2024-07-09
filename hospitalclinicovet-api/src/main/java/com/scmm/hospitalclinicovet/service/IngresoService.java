package com.scmm.hospitalclinicovet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.repository.IngresoRepository;

@Service
public class IngresoService implements IIngresoService {

	@Autowired
	private IngresoRepository ingresoRepository;
}
