package com.sixdee.wfm.service.impl;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.common.WfmBeanUtils;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Resource;
import com.sixdee.wfm.repository.ResourceRepository;
import com.sixdee.wfm.service.ResourceService;

@Component /* ALL LOGIC GO HERE */
public class ResourceServiceImpl extends PaginationBaseImpl implements ResourceService {
	public static Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
	/** Author Note: All Custom implementations go here and ResourceService > its abstraction. */
	@Autowired
	private ResourceRepository resourceRepo;
	@Autowired
	private WfmBeanUtils wfmBeanUtils;

	@Override /* @Pageable */
	public Page<Resource> getAllResources(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Resource> lister = resourceRepo.findAll(gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Resource.class));
		return lister;
	}

	@Override
	public List<Resource> getAllResources(String search) {
		List<Resource> resources = null;
		if (search != null) {
			resources = resourceRepo.findAll(resourceRepo.textInAllColumns(search));
		} else {
			resources = resourceRepo.findAll();
		}
		return resources;
	}

	@Override
	public Resource createResource(Resource resource) {
		return this.resourceRepo.save(this.wfmBeanUtils.map(resource, Resource.class));
	}

	@Override
	public Resource updateResource(String key, Long Id, Resource resourceDetails) {
		resourceRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Resource", "id", Id));
		resourceDetails.setResourceId(Id);
		return resourceRepo.save(resourceDetails);
	}

	@Override
	public void deleteResource(String key, Long Id) {
		Resource resource = resourceRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Resource", "id", Id));
		try {
			resourceRepo.delete(resource);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Resources");
		}
	}

	@Override
	public Resource findById(Long Id) {
		Resource resource = resourceRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Resource", "id", Id));
		return resource;
	}

}
