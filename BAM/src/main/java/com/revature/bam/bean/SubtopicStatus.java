package com.revature.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "SUBTOPIC_STATUS")
public class SubtopicStatus {

	@Id
	@Column(name = "STATUS_ID")
	@SequenceGenerator(name = "STATUS_ID_SEQ", sequenceName = "STATUS_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATUS_ID_SEQ")
	private Integer id;

	@Column(name = "STATUS_NAME")
	private String name;

	public SubtopicStatus() {
		//Empty Because No Args
	}

	public SubtopicStatus(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SubtopicStatus(String name) {
		super();
		this.name = name;
	}//NOSONAR

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
		return "SubtopicStatus [id=" + id + ", name=" + name + "]";//NOSONAR
	}

}
