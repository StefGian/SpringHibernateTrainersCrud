package com.websystique.springmvc.service;

import java.util.List;

import com.websystique.springmvc.model.Trainer;

public interface TrainerService {

	Trainer findById(int id);
	
	void saveTrainer(Trainer trainer);
	
	void updateTrainer(Trainer trainer);
	
	void deleteTrainerBySsn(String ssn);

	List<Trainer> findAllTrainers(); 
	
	Trainer findTrainerBySsn(String ssn);

	boolean isTrainerSsnUnique(Integer id, String ssn);
	
}
