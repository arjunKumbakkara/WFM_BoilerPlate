/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Category;
import com.sixdee.wfm.repository.CategoryRepository;
import com.sixdee.wfm.service.CategoryService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class CategoryServiceImpl extends PaginationBaseImpl implements CategoryService {
	public static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category createCategory(Category category) {
		System.out.println("CategoryServiceImpl.createCategory()" + category);
		return categoryRepository.save(category);
	}

	@Override /* @Pageable */
	public Page<Category> getAllCategory(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Category> lister = categoryRepository.findAll(categoryRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Category.class));
		return lister;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> category = null;
		/*
		 * if (search != null) { category = categoryRepository.findAll(categoryRepository.textInAllColumns(search)); } else {
		 */
		category = categoryRepository.findAll();
		/* } */
		return category;
	}

	@Override
	public Category findById(Long Id) {
		Category category = categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", Id));
		return category;
	}

	@Override
	public Category updateCategory(String key, Long Id, Category updateCategory) {

		categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", Id));
		updateCategory.setCategoryId(Id);
		return categoryRepository.save(updateCategory);

	}

	@Override
	public void deleteCategory(String key, Long Id) {
		Category category = categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", Id));
		try {
			categoryRepository.delete(category);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Category");
		}
	}

}
