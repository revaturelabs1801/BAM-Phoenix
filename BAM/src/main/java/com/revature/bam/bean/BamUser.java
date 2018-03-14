package com.revature.bam.bean;

import javax.persistence.*;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.bam.logging.JSONify;


@Entity
@Table(name = "USERS")
@Component
public class BamUser {

	@Id
	@Column(name = "USER_ID")
	@SequenceGenerator(name = "USERID_SEQ", sequenceName = "USERID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERID_SEQ")
	private int userId;

	@Column(name = "FIRST_NAME")
	@NotEmpty(message = "First name cannot be empty")
	private String fName;

	@Column(name = "MIDDLE_NAME")
	private String mName;

	@Column(name = "LAST_NAME")
	@NotEmpty(message = "Last name cannot be empty")
	private String lName;

	@Column(name = "EMAIL")
	@NotEmpty(message = "e-mail address cannot be empty")
	private String email;

	@Column(name = "PASSWORD")
	@NotEmpty(message="Password cannot be empty")
	private String pwd;
	
	@Column(name = "ROLE") // Role 1 is for associates // Role 2 is for trainers & QC
	private int role; // Role 3 is for admins

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "BATCH_ID", referencedColumnName = "BATCH_ID")
	@Autowired // Batch ID should only be used for associates. DO NOT use this
	private Batch batch; // field to assign a batch to a trainer. It should be
							// null for
							// trainers and admins. A trainer is assigned in the
							// Batches table.
	@Column(name = "MAIN_PHONE")
	@NotEmpty(message = "Primary phone cannot be empty")
	private String phone;

	@Column(name = "SECOND_PHONE")
	private String phone2;

	@Column(name = "SKYPE_ID")
	private String skype;

	@Column(name = "PASSWORD_BAK") // This is a backup password that will be
									// used when
	private String pwd2; // the user needs to reset their password.

	@Column(name="ASSIGNFORCE_ID")
	private Integer assignForceID;
	
	
	public BamUser() {
		super();
	}

	public BamUser(String fName, String mName, String lName, String email, String pwd, int role, Batch batch,
			String phone, String phone2, String skype, String pwd2) {//NOSONAR
		super();
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.batch = batch;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
		this.pwd2 = pwd2;
	}

	public BamUser(int userId, String fName, String mName, String lName, String email, String pwd, int role,
			Batch batch, String phone, String phone2, String skype, String pwd2) {//NOSONAR
		super();
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.userId = userId;
		this.pwd = pwd;
		this.role = role;
		this.batch = batch;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
		this.pwd2 = pwd2;
	}

	public BamUser(int userId, String fName, String mName, String lName, String email, String pwd, int role,
			Batch batch, String phone, String phone2, String skype, String pwd2, Integer AssignForceID) {//NOSONAR
		super();
		this.userId = userId;
		this.fName = fName;
		this.mName = mName;
		this.lName = lName;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
		this.batch = batch;
		this.phone = phone;
		this.phone2 = phone2;
		this.skype = skype;
		this.pwd2 = pwd2;
		assignForceID = AssignForceID;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public Integer getAssignForceID() {
		return assignForceID;
	}

	public void setAssignForceID(Integer assignForceID) {
		this.assignForceID = assignForceID;
	}

	@Override
	public String toString() {
		JSONify jsonify = new JSONify();
		String json = "{" + jsonify.quotify("BamUser") + ":{";
		json += jsonify.addKey("bamUserID") + jsonify.addValue(String.valueOf(userId));
		json += jsonify.addKey("bamUserFirstName") + jsonify.addValue(fName);
		json += jsonify.addKey("bamUserMiddleName") + jsonify.addValue(mName);
		json += jsonify.addKey("bamUserLastName") + jsonify.addValue(lName);
		json += jsonify.addKey("bamUserEmail") + jsonify.addValue(email);
		json += jsonify.addKey("bamUserPassword") + jsonify.addValue(pwd);
		json += jsonify.addKey("bamUserRole") + jsonify.addValue(String.valueOf(role));
		json += jsonify.addKey("bamUserPhone") + jsonify.addValue(phone);
		json += jsonify.addKey("bamUserPhone2") + jsonify.addValue(phone2);
		json += jsonify.addKey("bamUserSkype") + jsonify.addValue(skype);
		json += jsonify.addKey("bamUserPassword2") + jsonify.addEndValue(pwd2);
		json += "}}";
		
		return json;
	}
}
