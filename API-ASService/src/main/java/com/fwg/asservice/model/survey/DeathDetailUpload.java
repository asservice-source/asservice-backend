package com.fwg.asservice.model.survey;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fwg.asservice.model.Person;

@Entity
@Table(name = "DeathDetailUpload")
public class DeathDetailUpload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4342326728130706007L;

	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Id
	@Column(name = "CitizenID")
	private String citizenId;
	
	@Id
	@Column(name = "FileID")
	private int fileId;
	
	@Column(name = "Description")
	private String description;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_CitizenID")
	private Person person;

	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	/*public String getCitizenId() {
		return citizenId;
	}

	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}

