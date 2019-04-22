package com.sixdee.wfm.common;

import org.springframework.data.domain.PageRequest;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

public interface PaginationBase {
	PageRequest gotoPageWithConstraints(String page,String pageSize,String sortDirection,String sortKey,@SuppressWarnings("rawtypes") Class domain);
}
