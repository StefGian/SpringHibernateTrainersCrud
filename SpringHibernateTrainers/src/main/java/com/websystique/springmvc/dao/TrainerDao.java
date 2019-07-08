package com.websystique.springmvc.dao;

import java.util.List;

import com.websystique.springmvc.model.Trainer;

public interface TrainerDao {

	Trainer findById(int id);

	void saveTrainer(Trainer trainer);
	
	void deleteTrainerBySsn(String ssn);
	
	List<Trainer> findAllTrainers();

	Trainer findTrainerBySsn(String ssn);

}
