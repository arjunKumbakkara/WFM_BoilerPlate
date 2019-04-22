
package com.sixdee.wfm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sixdee.wfm.common.Constants;
import com.sixdee.wfm.common.TableNames;

import io.swagger.annotations.ApiModelProperty;
/*
@author
Nalini N

Date:14-Mar-2019*/

@Entity
@Table(name = TableNames.Category)
@EntityListeners(AuditingEntityListener.class)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", insertable = false, updatable = false)
	@ApiModelProperty(hidden = true)
	private Long categoryId;

	@Column(name = "category_name", unique = true)
	@NotNull
	@ApiModelProperty(required = true)
	private String categoryName;

	@Column(name = "parent_category_id")
	private Integer parent_category_id;

	/*
	 * This is to get tree structure in the same table..
	 * 
	 * category_id category_name parent_category_id create_date created_by update_date updated_by 2 Category 1 SubCat 11 18 03-04-2019 00:00:00 03-04-2019 14:10:58 5 Category 1 Sub Cat 2 18 01-04-2019
	 * 00:00:00 01-04-2019 00:00:00 16 Category 10 18 03-04-2019 16:32:55 03-04-2019 16:32:55 18 Category 12 03-04-2019 17:34:37 03-04-2019 17:34:37 19 Category 13 03-04-2019 17:38:15 03-04-2019
	 * 17:38:15
	 * 
	 * 
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parent_category_id", insertable = false, updatable = false)
	private Category manager;

	@OneToMany(mappedBy = "manager")
	private Set<Category> subCategoryList = new HashSet<Category>();

	@Column(name = "create_date")
	@CreatedDate
	private Date createDate;

	@Column(name = "update_date")
	@LastModifiedDate
	private Date updateDate;

	public Integer getParent_category_id() {
		return parent_category_id;
	}

	public void setParent_category_id(Integer parent_category_id) {
		this.parent_category_id = parent_category_id;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	/*
	 * public Collection<Category> getCategoryList() { return categoryList; }
	 */

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/*
	 * public void setCategoryList(Set<Category> categoryList) { this.categoryList = categoryList; }
	 */

	@JsonFormat(pattern = Constants.RESPONSE_DATE_FORMAT)
	public Date getUpdateDate() {
		return updateDate;
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

	@ApiModelProperty(hidden = true)
	public Set<Category> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(Set<Category> subordinates) {
		this.subCategoryList = subordinates;
	}
}
