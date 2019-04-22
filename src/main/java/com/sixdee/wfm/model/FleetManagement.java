/**
 * 
	
 */
package com.sixdee.wfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
 *         Date : 11-Mar-2019
 */
@Entity
@Table(name = TableNames.FleetManagement)
@EntityListeners(AuditingEntityListener.class)
public class FleetManagement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fleet_management_id")
	@ApiModelProperty(hidden = true)
	private Long fleetManagementId;

	@Column(name = "vehicle_id")
	private String vehicleId;

	@Column(name = "status_id")
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the skill id and for GET request ignore this field in Skill
	private int statusId;

	@Column(name = "vehicle_type")
	@JsonProperty(access = Access.WRITE_ONLY) // Only for POST Request show the skill id and for GET request ignore this field in Skill
	private int vehicleType;

	@Column(name = "location_id")
	private int locationId;

	@Column(name = "assigned_to")
	private int assignedTo;

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false, insertable = false, updatable = false)
	private FleetManagementStatus status;

	@ManyToOne
	@JoinColumn(name = "vehicle_type", nullable = false, insertable = false, updatable = false)
	private VehicleType vehicle;

	public VehicleType getVehicle() {
		return vehicle;
	}

	@ApiModelProperty(hidden = true)
	public void setVehicle(VehicleType vehicle) {
		this.vehicle = vehicle;
	}

	public FleetManagementStatus getStatus() {
		return status;
	}

	@ApiModelProperty(hidden = true)
	public void setStatus(FleetManagementStatus status) {
		this.status = status;
	}

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getUpdateDate() {
		return updateDate;
	}

	public Long getFleetManagementId() {
		return fleetManagementId;
	}

	public void setFleetManagementId(Long fleetManagementId) {
		this.fleetManagementId = fleetManagementId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public int getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(int vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(int assignedTo) {
		this.assignedTo = assignedTo;
	}

	@ApiModelProperty(hidden = true)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getCreateDate() {
		return createDate;
	}

	@ApiModelProperty(hidden = true)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
