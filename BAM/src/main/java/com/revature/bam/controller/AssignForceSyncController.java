package com.revature.bam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.service.AssignForceSyncService;

@RestController
public class AssignForceSyncController {

	@Autowired
	AssignForceSyncService service;
	
	
	@GetMapping("/refreshBatches")
	public ResponseEntity<?> refreshBatches() {
		service.assignForceSync();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
