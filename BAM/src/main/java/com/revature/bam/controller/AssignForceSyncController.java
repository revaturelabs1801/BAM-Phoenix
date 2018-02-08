package com.revature.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.service.AssignForceSyncService;

@RestController
@CrossOrigin
public class AssignForceSyncController {
 
  @Autowired
  AssignForceSyncService service;

  /**
   * Syncs batch information from assignforce
   * @return RESET CONTENT (205)
   */
  @GetMapping("/refreshbatches")
  public ResponseEntity<?> refreshBatches() {
	  service.assignForceSync();
	  return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
  }

}
