package com.danilo.diplomski.services;

import com.danilo.diplomski.DTO.ObligationDTO;
import com.danilo.diplomski.models.Obligation;

public interface ObligationService {

	Obligation createObligation(ObligationDTO userSentObligation);
}
