/**
 * 
	
 */
package com.sixdee.wfm.model;

/**
 * @author Nalini N
 *
 * Date : 22-Mar-2019
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
@Table(name = TableNames.RegularExpression)
public class RegularExpression {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reg_exp_id")
	private int regExpId;

	@Column(name = "reg_exp")
	private String regExp;

	@Column(name = "reg_exp_desc")
	private String regExpDesc;

	@Column(name = "error_code_id")
	private int errorCodeId;

	@OneToOne
	@JoinColumn(name = "error_code_id", nullable = false, insertable = false, updatable = false)
	private ErrorCodes errorCodes;

	public int getRegExpId() {
		return regExpId;
	}

	public void setRegExpId(int regExpId) {
		this.regExpId = regExpId;
	}

	public String getRegExp() {
		return regExp;
	}

	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}

	public String getRegExpDesc() {
		return regExpDesc;
	}

	public void setRegExpDesc(String regExpDesc) {
		this.regExpDesc = regExpDesc;
	}

	public int getErrorCodeId() {
		return errorCodeId;
	}

	public void setErrorCodeId(int errorCodeId) {
		this.errorCodeId = errorCodeId;
	}

	public ErrorCodes getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(ErrorCodes errorCodes) {
		this.errorCodes = errorCodes;
	}

}
