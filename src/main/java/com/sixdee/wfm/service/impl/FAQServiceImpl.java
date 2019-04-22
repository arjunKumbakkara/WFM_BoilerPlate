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
import com.sixdee.wfm.model.FAQ;
import com.sixdee.wfm.repository.FAQRepository;
import com.sixdee.wfm.service.FAQService;

/**
 * @author Nalini N
 *
 *         Date : 11-Mar-2019
 */
@Component
public class FAQServiceImpl extends PaginationBaseImpl implements FAQService {
	public static Logger logger = LoggerFactory.getLogger(FAQServiceImpl.class);
	@Autowired
	private FAQRepository questionRepository;

	@Override
	public FAQ createQuestion(FAQ question) {
		logger.info("QuestionServiceImpl.createQuestion()" + question);
		return questionRepository.save(question);
	}

	@Override
	public Page<FAQ> getAllQuestion(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<FAQ> lister = questionRepository.findAll(questionRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, FAQ.class));
		return lister;
	}

	@Override
	public List<FAQ> getAllQuestion(String search) {
		List<FAQ> question = null;
		if (search != null) {
			question = questionRepository.findAll(questionRepository.textInAllColumns(search));
		} else {
			question = questionRepository.findAll();
		}
		return question;
	}

	@Override
	public FAQ findById(Long Id) {
		FAQ question = questionRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", Id));
		return question;
	}

	@Override
	public FAQ updateQuestion(String key, Long Id, FAQ updateQuestion) {

		questionRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", Id));
		updateQuestion.setQuestionId(Id);
		return questionRepository.save(updateQuestion);

	}

	@Override
	public void deleteQuestion(String key, Long Id) {
		FAQ question = questionRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Question", "id", Id));
		try {
			questionRepository.delete(question);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("FAQ's");
		}
	}
}
