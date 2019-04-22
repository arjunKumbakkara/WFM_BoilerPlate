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
@Table(name = TableNames.City)
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "city_id")
	@NotNull
	//Because these are the real CityCodes/CityIdentifiers.(The system cant generate them.)
	@ApiModelProperty(required = true)
	private int cityId;

	@Column(name = "city_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String cityName;

	@Column(name = "state_id")
	@NotNull
	@JsonIgnore
	private int stateId;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false, insertable = false, updatable = false)
	private State state;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
