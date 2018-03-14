package com.revature.bam.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bam.bean.BamUser;
import com.revature.bam.bean.Batch;
import com.revature.bam.bean.BatchType;
import com.revature.bam.bean.CurriculumSubtopic;
import com.revature.bam.bean.Subtopic;
import com.revature.bam.repository.BatchRepository;
import com.revature.bam.repository.BatchTypeRepository;
import com.revature.bam.repository.SubtopicNameRepository;
import com.revature.bam.repository.SubtopicRepository;

@Service
public class BatchService {
	@Autowired
	BatchRepository batchRepository;

	@Autowired
	BatchTypeRepository batchTypeRepository;
	
	@Autowired
	SubtopicRepository subtopicRepository;
	
	@Autowired
	SubtopicNameRepository subtopicNameRepository;
	
	@Autowired
	SubtopicService subtopicService;
	
	public Batch addOrUpdateBatch(Batch b) {
		return batchRepository.save(b);
	}

	public Batch getBatchById(Integer id) {
		LogManager.getLogger(BatchService.class).fatal(batchRepository);
		return batchRepository.findById(id);
	}

	public List<Batch> getBatchAll() {
		return batchRepository.findAll();
	}

	public List<Batch> getBatchByTrainer(BamUser trainer) {
		return batchRepository.findByTrainer(trainer);
	}
	
	public List<BatchType> getAllBatchTypes() {
		return batchTypeRepository.findAll();
	}
	
	
	/**
	 * Method to get all currently active batches
	 * @author Francisco Palomino | Batch: 1712-dec10-java-steve
	 * @param end Current date
	 * @param start Current date
	 * @return a list of batches, Http status 200 otherwise Http status 204
	 */
	public List<Batch> currentBatches(Timestamp end, Timestamp start) {
		return batchRepository.findByEndDateGreaterThanAndStartDateLessThan(end, start);
	}
	
}
