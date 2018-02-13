package com.revature.bam.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Component
@Entity
@Table(name = "CURRICULUM_SUBTOPIC")
public class CurriculumSubtopic {
	
	@Id
	@Column(name = "CURRICULUM_SUBTOPIC_ID")
	@SequenceGenerator(name = "CURRICULUM_SUBTOPIC_ID_SEQ", sequenceName = "CURRICULUM_SUBTOPIC_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRICULUM_SUBTOPIC_ID_SEQ")
	private int curriculumSubtopicId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRICULUM_SUBTOPIC_NAME_ID", referencedColumnName = "SUBTOPIC_NAME_ID")
	@NotNull(message="Curriculum Subtopic Name cannot be null")
	private SubtopicName curriculumSubtopicNameId;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRICULUM_SUBTOPIC_CUR_ID", referencedColumnName = "CURRICULUM_ID")
	private Curriculum curriculum;

	
	@Column(name = "CURRICULUM_WEEK")
	private int curriculumSubtopicWeek;
	
	@Column(name = "CURRICULUM_DAY")
	private int curriculumSubtopicDay;
	
	public CurriculumSubtopic() {
		//Empty Because No Args
	}

	public CurriculumSubtopic(int curriculumSubtopicId, SubtopicName curriculumSubtopicNameId,
			Curriculum curriculumSubtopicCurriculumID, int curriculumSubtopicWeek, int curriculumSubtopicDay) {
		super();
		this.curriculumSubtopicId = curriculumSubtopicId;
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
		this.curriculum = curriculumSubtopicCurriculumID;
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	public int getCurriculumSubtopicId() {
		return curriculumSubtopicId;
	}

	public void setCurriculumSubtopicId(int curriculumSubtopicId) {
		this.curriculumSubtopicId = curriculumSubtopicId;
	}

	public SubtopicName getCurriculumSubtopicNameId() {
		return curriculumSubtopicNameId;
	}

	public void setCurriculumSubtopicNameId(SubtopicName curriculumSubtopicNameId) {
		this.curriculumSubtopicNameId = curriculumSubtopicNameId;
	}

	@JsonIgnore
	public Curriculum getCurriculumSubtopicCurriculumID() {
		return curriculum;
	}

	public void setCurriculumSubtopicCurriculumID(Curriculum curriculumSubtopicCurriculumID) {
		this.curriculum = curriculumSubtopicCurriculumID;
	}

	public int getCurriculumSubtopicWeek() {
		return curriculumSubtopicWeek;
	}

	public void setCurriculumSubtopicWeek(int curriculumSubtopicWeek) {
		this.curriculumSubtopicWeek = curriculumSubtopicWeek;
	}

	public int getCurriculumSubtopicDay() {
		return curriculumSubtopicDay;
	}

	public void setCurriculumSubtopicDay(int curriculumSubtopicDay) {
		this.curriculumSubtopicDay = curriculumSubtopicDay;
	}

	@Override
	public String toString() {
		return "CurriculumSubtopic [curriculumSubtopic_Id=" + curriculumSubtopicId + ", curriculumSubtopicNameId="
				+ curriculumSubtopicNameId + ", curriculumSubtopicCurriculumID=" + curriculum
				+ ", curriculumSubtopicWeek=" + curriculumSubtopicWeek + ", curriculumSubtopicDay="
				+ curriculumSubtopicDay + "]";
	}

	
	
	
	
}
