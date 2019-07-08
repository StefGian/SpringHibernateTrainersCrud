package com.websystique.springmvc.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.atLeastOnce;

import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.websystique.springmvc.model.Trainer;
import com.websystique.springmvc.service.TrainerService;

public class AppControllerTest {

	@Mock
	TrainerService service;
	
	@Mock
	MessageSource message;
	
	@InjectMocks
	AppController appController;
	
	@Spy
	List<Trainer> trainers = new ArrayList<Trainer>();

	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		trainers = getTrainerList();
	}
	
	@Test
	public void listTrainers(){
		when(service.findAllTrainers()).thenReturn(trainers);
		Assert.assertEquals(appController.listTrainers(model), "alltrainers");
		Assert.assertEquals(model.get("trainers"), trainers);
		verify(service, atLeastOnce()).findAllTrainers();
	}
	
	@Test
	public void newTrainer(){
		Assert.assertEquals(appController.newTrainer(model), "registration");
		Assert.assertNotNull(model.get("trainer"));
		Assert.assertFalse((Boolean)model.get("edit"));
		Assert.assertEquals(((Trainer)model.get("trainer")).getId(), 0);
	}


	@Test
	public void saveTrainerWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).saveTrainer(any(Trainer.class));
		Assert.assertEquals(appController.saveTrainer(trainers.get(0), result, model), "registration");
	}

	@Test
	public void saveTrainerWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isTrainerSsnUnique(anyInt(), anyString())).thenReturn(false);
		Assert.assertEquals(appController.saveTrainer(trainers.get(0), result, model), "registration");
	}

	
	@Test
	public void saveTrainerWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isTrainerSsnUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(service).saveTrainer(any(Trainer.class));
		Assert.assertEquals(appController.saveTrainer(trainers.get(0), result, model), "success");
		Assert.assertEquals(model.get("success"), "Trainer Axel registered successfully");
	}

	@Test
	public void editTrainer(){
		Trainer emp = trainers.get(0);
		when(service.findTrainerBySsn(anyString())).thenReturn(emp);
		Assert.assertEquals(appController.editTrainer(anyString(), model), "registration");
		Assert.assertNotNull(model.get("trainer"));
		Assert.assertTrue((Boolean)model.get("edit"));
		Assert.assertEquals(((Trainer)model.get("trainer")).getId(), 1);
	}

	@Test
	public void updateTrainerWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).updateTrainer(any(Trainer.class));
		Assert.assertEquals(appController.updateTrainer(trainers.get(0), result, model,""), "registration");
	}

	@Test
	public void updateTrainerWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isTrainerSsnUnique(anyInt(), anyString())).thenReturn(false);
		Assert.assertEquals(appController.updateTrainer(trainers.get(0), result, model,""), "registration");
	}

	@Test
	public void updateTrainerWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		when(service.isTrainerSsnUnique(anyInt(), anyString())).thenReturn(true);
		doNothing().when(service).updateTrainer(any(Trainer.class));
		Assert.assertEquals(appController.updateTrainer(trainers.get(0), result, model, ""), "success");
		Assert.assertEquals(model.get("success"), "Trainer Axel updated successfully");
	}
	
	
	@Test
	public void deleteTrainer(){
		doNothing().when(service).deleteTrainerBySsn(anyString());
		Assert.assertEquals(appController.deleteTrainer("123"), "redirect:/list");
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
