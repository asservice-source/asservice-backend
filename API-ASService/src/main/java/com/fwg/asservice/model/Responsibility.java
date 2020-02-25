/**
 * 
 */
package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Responsibility")
public class Responsibility {

	@Id
	@Column(name = "UserID")
	private String userID;
	
	@Column(name = "VillageID")
	private Integer villageID;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="VillageID", insertable = false, updatable = false)
	private Village village;

	public Integer getVillageID() {
		return villageID;
	}

	public void setVillageID(Integer villageID) {
		this.villageID = villageID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}
}