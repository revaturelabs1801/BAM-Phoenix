package com.revature.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.bam.bean.SubtopicName;
import com.revature.bam.bean.SubtopicType;
import com.revature.bam.bean.Subtopic;
import com.revature.bam.bean.TopicName;
import com.revature.bam.service.SubtopicService;
import com.revature.bam.service.TopicService;

@RestController
@RequestMapping("subtopic/")
@CrossOrigin
public class SubTopicController {
	@Autowired
	TopicService topicService;

	@Autowired
	SubtopicService subTopicService;

	
/**
 * @author Cristian Hermida, Charlie Harris / Batch 1712-dec10-java-steve
 * 
 * @param request
 * 			- I request must have to have the name of the topic.
 * @return The added subtopic (if any) as a SubtopicName and HttpStatus
 * 			- status of 201 CREATED if a Subtopic is created or updated.
 * 			- status of 204 NO_CONTENT is a Subtopic is not created.
 */
	@PostMapping("/updatestatus")
	public ResponseEntity<Subtopic> updateSubtopicStatus(@RequestBody Subtopic subtopic) {
		subtopic = subTopicService.updateSubtopicStatus(subtopic);

		if (subtopic != null) {
			return new ResponseEntity<Subtopic>(subtopic, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<Subtopic>(subtopic, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("add/{typeId}/{topicId}/{subtopicName}")
	public ResponseEntity<SubtopicName> addSubTopicName(@PathVariable int typeId, @PathVariable int topicId,
			@PathVariable String subtopicName) {
		SubtopicType type = subTopicService.getSubtopicType(typeId);
		TopicName topic = topicService.getTopicName(topicId);
		SubtopicName subtopic = new SubtopicName(subtopicName, topic, type);
		SubtopicName topicUpdate = subTopicService.addOrUpdateSubtopicName(subtopic);
		if (topicUpdate != null) {
			return new ResponseEntity<SubtopicName>(topicUpdate, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @author Sean Sung / Batch 1712-dec10-java-steve
	 * 
	 * @param post
	 *            - accepts subtopicid parameter
	 * @return HttpStatus - status of 204 if a subtopic was removed. - status of 500
	 *         if exception.
	 */
	@PostMapping("remove/{subtopicId}")
	public ResponseEntity<?> removeSubtopic(@PathVariable int subtopicId) {
		if (subTopicService.removeSubtopicFromBatch(subtopicId)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Checks whether the given batch has any subtopics in its calendar
	 * @author Jordan DeLong, Cristian Hermida, Charlie Harris / Batch 1712-dec10-java-steve
	 * @param batchId
	 * @return HttpStatus - status NO_CONTENT 204 if no subtopics found. - status OK 200 if at least one subtopic exists in batch.
	 */
	@GetMapping("ispopulated/{batchId}")
	public ResponseEntity<?> isPopulated(@PathVariable int batchId) {
		if (subTopicService.findTop1ByBatchId(batchId).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	/**
	 * Removes all subtopics from the calendar of the batch with id [batchId]
	 * @author Jordan DeLong, Cristian Hermida, Charlie Harris / Batch 1712-dec10-java-steve
	 * @param batchId
	 * @return HttpStatus - status NO_CONTENT if successful, status INTERNAL_SERVER_ERROR otherwise
	 */
	@PostMapping("removebybatch/{batchId}")
	public ResponseEntity<?> removeSubtopicByBatch(@PathVariable int batchId) {
		if(subTopicService.removeAllSubtopicsFromBatch(batchId)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
