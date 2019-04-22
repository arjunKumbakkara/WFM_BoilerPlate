/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Category;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface CategoryService {

	public Category createCategory(Category category);

	public Page<Category> getAllCategory(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Category> getAllCategory();

	public Category findById(Long Id);

	public Category updateCategory(String key, Long Id, Category updateCategory);

	public void deleteCategory(String key, Long Id);
}
