package com.revature.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.revature.bam.logging.JSONify;

@Component
@Entity
@Table(name = "SUBTOPIC_TYPE")
public class SubtopicType {

	@Id
	@Column(name = "TYPE_ID")
	@SequenceGenerator(name = "SUBTOPIC_TYPE_ID_SEQ", sequenceName = "SUBTOPIC_TYPE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBTOPIC_TYPE_ID_SEQ")
	private Integer id;

	@Column(name = "TYPE_NAME")
	private String name;

	public SubtopicType() {
		//Empty Because No Args
	}

	public SubtopicType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicType(String name) {
		super();//NOSONAR
		this.name = name;
	}

	public Integer getId() {
		return id;
	}//NOSONAR

	public void setId(Integer id) {
		this.id = id;
	}//NOSONAR

	public String getName() {
		return name;
	}//NOSONAR

	public void setName(String name) {
		this.name = name;
	}//NOSONAR

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("SubtopicType") + ":{";
		json += jsonify.addKey("subtopicTypeID") + jsonify.addValue(String.valueOf(id));
		json += jsonify.addKey("subtopicTypeName") + jsonify.addEndValue(name);
		json += "}}";
		return json;
	}

}
