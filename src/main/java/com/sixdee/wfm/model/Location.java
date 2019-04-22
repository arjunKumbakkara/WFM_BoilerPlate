package com.sixdee.wfm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 14-Mar-2019
 */
@Entity
@Table(name = TableNames.Locations)
@EntityListeners(AuditingEntityListener.class)
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	@ApiModelProperty(hidden = true)
	private Long locationId;

	@Column(name = "location_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String locationName;

	@Column(name = "community_id")
	@NotNull
	@ApiModelProperty(required = true)
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the community id and for GET request ignore this field in Skill
	private int communityId;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "community_id", nullable = false, insertable = false, updatable = false)
	private Community community;

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getUpdateDate() {
		return updateDate;
	}

	@ApiModelProperty(hidden = true)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the community
	 */
	public Community getCommunity() {
		return community;
	}

	/**
	 * @param community
	 *            the community to set
	 */
	@ApiModelProperty(hidden = true)
	public void setCommunity(Community community) {
		this.community = community;
	}

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getCreateDate() {
		return createDate;
	}

	@ApiModelProperty(hidden = true)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

}
