package com.sixdee.wfm.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.sixdee.wfm.WFMApplication;
import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.service.impl.DepartmentServiceImpl;

/**
 * @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
 */

public class PaginationBaseImpl implements PaginationBase {
	public static Logger logger = LoggerFactory.getLogger(PaginationBaseImpl.class);

	@Autowired
	private Environment env;
	@Autowired
	private WfmBeanUtils wfmBeanUtils;
	
	
	@Override
	public PageRequest gotoPageWithConstraints(String page, String pageSize, String sortDirection, String sortKey,@SuppressWarnings("rawtypes") Class domain) {
		PageRequest request=null;
		int pageNumber=0;
		int pageSizeValue=0;
		
		try {
			if(page!=null && Integer.parseInt(page)==0) {
				logger.info("PaginationBaseImpl: page param found as 0 , falling back to default as 1");
				pageNumber=1;
			}else if(page!=null){
				pageNumber=Integer.parseInt(page);
			}
			
			if(pageSize!=null && Integer.parseInt(pageSize)==0) {
				logger.info("PaginationBaseImpl: pageSize param found as 0 , falling back to default as 1");
				pageSizeValue=Integer.parseInt(""+env.getProperty("spring.data.web.pageable.default-page-size"));
			}else if(page!=null){
				pageSizeValue=Integer.parseInt(pageSize);
			}
			
			if(sortKey==null) {
				sortKey=""+wfmBeanUtils.getFieldName(domain);
				logger.info("No sortKey obtained thus falling back onto :"+sortKey+" of the supplied Domain :"+domain.getSimpleName());
			}
			if(sortDirection!=null && sortDirection.equalsIgnoreCase("asc")) {
				request= PageRequest.of(pageNumber-1,pageSizeValue, Sort.by(Sort.Direction.ASC,sortKey));
			}else if(sortDirection!=null && sortDirection.equalsIgnoreCase("desc")) {
				request = PageRequest.of(pageNumber-1,pageSizeValue, Sort.by(Sort.Direction.DESC,sortKey));	
			}else {
				request= PageRequest.of(pageNumber-1,pageSizeValue, Sort.by(Sort.Direction.ASC,sortKey));
			}	
		}catch (Exception e) {
			logger.info("Invalid Page / Page size attempted.Please Review");
			e.printStackTrace();
		}
		return request;
	}

}
