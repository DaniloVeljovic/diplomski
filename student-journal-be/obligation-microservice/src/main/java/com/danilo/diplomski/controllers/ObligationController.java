package com.danilo.diplomski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.diplomski.DTO.ObligationDTO;
import com.danilo.diplomski.models.Obligation;
import com.danilo.diplomski.repositories.ObligationRepository;
import com.danilo.diplomski.services.ObligationService;

@RestController
@RequestMapping(path="/obligations")
public class ObligationController {

	@Autowired
	private ObligationRepository obligationRepo;
	@Autowired 
	private ObligationService obligationService;
	//C
	
	//create an obligation for a course
	@PostMapping
	public ResponseEntity<Obligation> createObligation(@RequestBody ObligationDTO newObligation)
	{
		
		Obligation addedObligation = obligationService.createObligation(newObligation);
		
		obligationRepo.save(addedObligation);
		
		return new ResponseEntity<Obligation>(addedObligation, HttpStatus.CREATED);
	}
	
	//R
	
	//read all obligations for a user for a specific date
	@GetMapping(path="/{usedId}/date/{date}")
	public ResponseEntity<Iterable<Obligation>> getAllObligationsForAUser()
	{
		
		
		return null;
	}
	
	//read an obligations for a user for a specific date
	
	
	//U
	
	//update an obligations description
	
	//D
	
	//delete an obligation
}
