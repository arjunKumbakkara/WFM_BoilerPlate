/**
 * 
	
 */
package com.sixdee.wfm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.exception.DataViolationException;
import com.sixdee.wfm.exception.ResourceNotFoundException;
import com.sixdee.wfm.model.Task;
import com.sixdee.wfm.repository.TaskRepository;
import com.sixdee.wfm.service.TaskService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class TaskServiceImpl extends PaginationBaseImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task createTask(Task task) {
		System.out.println("TaskServiceImpl.createTask()" + task);
		return taskRepository.save(task);
	}

	@Override /* @Pageable */
	public Page<Task> getAllTask(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Task> lister = taskRepository.findAll(taskRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Task.class));
		return lister;
	}

	@Override
	public List<Task> getAllTask(String search) {
		List<Task> departments = null;
		if (search != null) {
			departments = taskRepository.findAll(taskRepository.textInAllColumns(search));
		} else {
			departments = taskRepository.findAll();
		}
		return departments;
	}

	@Override
	public Task findById(Long Id) {
		Task task = taskRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", Id));
		return task;
	}

	@Override
	public Task updateTask(String key, Long Id, Task updateTask) {

		taskRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", Id));
		updateTask.setTaskId(Id);
		return taskRepository.save(updateTask);

	}

	@Override
	public void deleteTask(String key, Long Id) {
		Task task = taskRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Task", "id", Id));
		try {
			taskRepository.delete(task);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Task");
		}
	}

}
