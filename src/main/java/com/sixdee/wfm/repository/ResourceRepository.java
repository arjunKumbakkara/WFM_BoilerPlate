package com.sixdee.wfm.repository;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sixdee.wfm.base.GlobalRepository;
import com.sixdee.wfm.model.Department;
import com.sixdee.wfm.model.Resource;
@Repository
public interface ResourceRepository extends GlobalRepository<Resource, Long>{
	/*TextInAllColumnsSearch: This is for TextInAllColumns Search*/
	List<Resource> findAll(Specification<Resource> departments);
	/*Note: Only custom methods with crowned JPQL,Native,TypedQuery should be put up here*/
	/*Globally Customized JPA operation*/
	//Note: In native query , the table name is always the table name.But in JPQL table name is the entity name
	//Note: @Queries at method level  are better than Named Queries. (Because persistence is managed at the Repository level and not the model level.) 
	//TODO: All Overridden methods shall be put here,If Any!
	
}



