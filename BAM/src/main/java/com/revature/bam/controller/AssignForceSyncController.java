package com.revature.bam.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.bam.service.AssignForceSyncService;

@RestController
public class AssignForceSyncController {
 
  @Autowired
  AssignForceSyncService service;

  @RequestMapping(value = "/refreshBatches", method = RequestMethod.GET)
  public void refreshBatches() throws JsonMappingException, IOException {
    service.assignForceSync();
  }

}
