/**
 * 
	
 */
package com.sixdee.wfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 14-Mar-2019
 */
@Entity
@Table(name = TableNames.Country)
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "country_id")
	@NotNull
	@ApiModelProperty(required = true)
	private int countryId;

	@Column(name = "country_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String countryName;

	/*
	 * @JoinColumn(name = "country_id")
	 * 
	 * @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) private List<State> states = new ArrayList<State>();
	 */

	/*
	 * public List<State> getStates() { return states; }
	 * 
	 * public void setStates(List<State> states) { this.states = states; }
	 */

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
