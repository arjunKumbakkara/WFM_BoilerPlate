package com.sixdee.wfm.base;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class GlobalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GlobalRepository<T, ID>, JpaSpecificationExecutor<T> {
	public static Logger logger = LoggerFactory.getLogger(GlobalRepositoryImpl.class);
	/*
	 * @PersistenceUnit
	 * 
	 * @Qualifier(value = "entityManagerFactoryOne") EntityManagerFactory entityManagerFactory;
	 * 
	 * @Autowired EntityManager entityManager = entityManagerFactory.createEntityManager();
	 */

	private EntityManager entityManager;

	/*
	 *     public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {         super(entityInformation, entityManager);         this.entityManager =
	 * entityManager;     }
	 */
	public GlobalRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void refresh(T t) {
		entityManager.refresh(t);
	}

	/** Globally set custom repository method:Search Attribute wise */
	@Override
	@Transactional
	public List<T> findByAttributeContainsText(String attributeName, String text) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());
		Root<T> root = cQuery.from(getDomainClass());
		cQuery.select(root).where(builder.like(root.<String>get(attributeName), "%" + text + "%"));
		TypedQuery<T> query = entityManager.createQuery(cQuery);
		return query.getResultList();
	}

	@SuppressWarnings("serial")
	public Specification<T> textInAllColumns(String text) {
		if (text == null) {
			text = "";
		}
		if (!text.contains("%")) {
			text = "%" + text + "%";
		}
		final String finalText = text;
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
				return builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a -> {
					if (a.getJavaType().getSimpleName().equalsIgnoreCase("string")) {
						return true;
					} else {
						return false;
					}
				}).map(a -> builder.like(root.get(a.getName()), finalText)).toArray(Predicate[]::new));
			}
		};
	}

	@SuppressWarnings("serial")
	public Specification<T> textInJoinedColumns(String text, String joinColumn) {
		if (!text.contains("%")) {
			text = "%" + text + "%";
		}
		final String finalText = text;
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder builder) {
				Join<T, T> joinCombination = root.join(joinColumn);
				return builder.or(root.getModel().getDeclaredSingularAttributes().stream().filter(a -> {
					if (a.getJavaType().getSimpleName().equalsIgnoreCase("string")) {
						return true;
					} else {
						return false;
					}
				}).map(a -> builder.like(root.get(a.getName()), finalText)).toArray(Predicate[]::new));
			}
		};
	}

}