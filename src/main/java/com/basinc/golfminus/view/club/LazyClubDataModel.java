package com.basinc.golfminus.view.club;

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

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyClubDataModel extends LazyDataModel<Club> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	@Override
	public List<Club> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Club> query = builder.createQuery(Club.class);
		Root<Club> clubs = query.from(Club.class);

//		Predicate restrictions = builder.conjunction();
//		if (!identity.hasRole("Admin")) {
//			restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id), identity.getSelectedClub().getId()));
//			if (!identity.isAbleToModifyOthersScores()) {
//				restrictions.getExpressions().add(builder.equal(users.get(User_.username), identity.getCurrentUser().getUsername()));
//			}
//		}
//		if (filters.containsKey("user.name")) {
//			restrictions.getExpressions().add(builder.like(builder.lower(users.get(User_.name)), filters.get("user.name")+"%"));
//		}
//		if ("user.name".equals(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(users.get(User_.name)));
//			else
//				query.orderBy(builder.desc(users.get(User_.name)));
//		}
//		query.where(restrictions);
		query.select(clubs);
		List<Club> results = entityManager.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);

		return results;
	}

}
