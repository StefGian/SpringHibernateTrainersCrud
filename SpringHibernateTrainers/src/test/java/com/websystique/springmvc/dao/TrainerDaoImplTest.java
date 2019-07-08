package com.websystique.springmvc.dao;

import java.math.BigDecimal;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.websystique.springmvc.model.Trainer;


public class TrainerDaoImplTest extends EntityDaoImplTest{

	@Autowired
	TrainerDao trainerDao;

	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Trainer.xml"));
		return dataSet;
	}
	
	/* In case you need multiple datasets (mapping different tables) and you do prefer to keep them in separate XML's
	@Override
	protected IDataSet getDataSet() throws Exception {
	  IDataSet[] datasets = new IDataSet[] {
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Trainer.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Benefits.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Departements.xml"))
	  };
	  return new CompositeDataSet(datasets);
	}
	*/

	@Test
	public void findById(){
		Assert.assertNotNull(trainerDao.findById(1));
		Assert.assertNull(trainerDao.findById(3));
	}

	
	@Test
	public void saveTrainer(){
		trainerDao.saveTrainer(getSampleTrainer());
		Assert.assertEquals(trainerDao.findAllTrainers().size(), 3);
	}
	
	@Test
	public void deleteTrainerBySsn(){
		trainerDao.deleteTrainerBySsn("11111");
		Assert.assertEquals(trainerDao.findAllTrainers().size(), 1);
	}
	
	@Test
	public void deleteTrainerByInvalidSsn(){
		trainerDao.deleteTrainerBySsn("23423");
		Assert.assertEquals(trainerDao.findAllTrainers().size(), 2);
	}

	@Test
	public void findAllTrainers(){
		Assert.assertEquals(trainerDao.findAllTrainers().size(), 2);
	}
	
	@Test
	public void findTrainerBySsn(){
		Assert.assertNotNull(trainerDao.findTrainerBySsn("11111"));
		Assert.assertNull(trainerDao.findTrainerBySsn("14545"));
	}

	public Trainer getSampleTrainer(){
		Trainer trainer = new Trainer();
		trainer.setName("Karen");
		trainer.setSsn("12345");
		trainer.setSalary(new BigDecimal(10980));
		trainer.setJoiningDate(new LocalDate());
		return trainer;
	}

}
