package com.revature.bam.bean;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "SUBTOPIC_NAME")
public class SubtopicName {

	@Id
	@Column(name = "SUBTOPIC_NAME_ID")
	@SequenceGenerator(name = "SUBTOPIC_NAME_ID_SEQ", sequenceName = "SUBTOPIC_NAME_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBTOPIC_NAME_ID_SEQ")
	private Integer id;

	@Column(name = "SUBTOPIC_NAME")
	private String name;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBTOPIC_TOPIC", referencedColumnName = "TOPIC_ID")
	@Autowired
	private TopicName topic;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUBTOPIC_TYPE", referencedColumnName = "TYPE_ID")
	@Autowired
	private SubtopicType type;

	public SubtopicName() {
		super();
	}

	public SubtopicName(Integer id, String name, TopicName topic, SubtopicType type) {
		super();
		this.id = id;
		this.name = name;
		this.topic = topic;
		this.type = type;
	}

	public SubtopicName(String name, TopicName topic, SubtopicType type) {
		super();
		this.name = name;
		this.topic = topic;
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

	public TopicName getTopic() {
		return topic;
	}

	public void setTopic(TopicName topic) {
		this.topic = topic;
	}

	public SubtopicType getType() {
		return type;
	}

	public void setType(SubtopicType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SubtopicName [id=" + id + ", name=" + name + ", topic=" + topic + "]";
	}

}
