package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Amphur")
public class Amphur {
	
	@Id
	@Column(name = "Code")
	private String code;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "ProvinceCode")
	private String provinceCode;
	
	/*@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_ProvinceCode")
	private Province province;*/

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	/*public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}*/
	
	
	
}
