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
@Table(name = TableNames.State)
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "state_id")
	@NotNull
	@ApiModelProperty(required = true)
	private int stateId;

	@Column(name = "state_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String stateName;

	@Column(name = "country_id")
	@NotNull
	@JsonIgnore
	private int countryId;

	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false, insertable = false, updatable = false)
	private Country country;

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
