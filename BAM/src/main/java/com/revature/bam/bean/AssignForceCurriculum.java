package com.revature.bam.bean;

import com.revature.bam.logging.JSONify;

public class AssignForceCurriculum {
	
	private Integer currId;
	private String name;
	
	public AssignForceCurriculum() {
		//Empty Because No Args
	}

	public AssignForceCurriculum(Integer currId, String name) {
		super();
		this.currId = currId;
		this.name = name;
	}
	
	
	public Integer getCurrId() {
		return currId;
	}

	public void setCurrId(Integer currId) {
		this.currId = currId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("AssignForceCurriculum") + ":{";
		json += jsonify.addKey("assignForceCurriculumName") + jsonify.addValue(name);
		json += jsonify.addKey("assignForceCurriculumID") + jsonify.addEndValue(currId.toString());
		json += "}}";
		return json;
	}
	

}
