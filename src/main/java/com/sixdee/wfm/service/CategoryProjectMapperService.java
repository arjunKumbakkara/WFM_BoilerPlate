/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.CategoryProjectMapper;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface CategoryProjectMapperService {

	public CategoryProjectMapper createCategoryProjectMapper(CategoryProjectMapper category);

	public Page<CategoryProjectMapper> getAllCategoryProjectMapper(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<CategoryProjectMapper> getAllCategoryProjectMapper(String search);

	public CategoryProjectMapper findById(Long Id);

	public CategoryProjectMapper updateCategoryProjectMapper(String key, Long Id, CategoryProjectMapper updateCategoryProjectMapper);

	public void deleteCategoryProjectMapper(String key, Long Id);

}
