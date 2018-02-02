package com.revature.bam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.bean.SubtopicName;
import com.revature.bam.bean.SubtopicType;
import com.revature.bam.bean.TopicName;
import com.revature.bam.service.SubtopicService;
import com.revature.bam.service.TopicService;

@RestController
@RequestMapping(value = "subtopic/")
public class SubTopicController {
	@Autowired
	TopicService topicService;

	@Autowired
	SubtopicService subTopicService;

/**
 * @author Cristian Hermida
 * 
 * @param request
 * 			- I request must have to have the name of the topic.
 * @return HttpStatus
 * 			- status of 201 CREATED if a Subtopic is created or updated.
 * 			- status of 204 NO_CONTENT is a Subtopic is not created.
 */

	@PostMapping("add")
	public ResponseEntity<?> addSubTopicName(HttpServletRequest request) {
		SubtopicType type = subTopicService.getSubtopicType(Integer.parseInt(request.getParameter("typeId")));
		TopicName topic = topicService.getTopicName(Integer.parseInt(request.getParameter("topicId")));
		SubtopicName subtopic = new SubtopicName(request.getParameter("subtopicName"), topic, type);
		SubtopicName topicUpdate = subTopicService.addOrUpdateSubtopicName(subtopic);
		if (topicUpdate != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

}
