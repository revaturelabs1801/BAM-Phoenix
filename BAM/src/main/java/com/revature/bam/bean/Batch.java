package com.revature.bam.bean;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.bam.logging.JSONify;

@Component
@Entity
@Table(name = "BATCHES")
public class Batch {

	@Id
	@Column(name = "BATCH_ID")
	private Integer id;

	@Column(name = "BATCH_NAME")
	@NotEmpty(message = "Batch name cannot be empty")
	private String name;

	@Column(name = "START_DATE")
	@NotNull(message = "Start date cannot be empty")
	private Timestamp startDate;

	@Column(name = "END_DATE")
	@NotNull(message = "End date cannot be empty")
	private Timestamp endDate;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "Trainer_ID", referencedColumnName = "User_Id")
	@Autowired
	private BamUser trainer;
	
		/*
		 * The trainer of a batch is stored in the trainer field.  It is vitally
		 * important that if a user is a trainer, that the batch ID of the user's
		 * table is empty.  All information about a trainer's batch is stored in
		 * this table. 
		 */

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "BATCH_TYPE_ID", referencedColumnName = "BATCH_TYPE_ID")
	@Autowired
	private BatchType type;

	public Batch() {
		super();
	}

	public Batch(Integer id, String name, Timestamp startDate, Timestamp endDate, BamUser trainer, BatchType type) {
		super();
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public Batch(String name, Timestamp startDate, Timestamp endDate, BamUser trainer, BatchType type) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.trainer = trainer;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public BamUser getTrainer() {
		return trainer;
	}

	public void setTrainer(BamUser trainer) {
		this.trainer = trainer;
	}

	public BatchType getType() {
		return type;
	}

	public void setType(BatchType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("Batches") + ":{";
		json += jsonify.addKey("batchesID") + jsonify.addValue(id.toString());
		json += jsonify.addKey("batchesName") + jsonify.addValue(name);
		json += jsonify.addKey("batchesStartDate") + jsonify.addValue(startDate.toString());
		json += jsonify.addKey("batchesEndDate") + jsonify.addValue(endDate.toString());
		json += jsonify.addKey("batchesTrainer") + trainer + ",";
		json += jsonify.addKey("batchesType") + type;
		json += "}}";
		return json;
	}

}
