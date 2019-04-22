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
import com.sixdee.wfm.model.Project;
import com.sixdee.wfm.repository.ProjectRepository;
import com.sixdee.wfm.service.ProjectService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class ProjectServiceImpl extends PaginationBaseImpl implements ProjectService {
	public static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project createProject(Project project) {
		logger.info("ProjectServiceImpl.createProject()" + project);
		return projectRepository.save(project);
	}

	@Override /* @Pageable */
	public Page<Project> getAllProject(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Project> lister = projectRepository.findAll(projectRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Project.class));
		return lister;
	}

	@Override
	public List<Project> getAllProject(String search) {
		List<Project> departments = null;
		if (search != null) {
			departments = projectRepository.findAll(projectRepository.textInAllColumns(search));
		} else {
			departments = projectRepository.findAll();
		}
		return departments;
	}

	@Override
	public Project findById(Long Id) {
		Project project = projectRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", Id));
		return project;
	}

	@Override
	public Project updateProject(String key, Long Id, Project updateProject) {

		projectRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", Id));
		updateProject.setProjectId(Id);
		return projectRepository.save(updateProject);

	}

	@Override
	public void deleteProject(String key, Long Id) {
		Project project = projectRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", Id));
		try {
			projectRepository.delete(project);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Project");
		}

	}
}
