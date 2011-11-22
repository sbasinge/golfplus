package com.basinc.golfminus.view.tournament;

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

import com.basinc.golfminus.domain.Tournament;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyTournamentDataModel extends LazyDataModel<Tournament> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	@Override
	public List<Tournament> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tournament> query = builder.createQuery(Tournament.class);
        Root<Tournament> root = query.from(Tournament.class);
        query.select(root);
        List<Tournament> results = entityManager.createQuery(query).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);
		return results;
	}

}
