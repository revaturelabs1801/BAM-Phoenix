package com.revature.bam.bean;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.bam.logging.JSONify;

@Component
@Entity
@Table(name = "TOPIC_WEEK")
public class TopicWeek {

	@Id
	@Column(name = "WEEK_ID")
	@SequenceGenerator(name = "WEEK_ID_SEQ", sequenceName = "WEEK_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WEEK_ID_SEQ")
	private Integer id;
 
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "TOPIC_NAME_ID", referencedColumnName = "TOPIC_ID")
	@Autowired
	private TopicName topic;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "TOPIC_BATCH_ID", referencedColumnName = "BATCH_ID")
	@Autowired
	private Batch batch;

	@Column(name = "TOPIC_WEEK_NUMBER")
	private Integer weekNumber;

	public TopicWeek() {
		super();
	}

	public TopicWeek(Integer id, TopicName topic, Batch batch, Integer weekNumber) {
		super();
		this.id = id;
		this.topic = topic;
		this.batch = batch;
		this.weekNumber = weekNumber;
	}

	public TopicWeek(TopicName topic, Batch batch, Integer weekNumber) {
		super();
		this.topic = topic;
		this.batch = batch;
		this.weekNumber = weekNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TopicName getTopic() {
		return topic;
	}

	public void setTopic(TopicName topic) {
		this.topic = topic;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("TopicWeek") + ":{";
		json += jsonify.addKey("topicWeekID") + jsonify.addValue(String.valueOf(id));
		json += jsonify.addKey("topicWeekTopic") + topic + ",";
		json += jsonify.addKey("topicWeekBatch") + batch + ",";
		json += jsonify.addKey("topicWeekNumber") + jsonify.addEndValue(weekNumber.toString());
		json += "}}";
		return json;
	}

}
