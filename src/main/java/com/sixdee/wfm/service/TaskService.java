/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Task;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface TaskService {

	public Task createTask(Task task);

	public Page<Task> getAllTask(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<Task> getAllTask(String search);

	public Task findById(Long Id);

	public Task updateTask(String key, Long Id, Task updateTask);

	public void deleteTask(String key, Long Id);
}
