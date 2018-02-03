package com.revature.bam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bam.bean.Batch;
import com.revature.bam.bean.Curriculum;
import com.revature.bam.bean.CurriculumSubtopic;
import com.revature.bam.bean.Subtopic;
import com.revature.bam.bean.SubtopicName;
import com.revature.bam.dto.CurriculumSubtopicDTO;
import com.revature.bam.dto.DaysDTO;
import com.revature.bam.exception.CustomException;
import com.revature.bam.service.BatchService;
import com.revature.bam.service.CurriculumService;
import com.revature.bam.service.CurriculumSubtopicService;
import com.revature.bam.service.SubtopicService;

@RestController
@RequestMapping(value = "curriculum/")
public class CurriculumController {

	@Autowired
	CurriculumService curriculumService;
	
	@Autowired
	CurriculumSubtopicService curriculumSubtopicService;
	
	@Autowired
	SubtopicService subtopicService;
	
	@Autowired 
	BatchService batchService;

	public CurriculumService get(){
		return curriculumService;
	}
	
	/***
	 * @author Nam Mai
	 * Method is needed for injecting mocked services for unit test
	 */
	@Autowired
	public CurriculumController(CurriculumService cs, CurriculumSubtopicService css, SubtopicService ss){
		curriculumService = cs;
		curriculumSubtopicService =css;
		subtopicService = ss;
	}
	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * getAllCurriculum: method to get all curriculums
	 * @return List<Curriculum>, HttpStatus.OK if successful,
	 * 		HttpStatus.NO_CONTENT if list is empty 
	 */
	@GetMapping(value = "all")
	@ResponseBody
	public ResponseEntity<List<Curriculum>> getAllCurriculum(){
		List<Curriculum> result = curriculumService.getAllCurriculum();
		if(result != null && !result.isEmpty()) {
			return new ResponseEntity<List<Curriculum>>(result, HttpStatus.OK);
		} 
		return new ResponseEntity<List<Curriculum>>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * getCurriculumById: method to get a Curriculum by its Id
	 * @return Curriculum, HttpStatus.OK if successful
	 * 		HttpStatus.NO_CONTENT if id doesn't match, 
	 * 		HttpStatus.BAD_REQUEST if missing parameters
	 */
	@GetMapping(value = "getcurriculum")
	@ResponseBody
	public ResponseEntity<Curriculum> getCurriculumById(HttpServletRequest request){
		String paramId = request.getParameter("curriculumId");
		if(paramId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int curriculumId = Integer.parseInt(paramId);
		Curriculum result = curriculumService.getCuricullumById(curriculumId);
		
		if(result != null) {
			return new ResponseEntity<Curriculum>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * @param request HttpServletRequest that holds curriculumId as parameter
	 * getAllCurriculumSchedules: method to retrieve list of curriculum subtopics
	 * 		given a curriculumId
	 * @return List<CurriculumSubtopics>, HttpStatus.OK if successful
	 * 		HttpStatus.NO_CONTENT if id doesn't match, 
	 * 		HttpStatus.BAD_REQUEST if missing parameters
	 */
	@GetMapping(value = "schedule")
	@ResponseBody
	public ResponseEntity<List<CurriculumSubtopic>> getAllCurriculumSchedules(HttpServletRequest request){
		String paramId = request.getParameter("curriculumId");
		if(paramId == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int curriculumId = Integer.parseInt(paramId);
		Curriculum c = new Curriculum();
		c.setCurriculumId(curriculumId);
		
		List<CurriculumSubtopic> result = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<List<CurriculumSubtopic>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * getTopicPool: method to get list of topics
	 * @return List<SubtopicName>, HttpStatus.OK if successful,
	 * 		HttpStatus.NO_CONTENT if list is empty 
	 */
	@GetMapping(value = "topicpool")
	@ResponseBody
	public ResponseEntity<List<SubtopicName>> getTopicPool(){
		List<SubtopicName> result = subtopicService.getAllSubtopics();
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<List<SubtopicName>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * @author Carter Taylor (1712-Steve), Olayinka Ewumi (1712-Steve)
	 * getSubtopicPool: method to get list of subtopics with associated
	 * 		batch and status
	 * @return List<Subtopic>, HttpStatus.OK if successful,
	 * 		HttpStatus.NO_CONTENT if list is empty 
	 */
	@GetMapping(value = "subtopicpool")
	@ResponseBody
	public	ResponseEntity<List<Subtopic>> getSubtopicPool(){
		List<Subtopic> result =  subtopicService.getSubtopics();
		if (result != null && !result.isEmpty()) {
			return new ResponseEntity<List<Subtopic>>(result, HttpStatus.OK);
		}
		return new ResponseEntity<List<Subtopic>>(HttpStatus.NO_CONTENT);
	}

	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param json String that contains curriculum subtopic object 
	 * addSchedule: method that takes a curriculum subtopic (schedule) as input
	 * 		from request body and saves both curriculum and curriculum subtopic
	 * @return HttpStatus.CREATED if successful
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@PostMapping(value = "addcurriculum")
	public ResponseEntity<?> addSchedule(@RequestBody String json) throws JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		CurriculumSubtopicDTO c = mapper.readValue(json, CurriculumSubtopicDTO.class);
		
		//save curriculum object first

		Curriculum curriculum = new Curriculum();
		curriculum.setCurriculumCreator(c.getMeta().getCurriculum().getCurriculumCreator());
		curriculum.setCurriculumdateCreated(c.getMeta().getCurriculum().getCurriculumdateCreated());
		curriculum.setCurriculumName(c.getMeta().getCurriculum().getCurriculumName());
		curriculum.setCurriculumNumberOfWeeks(c.getMeta().getCurriculum().getCurriculumNumberOfWeeks());
		curriculum.setCurriculumVersion(c.getMeta().getCurriculum().getCurriculumVersion());
		curriculum.setIsMaster(c.getMeta().getCurriculum().getIsMaster());

		curriculumService.save(curriculum);
		

		int numWeeks = c.getWeeks().length;
		for(int i = 0; i < numWeeks; i++){
			DaysDTO[] days = c.getWeeks()[i].getDays();
			for(int j = 0; j < days.length; j++){
				SubtopicName[] subtopic = days[j].getSubtopics();
				for(int k = 0; k < subtopic.length; k++){
					CurriculumSubtopic cs = new CurriculumSubtopic();
					cs.setCurriculumSubtopicCurriculumID(curriculum);
					cs.setCurriculumSubtopicNameId(subtopic[k]);
					cs.setCurriculumSubtopicWeek(i + 1);
					cs.setCurriculumSubtopicDay(j + 1);
					curriculumSubtopicService.saveCurriculumSubtopic(cs);
				}
			}
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param HttpServletRequest that holds curriculumId as parameter
	 * markCurricullumAsMaster: marks selected curriculum as master version 
	 * 		(identified by id sent as request parameter), and sets previous 
	 * 		master version to non-master status.
	 * @return HttpStatus.BAD_REQUEST if missing parameter,
	 * 		HttpStatus.ACCEPTED if successful
	 */
	@GetMapping(value = "makemaster")
	public ResponseEntity<?> markCurriculumAsMaster(HttpServletRequest request){
		String paramId = request.getParameter("curriculumId");
		if(paramId == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		int currId = Integer.parseInt(paramId);
		
		Curriculum c = curriculumService.getCuricullumById(currId);
		c.setIsMaster(1);
		
		//find the curriculum with same name and isMaster = 1; set to 0; save
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(c.getCurriculumName());
		
	    try {
	        Curriculum prevMaster = null;

	        for (int i = 0; i < curriculumList.size(); i++) {
	          if (curriculumList.get(i).getIsMaster() == 1)
	            prevMaster = curriculumList.get(i);
	        }
	        if (prevMaster != null) {
	          prevMaster.setIsMaster(0);
	          curriculumService.save(prevMaster);
	        } else {
	          LogManager.getRootLogger().error(prevMaster);
	        }
	      } catch (NullPointerException e) {
	        LogManager.getRootLogger().error(e);
	      }
		
		//save new master curriculum
		curriculumService.save(c);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param id - batch id given as path variable
	 * syncBatch: sync batch by getting list of curriculum subtopics
	 * 		related to that batch type
	 * @return HttpStatus.RESET_CONTENT if successful, 
	 * 		HttpStatus.NO_CONTENT if already synced
	 * @throws CustomException
	 */
	@GetMapping(value = "syncbatch/{id}")
	public ResponseEntity<?> syncBatch(@PathVariable int id) throws CustomException{
		Batch currBatch = batchService.getBatchById(id);
		String batchType = currBatch.getType().getName();
		
		//get curriculums with same batchTypes
		List<Curriculum> curriculumList = curriculumService.findAllCurriculumByName(batchType);
		
		//find the master curriculum; otherwise find one with most up to date version
		Curriculum c = null;
		for(int i = 0;  i < curriculumList.size(); i++){
			//master version found
			if(curriculumList.get(i).getIsMaster() == 1)
				c = curriculumList.get(i);
		}
		
		//if master not found, get latest version
		if(c == null){
			int min = curriculumList.get(0).getCurriculumVersion();
			Curriculum tempCurric = curriculumList.get(0);
			for(int i = 1; i < curriculumList.size(); i++){
				if(curriculumList.get(i).getCurriculumVersion() > min){
					min = curriculumList.get(i).getCurriculumVersion();
					tempCurric = curriculumList.get(i);
				}
			}
			c = tempCurric;
		}
		
		//get all curriculumSubtopics associated with curriculum
		List<CurriculumSubtopic> subtopicList = curriculumSubtopicService.getCurriculumSubtopicForCurriculum(c);
		
		//logic goes here to add to calendar
		if(subtopicService.getNumberOfSubtopics(id) ==  0){
			batchService.addCurriculumSubtopicsToBatch(subtopicList, currBatch);
		}else{
			//throw new CustomException("Batch already synced");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
	}
	
}
