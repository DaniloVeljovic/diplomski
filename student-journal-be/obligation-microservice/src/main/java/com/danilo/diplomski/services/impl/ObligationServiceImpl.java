package com.danilo.diplomski.services.impl;

import org.springframework.stereotype.Service;

import com.danilo.diplomski.DTO.ObligationDTO;
import com.danilo.diplomski.models.Obligation;
import com.danilo.diplomski.services.ObligationService;

@Service
public class ObligationServiceImpl implements ObligationService {

	@Override
	public Obligation createObligation(ObligationDTO newObligation) {
		Obligation addedObligation = new Obligation(newObligation.getDate(), newObligation.getDescription(), newObligation.getType());
		return addedObligation;
	}

}
