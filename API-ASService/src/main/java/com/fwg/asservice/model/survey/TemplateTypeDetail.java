package com.fwg.asservice.model.survey;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TemplateTypeDetail")
public class TemplateTypeDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8413897767584410586L;
	
	@Column(name = "RowGUID")
	private String rowGUID;
	
	@Id
	@Column(name = "TemplateTypeID")
	private int templateTypeId;
	
	@Id
	@Column(name = "SortOrder")
	private int sortOrder;
	
	@Column(name = "Round")
	private String round;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "StartDDMM")
	private String startDDMM;
	
	@Column(name = "EndDDMM")
	private String endDDMM;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_TemplateTypeID")
	private TemplateType templateType;

	public String getRowGUID() {
		return rowGUID;
	}

	public void setRowGUID(String rowGUID) {
		this.rowGUID = rowGUID;
	}

	public int getTemplateTypeId() {
		return templateTypeId;
	}

	public void setTemplateTypeId(int templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDDMM() {
		return startDDMM;
	}

	public void setStartDDMM(String startDDMM) {
		this.startDDMM = startDDMM;
	}

	public String getEndDDMM() {
		return endDDMM;
	}

	public void setEndDDMM(String endDDMM) {
		this.endDDMM = endDDMM;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}
	

}
