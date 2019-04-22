
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
import com.sixdee.wfm.model.Level;
import com.sixdee.wfm.repository.LevelRepository;
import com.sixdee.wfm.service.LevelService;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

@Component
public class LevelServiceImpl extends PaginationBaseImpl implements LevelService {
	public static Logger logger = LoggerFactory.getLogger(LevelServiceImpl.class);
	@Autowired
	private LevelRepository levelRepository;

	@Override
	public Level createLevel(Level level) {
		logger.info("LevelServiceImpl.createLevel()" + level);
		return levelRepository.save(level);
	}

	@Override /* @Pageable */
	public Page<Level> getAllLevel(String page, String pageSize, String sortDirection, String sortKey, String search) {
		Page<Level> lister = levelRepository.findAll(levelRepository.textInAllColumns(search), gotoPageWithConstraints(page, pageSize, sortDirection, sortKey, Level.class));
		return lister;
	}

	@Override
	public List<Level> getAllLevel(String search) {
		List<Level> level = null;
		if (search != null) {
			level = levelRepository.findAll(levelRepository.textInAllColumns(search));
		} else {
			level = levelRepository.findAll();
		}
		return level;
	}

	@Override
	public Level findById(Long Id) {
		Level level = levelRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Level", "id", Id));
		return level;
	}

	@Override
	public Level updateLevel(String key, Long Id, Level updateLevel) {

		levelRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Level", "id", Id));
		updateLevel.setLevelId(Id);
		return levelRepository.save(updateLevel);

	}

	@Override
	public void deleteLevel(String key, Long Id) {
		Level level = levelRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Level", "id", Id));
		try {
			levelRepository.delete(level);
		} catch (Exception e) {
			logger.error("Exception raised while deleting the entry in table ");
			throw new DataViolationException("Level");
		}
	}
}
