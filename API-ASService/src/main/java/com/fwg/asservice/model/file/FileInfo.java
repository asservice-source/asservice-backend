package com.fwg.asservice.model.file;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FileInfo")
public class FileInfo {

	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "OriginalName")
	private String originalName;
	
	@Column(name = "FileURL")
	private String fileURL;
	
	@Column(name = "FileSize")
	private BigDecimal FileSize;
	
	@Column(name = "ContentType")
	private String contentType;
	
	@Column(name = "DateCreated")
	private Date dateCreated;
	
	@Column(name = "DateModified")
	private Date dateModified;
	
	@Column(name = "Owner")
	private int owner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public BigDecimal getFileSize() {
		return FileSize;
	}

	public void setFileSize(BigDecimal fileSize) {
		FileSize = fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	
}
