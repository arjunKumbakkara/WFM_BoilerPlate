/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.CategoryProjectMapper;
import com.sixdee.wfm.repository.CategoryProjectMapperRepository;
import com.sixdee.wfm.service.CategoryProjectMapperService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class CategoryProjectMapperServiceImpl extends PaginationBaseImpl implements CategoryProjectMapperService {

	@Autowired
	private CategoryProjectMapperRepository categoryProjectMapperRepository;

	@Override
	public CategoryProjectMapper createCategoryProjectMapper(CategoryProjectMapper category) {
		System.out.println("CategoryServiceImpl.createCategory()" + category);
		return categoryProjectMapperRepository.save(category);
	}

	@Override /* @Pageable */
	public Page<CategoryProjectMapper> getAllCategoryProjectMapper(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<CategoryProjectMapper> lister = categoryProjectMapperRepository.findAll(categoryProjectMapperRepository.textInAllColumns(search),
				gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, CategoryProjectMapper.class));
		return lister;
	}

	@Override
	public List<CategoryProjectMapper> getAllCategoryProjectMapper(String search) {
		List<CategoryProjectMapper> category = null;
		if (search != null) {
			category = categoryProjectMapperRepository.findAll(categoryProjectMapperRepository.textInAllColumns(search));
		} else {
			category = categoryProjectMapperRepository.findAll();
		}
		return category;
	}

	@Override
	public CategoryProjectMapper findById(Long Id) {
		CategoryProjectMapper categoryProjectMapper = categoryProjectMapperRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("CategoryProjectMapper", "id", Id));
		return categoryProjectMapper;
	}

	@Override
	public CategoryProjectMapper updateCategoryProjectMapper(String key, Long Id, CategoryProjectMapper updateCategoryProjectMapper) {

		categoryProjectMapperRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("CategoryProjectMapper", "id", Id));
		updateCategoryProjectMapper.setCategoryProjectMappingId(Id);
		return categoryProjectMapperRepository.save(updateCategoryProjectMapper);

	}

	@Override
	public void deleteCategoryProjectMapper(String key, Long Id) {
		CategoryProjectMapper categoryProjectMapper = categoryProjectMapperRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("CategoryProjectMapper", "id", Id));
		categoryProjectMapperRepository.delete(categoryProjectMapper);
	}
}
