package com.revature.bam.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	 * Populates batch using a list of curriculum subtopics.
	 * 
	 * NOTE: This method assumes all batches start on a Monday.
	 * 
	 * @author DillonT
	 * @param currSubtopics
	 * @param batch
	 */
	public void addCurriculumSubtopicsToBatch(List<CurriculumSubtopic> currSubtopics, Batch batch){
		System.out.println("you made it to addCurriculumSubtopicsToBatch");
		Calendar cal = Calendar.getInstance();
		
	
		
		int prevSubtopicIndex = 0;
		int currentSubtopicIndex = 0;
		
		int defaultStartTimeHour= 9;
		int currentStartTimeHour= 9;
		
		for(CurriculumSubtopic cSTopic: currSubtopics){
			Subtopic sub = new Subtopic();
			
			//set name and batch using given information
			sub.setBatch(batch);
			sub.setSubtopicName((subtopicNameRepository.findById(cSTopic.getCurriculumSubtopicNameId().getId())));
			sub.setStatus(subtopicService.getStatus("Pending"));
			
			//Get the absolute day of batch that the subtopic should be added to
			int sDay = cSTopic.getCurriculumSubtopicDay();
			int sWeek = cSTopic.getCurriculumSubtopicWeek();
			int absoluteDayOfBatch = (sWeek-1)*7 + sDay - 1;
			//System.out.println(absoluteDayOfBatch);
			//Set the subtopics date using the batches start date and the 
			//previously derived absolute day of batch.
			//System.out.println(batch.getStartDate());
			cal.setTime(batch.getStartDate());
			cal.add(Calendar.DAY_OF_WEEK, absoluteDayOfBatch);
			
			if(currentSubtopicIndex == 0)
			{
				cal.set(Calendar.HOUR_OF_DAY, defaultStartTimeHour);
			}
			else
			{
				CurriculumSubtopic prev = currSubtopics.get(prevSubtopicIndex);
				CurriculumSubtopic current = currSubtopics.get(currentSubtopicIndex);
				//System.out.println("prev: " + prev.getCurriculumSubtopicWeek() + " " + prev.getCurriculumSubtopicDay());
				//System.out.println("curr: " + current.getCurriculumSubtopicWeek() + " " + current.getCurriculumSubtopicDay());
				if(prev.getCurriculumSubtopicWeek() == current.getCurriculumSubtopicWeek() && prev.getCurriculumSubtopicDay() == current.getCurriculumSubtopicDay()){
					currentStartTimeHour = (currentStartTimeHour + 1) % 22;
					cal.set(Calendar.HOUR_OF_DAY, currentStartTimeHour);
				}
				else
				{
					currentStartTimeHour = defaultStartTimeHour;
					cal.set(Calendar.HOUR_OF_DAY, defaultStartTimeHour);
				}
			}
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			Timestamp t = new Timestamp(cal.getTime().getTime());
			//System.out.println(new Date(t.getTime()));
			sub.setSubtopicDate(t);
			
			prevSubtopicIndex = currentSubtopicIndex;
			currentSubtopicIndex++;
			//System.out.println(sub.getSubtopicDate());
			//subtopicRepository.save(sub);
		}
		
	}
}
