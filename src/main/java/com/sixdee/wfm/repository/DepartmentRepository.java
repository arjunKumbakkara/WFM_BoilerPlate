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



@Repository
public interface DepartmentRepository extends GlobalRepository<Department, Long>{
	
	/*TextInAllColumnsSearch: This is for TextInAllColumns Search*/
	List<Department> findAll(Specification<Department> departments);
	/*Note: Only custom methods with crowned JPQL,Native,TypedQuery should be put up here*/
	/*Globally Customized JPA operation*/
	
	//Note: In native query , the table name is always the table name.But in JPQL table name is the entity name
	//Note: @Queries at method level  are better than Named Queries. (Because persistence is managed at the Repository level and not the model level.) 
	Optional<Department> findById(Long id);
	String Id="2";
	@Query(
			  value = "select  department_id,department_name,department_desc,create_date,update_date  from WFM_Departments  where department_id ='"+Id+"'", 
			  nativeQuery = true)
	Collection<Department> findAllActiveUsersNative();
    
    /*@Query(getAllDepartmentsPaged)
    List<Department> getAllPostsByRank(Pageable pageable);
    final String getAllDepartmentsPaged= "select * from WFM_Departments order by value department_id";
*/
//	List<Department> fetchByLastNameLength(@Param("length") Long length);
/*	//@Query("select  departmentId,departmentName,departmentDesc,createDate,updateDate  from Department  where departmentName like '%DSL%'")
	List<Department> customDepartment();*/
	
	/*List<Department> findByName(String name);*/
	/*List<Department> countByName(String name);
	List<Department> findByNameOrderByIdDesc(String name);
	List<Department> deleteByName(String name);
	
	
	@Query("Select  c  From Wfm_departments c where name like '%100 Steps'")
	List<Department> customDepartment();
	

	@Query(value = "Select  *  From Course c where name like '%100 Steps'", nativeQuery = true)
	List<Department> customDepartmentNativeQuery();

	@Query(name = "query_get_100_Step_courses")
	List<Department> customDepartmentUsingNamedQuery();
	*/
	
}



