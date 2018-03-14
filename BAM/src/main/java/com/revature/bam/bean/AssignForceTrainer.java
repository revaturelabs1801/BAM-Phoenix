package com.revature.bam.bean;

import org.springframework.stereotype.Component;

import com.revature.bam.logging.JSONify;

@Component
public class AssignForceTrainer {

	private Integer trainerId;
	private String firstName;
	private String lastName;
	
	public AssignForceTrainer() {
		//Empty Because No Args
	}

	
	public AssignForceTrainer(Integer trainerId, String firstName, String lastName) {
		super();
		this.trainerId = trainerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("AssignForceTrainer") + ":{";
		json += jsonify.addKey("assignForceTrainerFirstName") + jsonify.addValue(firstName);
		json += jsonify.addKey("assignForceTrainerLastName") + jsonify.addValue(lastName);
		json += jsonify.addKey("assignForceTrainerID") + jsonify.addEndValue(trainerId.toString());
		json += "}}";
		return json;
	}

}
