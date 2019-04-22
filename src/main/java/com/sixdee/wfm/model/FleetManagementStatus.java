/**
 * 
	
 */
package com.sixdee.wfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Entity
@Table(name = TableNames.FleetManagementStatus)
@EntityListeners(AuditingEntityListener.class)
public class FleetManagementStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fleet_management_status_id")
	@ApiModelProperty(hidden = true)
	private Long fleetManagementStatusId;

	@Column(name = "fleet_management_status")
	@ApiModelProperty(required = true)
	private String fleetManagementStatus;

	public Long getFleetManagementStatusId() {
		return fleetManagementStatusId;
	}

	public void setFleetManagementStatusId(Long fleetManagementStatusId) {
		this.fleetManagementStatusId = fleetManagementStatusId;
	}

	public String getFleetManagementStatus() {
		return fleetManagementStatus;
	}

	public void setFleetManagementStatus(String fleetManagementStatus) {
		this.fleetManagementStatus = fleetManagementStatus;
	}

}
