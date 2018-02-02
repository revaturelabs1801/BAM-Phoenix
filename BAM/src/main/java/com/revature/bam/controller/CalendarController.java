package com.revature.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bam.bean.Subtopic;
import com.revature.bam.bean.SubtopicStatus;
import com.revature.bam.bean.TopicName;
import com.revature.bam.bean.TopicWeek;
import com.revature.bam.service.SubtopicService;
import com.revature.bam.service.TopicService;

@RestController
@RequestMapping(value = "calendar/")
public class CalendarController {

	private static final String BATCHID = "batchId";
	private static final String PAGENUMBER = "pageNumber";
	private static final String PAGESIZE = "pageSize";
	private static final String SUBTOPICID = "subtopicId";
	private static final String DATE = "date";
	private static final String STATUS = "status";

	@Autowired
	SubtopicService subtopicService;

	@Autowired
	TopicService topicService;

	/**
	 * 
	 * This uses pagination. Will return a list of subtopics depending on what page
	 * and how many per page of subtopics you want. The page number and size is
	 * determined by the parameters.
	 * 
	 * Depending on how the FullCalendar API is setup to take pages of json data,
	 * this method may need to change.
	 * 
	 * @param request
	 *            - Parameters: batchId (int), pageNumber (int), pageSize (int)
	 * @return List<Stubtopic> , HttpStatus.OK (200) if successful, BAD REQUEST (400)
	 *         if missing parameters
	 * 
	 *         Authors: Michael Garza Gary LaMountain
	 * 
	 *         note: It will be better to sort by subtopicDate because it will load
	 *         the most recent subtopics. However, since the subtopics have the
	 *         sames dates, it's causing duplications on the calendar. return
	 *         subtopicService.findByBatchId(batchId, new
	 *         PageRequest(pageNum,pageSiz, Direction.DESC, "subtopicDate"));
	 */
	@GetMapping("subtopicspagination")
	public ResponseEntity<List<Subtopic>> getSubtopicsByBatchPagination(HttpServletRequest request) {
		String batchIdParam = request.getParameter(BATCHID);
		String pageNumParam = request.getParameter(PAGENUMBER);
		String pageSizeParam = request.getParameter(PAGESIZE);
		if (batchIdParam == null || pageNumParam == null || pageSizeParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int batchId = Integer.parseInt(batchIdParam);
		int pageNum = Integer.parseInt(pageNumParam);
		int pageSiz = Integer.parseInt(pageSizeParam);

		return new ResponseEntity<List<Subtopic>>(
				subtopicService.findByBatchId(batchId, new PageRequest(pageNum, pageSiz)), HttpStatus.OK);
	}

	/**
	 * Gets a list of subtopics for a given batch
	 * 
	 * @param request
	 *            -Parameters: batchId (int)
	 * @return List of subtopics for the batch with the given id OK (200) if
	 *         successful, BAD REQUEST (400) if missing parameters
	 */
	@GetMapping("subtopics")
	public ResponseEntity<List<Subtopic>> getSubtopicsByBatch(HttpServletRequest request) {
		String batchIdParam = request.getParameter(BATCHID);
		if (batchIdParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int batchId = Integer.parseInt(batchIdParam);
		
		return new ResponseEntity<List<Subtopic>>(subtopicService.getSubtopicByBatchId(batchId), HttpStatus.OK);
	}

	/**
	 * Counts the number of Subtopics by matching their ids with the batchId.
	 * 
	 * @param request
	 *            -Parameters: batchId (int)
	 * @return number(Long) of Subtopics, OK (200) if successful, BAD REQUEST (400)
	 *         if missing parameters
	 * @author Michael Garza, Gary LaMountain
	 */
	@GetMapping("getnumberofsubtopics")
	public ResponseEntity<Long> getNumberOfSubTopicsByBatch(HttpServletRequest request) {
		String batchIdParam = request.getParameter(BATCHID);
		if (batchIdParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int batchId = Integer.parseInt(batchIdParam);

		return new ResponseEntity<Long>(subtopicService.getNumberOfSubtopics(batchId), HttpStatus.OK);
	}

	/**
	 * Gets a list of topics for a given batch
	 * 
	 * @param request
	 *            - Parameters: - batchId (int)
	 * @return List of topics for the batch with the given id, HttpStatus.OK
	 *         (200) if successful, BAD REQUEST (400) if missing parameters
	 */
	@GetMapping("topics")
	public ResponseEntity<List<TopicWeek>> getTopicsByBatchPag(HttpServletRequest request) {
		String batchIdParam = request.getParameter(BATCHID);
		if (batchIdParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		int batchId = Integer.parseInt(batchIdParam);

		return new ResponseEntity<List<TopicWeek>>(topicService.getTopicByBatchId(batchId), HttpStatus.OK);
	}
	
	
	/**
	 * Updates the date for the given subtopic in the given batch
	 * @param request
	 * 	Parameters: subtopicId (int), batchId (int), date (long/date)
	 * @return OK (200) if update occurs, NO CONTENT (204) if requested batch/subtopic does not exist, BAD REQUEST (400) if missing parameters
	 * @throws ParseException
	 */
	@GetMapping("dateupdate")
	public ResponseEntity<?> changeTopicDate(HttpServletRequest request) throws ParseException {
		String subtopicIdParam = request.getParameter(SUBTOPICID);
		String batchIdParam = request.getParameter(BATCHID);
		String newDateParam = request.getParameter(DATE);
		if (subtopicIdParam == null || batchIdParam == null || newDateParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));
		int batchId = Integer.parseInt(request.getParameter(BATCHID));
		long newDate = Long.valueOf(newDateParam);
		
		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		for(Subtopic sub: topics) {
			if (sub.getSubtopicId() == subtopicId) {
				sub.setSubtopicDate(new Timestamp(newDate + 46800000));
				subtopicService.updateSubtopic(sub);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


	/**
	 * Updates the status of the given subtopic in the given batch
	 * @param request
	 * 	Parameters: subtopicId (int), batchId (int), status (string)
	 * @return
	 * @throws ParseException
	 */
	@GetMapping("statusupdate")
	public ResponseEntity<?> updateTopicStatus(HttpServletRequest request) throws ParseException {
		String subtopicIdParam = request.getParameter(SUBTOPICID);
		String batchIdParam = request.getParameter(BATCHID);
		String statusParam = request.getParameter(STATUS);
		if (subtopicIdParam == null || batchIdParam == null || statusParam == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		int subtopicId = Integer.parseInt(subtopicIdParam);
		int batchId = Integer.parseInt(batchIdParam);
		SubtopicStatus status = subtopicService.getStatus(statusParam);

		List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
		for (Subtopic sub: topics) {
			if (sub.getSubtopicId() == subtopicId) {
				sub.setStatus(status);
				subtopicService.updateSubtopic(sub);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("addtopics")
	public ResponseEntity<?> addTopics(@RequestBody String jsonObject, HttpSession session) {
		List<TopicName> topicsFromStub = null;

		ObjectMapper mapper = new ObjectMapper();
		try {

			topicsFromStub = mapper.readValue(jsonObject,
					mapper.getTypeFactory().constructCollectionType(List.class, TopicName.class));
		} catch (IOException e) {
			LogManager.getRootLogger().error(e);
		}
		List<TopicName> allTopicsInBAM = topicService.getTopics();
		for (int i = 0; i < topicsFromStub.size(); i++) {
			boolean found = false;
			for (int j = 0; j < allTopicsInBAM.size(); j++) {
				if (topicsFromStub.get(i).getName().equals(allTopicsInBAM.get(j).getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				topicService.addOrUpdateTopicName(topicsFromStub.get(i));
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
