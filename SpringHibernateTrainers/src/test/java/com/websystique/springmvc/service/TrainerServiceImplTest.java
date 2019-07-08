package com.websystique.springmvc.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import org.joda.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.websystique.springmvc.model.Trainer;
import com.websystique.springmvc.dao.TrainerDao;

public class TrainerServiceImplTest {

	@Mock
	TrainerDao dao;
	
	@InjectMocks
	TrainerServiceImpl trainerService;
	
	@Spy
	List<Trainer> trainers = new ArrayList<Trainer>();
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		trainers = getTrainerList();
	}

	@Test
	public void findById(){
		Trainer emp = trainers.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		Assert.assertEquals(trainerService.findById(emp.getId()),emp);
	}

	@Test
	public void saveTrainer(){
		doNothing().when(dao).saveTrainer(any(Trainer.class));
		trainerService.saveTrainer(any(Trainer.class));
		verify(dao, atLeastOnce()).saveTrainer(any(Trainer.class));
	}
	
	@Test
	public void updateTrainer(){
		Trainer emp = trainers.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		trainerService.updateTrainer(emp);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deleteTrainerBySsn(){
		doNothing().when(dao).deleteTrainerBySsn(anyString());
		trainerService.deleteTrainerBySsn(anyString());
		verify(dao, atLeastOnce()).deleteTrainerBySsn(anyString());
	}
	
	@Test
	public void findAllTrainers(){
		when(dao.findAllTrainers()).thenReturn(trainers);
		Assert.assertEquals(trainerService.findAllTrainers(), trainers);
	}
	
	@Test
	public void findTrainerBySsn(){
		Trainer emp = trainers.get(0);
		when(dao.findTrainerBySsn(anyString())).thenReturn(emp);
		Assert.assertEquals(trainerService.findTrainerBySsn(anyString()), emp);
	}

	@Test
	public void isTrainerSsnUnique(){
		Trainer emp = trainers.get(0);
		when(dao.findTrainerBySsn(anyString())).thenReturn(emp);
		Assert.assertEquals(trainerService.isTrainerSsnUnique(emp.getId(), emp.getSsn()), true);
	}
	
	
	public List<Trainer> getTrainerList(){
		Trainer t1 = new Trainer();
		t1.setId(1);
		t1.setName("Axel");
		t1.setJoiningDate(new LocalDate());
		t1.setSalary(new BigDecimal(10000));
		t1.setSsn("XXX111");
		
		Trainer t2 = new Trainer();
		t2.setId(2);
		t2.setName("Jeremy");
		t2.setJoiningDate(new LocalDate());
		t2.setSalary(new BigDecimal(20000));
		t2.setSsn("XXX222");
		
		trainers.add(t1);
		trainers.add(t2);
		return trainers;
	}
	
}
