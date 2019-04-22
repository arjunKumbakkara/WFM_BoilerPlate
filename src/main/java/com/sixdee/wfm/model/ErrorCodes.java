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
import javax.persistence.Table;

import com.sixdee.wfm.common.TableNames;

/**
 * @author nalini.n
 *
 *         Date : 27-Feb-2019
 */
@Entity
@Table(name = TableNames.ErrorCodes)
public class ErrorCodes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "error_code_id")
	private int errorCodeId;

	@Column(name = "error_code")
	private String errorCode;

	@Column(name = "error_desc")
	private String errorDesc;

	public int getErrorCodeId() {
		return errorCodeId;
	}

	public void setErrorCodeId(int errorCodeId) {
		this.errorCodeId = errorCodeId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
