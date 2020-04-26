package com.danilo.diplomski.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.Obligation;

@Transactional
public interface ObligationRepository extends CrudRepository<Obligation, Integer> {

}
