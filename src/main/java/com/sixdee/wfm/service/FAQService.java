/**
 * 
	
 */
package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.FAQ;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Service
public interface FAQService {

	public FAQ createQuestion(FAQ question);

	public Page<FAQ> getAllQuestion(String page, String pageSize, String sortDirection, String sortKey, String search);

	public List<FAQ> getAllQuestion(String search);

	public FAQ findById(Long Id);

	public FAQ updateQuestion(String key, Long Id, FAQ updateQuestion);

	public void deleteQuestion(String key, Long Id);

}
