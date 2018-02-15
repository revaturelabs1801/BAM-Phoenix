package com.revature.bam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Mohamed Swelam / Batch : 1712_dec11_Java_Steve
 * 
 * This a timed schedule service class to be used for routine services.
 *
 */
@Service
public class TimedSyncService {
	
	
	@Autowired
	AssignForceSyncService service;

	
	/**
	 * @author Mohamed Swelam / Batch : 1712_dec11_Java_Steve
	 * 
	 * To schedule AssignForce Sync Service every hour.
	 * 
	 */
	@Scheduled(fixedRate = 60*60*1000)
	public void scheduleAssignForceSyncService() {
		service.assignForceSync();
	}


}
