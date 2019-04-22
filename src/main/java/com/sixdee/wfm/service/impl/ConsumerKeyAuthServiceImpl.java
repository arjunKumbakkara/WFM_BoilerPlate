package com.sixdee.wfm.service.impl;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sixdee.wfm.common.PaginationBaseImpl;
import com.sixdee.wfm.service.ConsumerKeyAuthService;


@Service
public class ConsumerKeyAuthServiceImpl implements ConsumerKeyAuthService {
	public static Logger logger = LoggerFactory.getLogger(ConsumerKeyAuthServiceImpl.class);
	//TODO : Implement Consumer-Key Verification logic
	@Override
	public boolean isAdmin(String key) {
		return "admin".equals(key);
	}

}
