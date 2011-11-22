package com.basinc.golfminus.view.teetime;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyTeetimeDataModel extends LazyDataModel<TeeTime> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	@Override
	public List<TeeTime> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
		
		query.select(teeTime);
		List<TeeTime> results = entityManager.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);
		return results;
	}

}
