/**
 * 
	
 */
package com.sixdee.wfm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;


@Entity
public class Week {
	private Long shiftWeekId;
	
	private Long Monday_Shift_Type;    //half/full/off
	private Date Monday_StartTime;
	private Date Monday_EndTime;
	
	private Long Tuesday_Shift_Type;
	private Date Tuesday_StartTime;
	private Date Tuesday_EndTime;
	
	
	private Long Wednesday_Shift_Type;
	private Date Wednesday_StartTime;
	private Date Wednesday_EndTime;
	
	private Long Thursday_Shift_Type;
	private Date Thursday_StartTime;
	private Date Thursday_EndTime;
	
	private Long Friday_Shift_Type;
	private Date Friday_StartTime;
	private Date Friday_EndTime;
	
	private Long Saturday_Shift_Type;
	private Date Saturday_StartTime;
	private Date Saturday_EndTime;
	
	private Long Sunday_Shift_Type;
	private Date Sunday_StartTime;
	private Date Sunday_EndTime;
	/**
	 * @return the shiftWeekId
	 */
	public Long getShiftWeekId() {
		return shiftWeekId;
	}
	/**
	 * @param shiftWeekId the shiftWeekId to set
	 */
	public void setShiftWeekId(Long shiftWeekId) {
		this.shiftWeekId = shiftWeekId;
	}
	/**
	 * @return the monday_Shift_Type
	 */
	public Long getMonday_Shift_Type() {
		return Monday_Shift_Type;
	}
	/**
	 * @param monday_Shift_Type the monday_Shift_Type to set
	 */
	public void setMonday_Shift_Type(Long monday_Shift_Type) {
		Monday_Shift_Type = monday_Shift_Type;
	}
	/**
	 * @return the monday_StartTime
	 */
	public Date getMonday_StartTime() {
		return Monday_StartTime;
	}
	/**
	 * @param monday_StartTime the monday_StartTime to set
	 */
	public void setMonday_StartTime(Date monday_StartTime) {
		Monday_StartTime = monday_StartTime;
	}
	/**
	 * @return the monday_EndTime
	 */
	public Date getMonday_EndTime() {
		return Monday_EndTime;
	}
	/**
	 * @param monday_EndTime the monday_EndTime to set
	 */
	public void setMonday_EndTime(Date monday_EndTime) {
		Monday_EndTime = monday_EndTime;
	}
	/**
	 * @return the tuesday_Shift_Type
	 */
	public Long getTuesday_Shift_Type() {
		return Tuesday_Shift_Type;
	}
	/**
	 * @param tuesday_Shift_Type the tuesday_Shift_Type to set
	 */
	public void setTuesday_Shift_Type(Long tuesday_Shift_Type) {
		Tuesday_Shift_Type = tuesday_Shift_Type;
	}
	/**
	 * @return the tuesday_StartTime
	 */
	public Date getTuesday_StartTime() {
		return Tuesday_StartTime;
	}
	/**
	 * @param tuesday_StartTime the tuesday_StartTime to set
	 */
	public void setTuesday_StartTime(Date tuesday_StartTime) {
		Tuesday_StartTime = tuesday_StartTime;
	}
	/**
	 * @return the tuesday_EndTime
	 */
	public Date getTuesday_EndTime() {
		return Tuesday_EndTime;
	}
	/**
	 * @param tuesday_EndTime the tuesday_EndTime to set
	 */
	public void setTuesday_EndTime(Date tuesday_EndTime) {
		Tuesday_EndTime = tuesday_EndTime;
	}
	/**
	 * @return the wednesday_Shift_Type
	 */
	public Long getWednesday_Shift_Type() {
		return Wednesday_Shift_Type;
	}
	/**
	 * @param wednesday_Shift_Type the wednesday_Shift_Type to set
	 */
	public void setWednesday_Shift_Type(Long wednesday_Shift_Type) {
		Wednesday_Shift_Type = wednesday_Shift_Type;
	}
	/**
	 * @return the wednesday_StartTime
	 */
	public Date getWednesday_StartTime() {
		return Wednesday_StartTime;
	}
	/**
	 * @param wednesday_StartTime the wednesday_StartTime to set
	 */
	public void setWednesday_StartTime(Date wednesday_StartTime) {
		Wednesday_StartTime = wednesday_StartTime;
	}
	/**
	 * @return the wednesday_EndTime
	 */
	public Date getWednesday_EndTime() {
		return Wednesday_EndTime;
	}
	/**
	 * @param wednesday_EndTime the wednesday_EndTime to set
	 */
	public void setWednesday_EndTime(Date wednesday_EndTime) {
		Wednesday_EndTime = wednesday_EndTime;
	}
	/**
	 * @return the thursday_Shift_Type
	 */
	public Long getThursday_Shift_Type() {
		return Thursday_Shift_Type;
	}
	/**
	 * @param thursday_Shift_Type the thursday_Shift_Type to set
	 */
	public void setThursday_Shift_Type(Long thursday_Shift_Type) {
		Thursday_Shift_Type = thursday_Shift_Type;
	}
	/**
	 * @return the thursday_StartTime
	 */
	public Date getThursday_StartTime() {
		return Thursday_StartTime;
	}
	/**
	 * @param thursday_StartTime the thursday_StartTime to set
	 */
	public void setThursday_StartTime(Date thursday_StartTime) {
		Thursday_StartTime = thursday_StartTime;
	}
	/**
	 * @return the thursday_EndTime
	 */
	public Date getThursday_EndTime() {
		return Thursday_EndTime;
	}
	/**
	 * @param thursday_EndTime the thursday_EndTime to set
	 */
	public void setThursday_EndTime(Date thursday_EndTime) {
		Thursday_EndTime = thursday_EndTime;
	}
	/**
	 * @return the friday_Shift_Type
	 */
	public Long getFriday_Shift_Type() {
		return Friday_Shift_Type;
	}
	/**
	 * @param friday_Shift_Type the friday_Shift_Type to set
	 */
	public void setFriday_Shift_Type(Long friday_Shift_Type) {
		Friday_Shift_Type = friday_Shift_Type;
	}
	/**
	 * @return the friday_StartTime
	 */
	public Date getFriday_StartTime() {
		return Friday_StartTime;
	}
	/**
	 * @param friday_StartTime the friday_StartTime to set
	 */
	public void setFriday_StartTime(Date friday_StartTime) {
		Friday_StartTime = friday_StartTime;
	}
	/**
	 * @return the friday_EndTime
	 */
	public Date getFriday_EndTime() {
		return Friday_EndTime;
	}
	/**
	 * @param friday_EndTime the friday_EndTime to set
	 */
	public void setFriday_EndTime(Date friday_EndTime) {
		Friday_EndTime = friday_EndTime;
	}
	/**
	 * @return the saturday_Shift_Type
	 */
	public Long getSaturday_Shift_Type() {
		return Saturday_Shift_Type;
	}
	/**
	 * @param saturday_Shift_Type the saturday_Shift_Type to set
	 */
	public void setSaturday_Shift_Type(Long saturday_Shift_Type) {
		Saturday_Shift_Type = saturday_Shift_Type;
	}
	/**
	 * @return the saturday_StartTime
	 */
	public Date getSaturday_StartTime() {
		return Saturday_StartTime;
	}
	/**
	 * @param saturday_StartTime the saturday_StartTime to set
	 */
	public void setSaturday_StartTime(Date saturday_StartTime) {
		Saturday_StartTime = saturday_StartTime;
	}
	/**
	 * @return the saturday_EndTime
	 */
	public Date getSaturday_EndTime() {
		return Saturday_EndTime;
	}
	/**
	 * @param saturday_EndTime the saturday_EndTime to set
	 */
	public void setSaturday_EndTime(Date saturday_EndTime) {
		Saturday_EndTime = saturday_EndTime;
	}
	/**
	 * @return the sunday_Shift_Type
	 */
	public Long getSunday_Shift_Type() {
		return Sunday_Shift_Type;
	}
	/**
	 * @param sunday_Shift_Type the sunday_Shift_Type to set
	 */
	public void setSunday_Shift_Type(Long sunday_Shift_Type) {
		Sunday_Shift_Type = sunday_Shift_Type;
	}
	/**
	 * @return the sunday_StartTime
	 */
	public Date getSunday_StartTime() {
		return Sunday_StartTime;
	}
	/**
	 * @param sunday_StartTime the sunday_StartTime to set
	 */
	public void setSunday_StartTime(Date sunday_StartTime) {
		Sunday_StartTime = sunday_StartTime;
	}
	/**
	 * @return the sunday_EndTime
	 */
	public Date getSunday_EndTime() {
		return Sunday_EndTime;
	}
	/**
	 * @param sunday_EndTime the sunday_EndTime to set
	 */
	public void setSunday_EndTime(Date sunday_EndTime) {
		Sunday_EndTime = sunday_EndTime;
	}
	
	

}