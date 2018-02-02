package com.revature.bam.controller;

/**
 * 
 */

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.bam.bean.Subtopic;
import com.revature.bam.exception.CustomException;
import com.revature.bam.service.SubtopicService;

@RestController
@RequestMapping(value = "/Subtopic")
public class SubtopicAddController {

  @Autowired
  SubtopicService subserv;

  /**
   * 
   * @param jsonObj
   * @author Samuel Louis-Pierre, Avant Mathur, Tyler Dresselhouse, Daniel Robinson
   * 
   *         REST controller to add existing subtopic to specified batch
   * @throws CustomException
   */

  @RequestMapping(value = "addSubtopic", method = RequestMethod.POST, produces = "application/json")
  public void addSubtopic(@RequestBody String jsonObj) throws CustomException {

    Subtopic st = null;
    try {
      st = new ObjectMapper().readValue(jsonObj, Subtopic.class);
    } catch (IOException e) {
      throw new CustomException(e);
    }

    System.out.println(st);
    subserv.updateSubtopic(st);
  }
  
  @GetMapping("/hello")
  public ResponseEntity<String> helloWorld() {
	  System.out.println("Hello World!");
	  return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

}