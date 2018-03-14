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
@Table(name = "BATCH_TYPE")
public class BatchType {

	@Id
	@Column(name = "BATCH_TYPE_ID")
	@SequenceGenerator(name = "BATCH_TYPE_ID_SEQ", sequenceName = "BATCH_TYPE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_TYPE_ID_SEQ")
	private Integer id;

	@Column(name = "BATCH_TYPE_NAME")
	private String name;

	@Column(name = "BATCH_TYPE_LENGTH") // For now, this defaults to 10 and can't be changed.
	private Integer length = 10; // In the future, this field can be editable.

	public BatchType() {
		super();
	}

	public BatchType(Integer id, String name, Integer length) {
		super();
		this.id = id;
		this.name = name;
		this.length = length;
	}

	public BatchType(String name, Integer length) {
		super();
		this.name = name;
		this.length = length;
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

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("BatchType") + ":{";
		json += jsonify.addKey("batchTypeID") + jsonify.addValue(id.toString());
		json += jsonify.addKey("batchTypeName") + jsonify.addValue(name);
		json += jsonify.addKey("batchTypeLength") + jsonify.addEndValue(length.toString());
		json += "}}";
		return json;
	}

}
