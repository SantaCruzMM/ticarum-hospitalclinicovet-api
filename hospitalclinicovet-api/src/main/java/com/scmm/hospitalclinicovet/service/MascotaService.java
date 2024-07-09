package com.scmm.hospitalclinicovet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scmm.hospitalclinicovet.repository.MascotaRepository;

@Service
public class MascotaService  implements IMascotaService {

	@Autowired
	private MascotaRepository mascotaRepository;
}
