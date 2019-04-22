/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Project;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface ProjectService {

	public Project createProject(Project project);

	public Page<Project> getAllProject(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Project> getAllProject(String search);

	public Project findById(Long Id);

	public Project updateProject(String key, Long Id, Project updateProject);

	public void deleteProject(String key, Long Id);
}
