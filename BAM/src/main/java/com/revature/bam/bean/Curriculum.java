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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "CURRICULUM")
public class Curriculum {
	
	@Id
	@Column(name = "CURRICULUM_ID")
	@SequenceGenerator(name = "CURRICULUM_ID_SEQ", sequenceName = "CURRICULUM_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRICULUM_ID_SEQ")
	private Integer id;
	
	@Column(name= "CURRICULUM_NAME")
	@NotEmpty(message = "Curriculum name cannot be empty")
	private String curriculumName;
	
	@Column(name ="CURRICULUM_VERSION")
	private int curriculumVersion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRICULUM_CREATOR", referencedColumnName = "USER_ID")
	private BamUser curriculumCreator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CURRICULUM_MODIFIER", referencedColumnName = "USER_ID")
	private BamUser curriculumModifier;
	
	@Column(name = "CURRICULUM_DATE_CREATED")
	@NotEmpty(message = "Curriculum Date Created cannot be empty")
	private String curriculumdateCreated;
	
	@Column(name = "CURRICULUM_NUMBER_OF_WEEKS")
	private int curriculumNumberOfWeeks;
	
	@Column(name = "CURRICULUM_IS_MASTER")
	private int isMaster;
	
	public Curriculum(){
		//Empty Because No Args
	}

	public Integer getCurriculumId() {
		return id;
	}

	public void setCurriculumId(Integer curriculumId) {
		this.id = curriculumId;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public int getCurriculumVersion() {
		return curriculumVersion;
	}

	public void setCurriculumVersion(int curriculumVersion) {
		this.curriculumVersion = curriculumVersion;
	}

	public BamUser getCurriculumCreator() {
		return curriculumCreator;
	}

	public void setCurriculumCreator(BamUser curriculumCreator) {
		this.curriculumCreator = curriculumCreator;
	}

	public BamUser getCurriculumModifier() {
		return curriculumModifier;
	}

	public void setCurriculumModifier(BamUser curriculumModifier) {
		this.curriculumModifier = curriculumModifier;
	}

	public String getCurriculumdateCreated() {
		return curriculumdateCreated;
	}

	public void setCurriculumdateCreated(String curriculumdateCreated) {
		this.curriculumdateCreated = curriculumdateCreated;
	}

	public int getCurriculumNumberOfWeeks() {
		return curriculumNumberOfWeeks;
	}

	public void setCurriculumNumberOfWeeks(int curriculumNumberOfWeeks) {
		this.curriculumNumberOfWeeks = curriculumNumberOfWeeks;
	}

	public int getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}

	@Override
	public String toString() {
		return "Curriculum [id=" + id + ", curriculumName=" + curriculumName + ", curriculumVersion="
				+ curriculumVersion + ", curriculumCreator=" + curriculumCreator + ", curriculumModifier="
				+ curriculumModifier + ", curriculumdateCreated=" + curriculumdateCreated + ", curriculumNumberOfWeeks="
				+ curriculumNumberOfWeeks + ", isMaster=" + isMaster + "]";
	}
	
}
