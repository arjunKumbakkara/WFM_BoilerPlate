/**
 * 
	
 */
package com.sixdee.wfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 14-Mar-2019
 */
@Entity
@Table(name = TableNames.Community)
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "community_id")
	@NotNull
	@ApiModelProperty(required = true)
	private int communityId;

	@Column(name = "community_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String communityName;

	@Column(name = "city_id")
	@NotNull
	@JsonIgnore
	private int cityId;

	@Column(name = "postal_code", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String postalCode;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false, insertable = false, updatable = false)
	private City city;

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
