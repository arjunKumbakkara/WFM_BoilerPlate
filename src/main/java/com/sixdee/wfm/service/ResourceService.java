package com.sixdee.wfm.service;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.model.Resource;

@Service
public interface ResourceService {
	public Page<Resource> getAllResources(String page,String pageSize,String sortDirection,String sortKey,String search);
	public List<Resource> getAllResources(String search);
	public Resource createResource(Resource department);
	public Resource updateResource(String key,Long Id,Resource departmentDetails);
	public void deleteResource(String key,Long Id);
	public Resource findById(Long Id);
	

}
