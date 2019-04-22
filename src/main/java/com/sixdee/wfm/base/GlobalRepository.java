package com.sixdee.wfm.base;

import java.io.Serializable;
import java.util.List;

/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import org.springframework.data.jpa.domain.Specification;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

//We are essentially trying to achieve generic repository design pattern.
//but : This only works if the domain classes use single table inheritance

/*@NoRepositoryBean
public interface TestBaseRepository<T, ID extends Serializable> extends Repository<T, ID> {
    Optional<T> deleteById(ID id);
    //All such commonly different methods will come here.
}*/

@NoRepositoryBean
public interface GlobalRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	public List<T> findByAttributeContainsText(String attributeName, String text);

	Specification<T> textInAllColumns(String text);

	/**
	 * @param t
	 */
	void refresh(T t);

}