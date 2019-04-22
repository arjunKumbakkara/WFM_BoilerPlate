/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0]
 */

package com.sixdee.wfm.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.model.Level;

@Service
public interface LevelService {

	public Level createLevel(Level level);

	public Page<Level> getAllLevel(String page, String pageSize, String sortDirection, String sortKey, String search);

	public Level findById(Long Id);

	public List<Level> getAllLevel(String search);

	public Level updateLevel(String key, Long Id, Level updateLevel);

	public void deleteLevel(String key, Long Id);
}
