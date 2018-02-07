package com.revature.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.bean.Subtopic;
import com.revature.bam.exception.CustomException;
import com.revature.bam.service.SubtopicService;

@RestController
@RequestMapping("/subtopic")
@CrossOrigin
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

  @PostMapping("addsubtopic")
  public ResponseEntity<?> addSubtopic(@RequestBody Subtopic st) /*throws CustomException */{

//   st = null;
//    try {
//      st = new ObjectMapper().readValue(st, Subtopic.class);
//    } catch (IOException e) {
//      throw new CustomException(e);
//    }

    subserv.updateSubtopic(st);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}