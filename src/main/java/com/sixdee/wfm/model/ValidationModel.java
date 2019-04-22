package com.sixdee.wfm.model;

/**
 * 
	
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sixdee.wfm.common.TableNames;

/**
 * @author nalini.n
 *
 *         Date : 27-Feb-2019
 */
@Entity
@Table(name = TableNames.Validations)
public class ValidationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "validation_id")
	private Long validationId;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "is_mandatory")
	private Integer isMandatory;

	@Column(name = "min_length")
	private Integer minLength;

	@Column(name = "max_length")
	private Integer maxLength;

	@Column(name = "reg_exp_id")
	private Integer regExpId;

	@Column(name = "soft_delete")
	private Integer softDelete;

	@OneToOne
	@JoinColumn(name = "reg_exp_id", nullable = false, insertable = false, updatable = false)
	private RegularExpression regularExpression;

	public Long getValidationId() {
		return validationId;
	}

	public void setValidationId(Long validationId) {
		this.validationId = validationId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(Integer isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getRegExpId() {
		return regExpId;
	}

	public void setRegExpId(Integer regExpId) {
		this.regExpId = regExpId;
	}

	public Integer getSoftDelete() {
		return softDelete;
	}

	public void setSoftDelete(Integer softDelete) {
		this.softDelete = softDelete;
	}

	public RegularExpression getRegularExpression() {
		return regularExpression;
	}

	public void setRegularExpression(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}

}
