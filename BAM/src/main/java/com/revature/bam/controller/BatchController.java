package com.revature.bam.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.bean.Batch;
import com.revature.bam.bean.BatchType;
import com.revature.bam.service.BamUserService;
import com.revature.bam.service.BatchService;
 
/**
 * @author Mohamed Swelam / Batch : 1712_dec11_Java_Steve
 * 
 * Controller class for all batch requests end points.
 *
 */
@RestController
@RequestMapping(value = "batches/")
public class BatchController {

  private static final String EMAIL = "email";

  @Autowired
  BatchService batchService;

  @Autowired
  BamUserService bamUserService;


 /**
 *  A method to get all batches using BatchService.
 * 
 * @return a list of all batches, Http status 200 otherwise Http status 204
 */
@GetMapping("all")
  @ResponseBody 
  public ResponseEntity<List<Batch>> getBatchAll() {
	  List<Batch> result = batchService.getBatchAll();
		if(result != null && !result.isEmpty()) {
			return new ResponseEntity<List<Batch>>(result, HttpStatus.OK);
		}
	  return new ResponseEntity<List<Batch>>(HttpStatus.NO_CONTENT);
  }

  /**
 * A method to get all past batches for the trainer using BatchService.
 * 
 * @param request Http request hold the trainer email as parameter.
 * @return a list of all past batches for the trainer, Http status 200 otherwise Http status 204
 */
@GetMapping("past")
  @ResponseBody
  public ResponseEntity<List<Batch>> getPastBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> pastBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).after(b.getEndDate())) {
        pastBatches.add(b);
      }
    }
    if (!pastBatches.isEmpty()) {
    	return new ResponseEntity<List<Batch>>(pastBatches, HttpStatus.OK);
    }
    return  new ResponseEntity<List<Batch>>(HttpStatus.NO_CONTENT);
  }

  /**
 * A method to get all future batches for the trainer using BatchService.
 * 
 * @param request Http request hold the trainer email as parameter.
 * @return a list of all future batches for the trainer, Http status 200 otherwise Http status 204
 */
@GetMapping("future")
  @ResponseBody
  public ResponseEntity<List<Batch>> getFutureBatches(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> futureBatches = new ArrayList<>();
    for (Batch b : batches) {
      if (new Timestamp(System.currentTimeMillis()).before(b.getStartDate())) {
        futureBatches.add(b);
      }
    }
    
    if (!futureBatches.isEmpty()) {
    	return new ResponseEntity<List<Batch>>(futureBatches, HttpStatus.OK);
    }
    return  new ResponseEntity<List<Batch>>(HttpStatus.NO_CONTENT);
  }

  /**
 * A method to get all in-progress batches for the trainer using BatchService.
 * 
 * @param request Http request hold the trainer email as parameter.
 * @return a list of all in-progress batches for the trainer, Http status 200 otherwise Http status 204
 */
@GetMapping("inprogress")
  @ResponseBody
  public ResponseEntity<Batch> getBatchInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    Batch batchInProgress = null;
    Timestamp t = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (t.after(b.getStartDate()) && t.before(b.getEndDate())) {
        batchInProgress = b;
        break;
      }
    }
    if (batchInProgress != null) {
    	return new ResponseEntity<Batch>(batchInProgress, HttpStatus.OK);
    }
    return  new ResponseEntity<Batch>(HttpStatus.NO_CONTENT);
  }

  /**
 * A method to get all in-progress for the trainer batches using BatchService.
 * 
 * @param request Http request hold the trainer email as parameter.
 * @return a list of all in-progress batches for the trainer, Http status 200 otherwise Http status 204
 */
@GetMapping("allinprogress")
  @ResponseBody
  public ResponseEntity<List<Batch>> getAllBatchesInProgress(HttpServletRequest request) {
    List<Batch> batches = batchService.getBatchByTrainer(bamUserService.findUserByEmail(request.getParameter(EMAIL)));

    List<Batch> batchesInProgress = new ArrayList<>();
    Timestamp time = new Timestamp(System.currentTimeMillis());
    for (Batch b : batches) {
      if (time.after(b.getStartDate()) && time.before(b.getEndDate())) {
        batchesInProgress.add(b);
      }
    }
    if (!batchesInProgress.isEmpty()) {
    	return new ResponseEntity<List<Batch>>(batchesInProgress, HttpStatus.OK);
    }
    return  new ResponseEntity<List<Batch>>(HttpStatus.NO_CONTENT);
  }


  /**
 * A method to get batch by batch id using BatchService.
 * 
 * @param request Http request hold the batch id as parameter.
 * @return a batch , Http status 200 otherwise Http status 204.
 */
@GetMapping("byid")
  @ResponseBody
  public ResponseEntity<Batch> getBatchById(HttpServletRequest request) {
	  Batch result = batchService.getBatchById(Integer.parseInt(request.getParameter("batchId")));
	  if ( result != null) {
		  return new ResponseEntity<Batch>(result, HttpStatus.OK); 
	  }
	  return  new ResponseEntity<Batch>(HttpStatus.NO_CONTENT);
  }

  /**
 * A method to update batch using BatchService.
 * 
 * @param batch to be update.
 * @return Http status 201 otherwise Http status 400
 */
@PostMapping("updatebatch")
  public ResponseEntity<?> updateBatch(@RequestBody Batch batch) {
    Batch result = batchService.addOrUpdateBatch(batch);
    if ( result != null) {
		  return new ResponseEntity<Batch>(HttpStatus.ACCEPTED); 
	  }
	  return  new ResponseEntity<Batch>(HttpStatus.BAD_REQUEST);
    
  }

  /**
 * A method to get all batch types using BatchService.
 * 
 * @return a list of batch types, Http status 200 otherwise Http status 204
 */
@GetMapping("batchtypes")
  @ResponseBody
  public ResponseEntity<List<BatchType>> getAllBatchTypes() {
	  List<BatchType> result = batchService.getAllBatchTypes();
	  if ( result != null) {
		  return new ResponseEntity<List<BatchType>>(result, HttpStatus.OK); 
	  }
	  return  new ResponseEntity<List<BatchType>>(HttpStatus.NO_CONTENT);
  }

}
