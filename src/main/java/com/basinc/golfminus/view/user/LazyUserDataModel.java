package com.basinc.golfminus.view.user;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.Club_;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.domain.User_;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyUserDataModel extends LazyDataModel<User> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> users = query.from(User.class);
		ListJoin<User, Club> clubs = users.join(User_.clubs);
//		ListJoin<User, Handicap> handicaps = users.join(User_.handicapHistory);
//		
//		Subquery<Date> subQuery = query.subquery(Date.class);
//		subQuery.from(Handicap.class);
		
		
		Predicate restrictions = builder.conjunction();
		restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id), identity.getSelectedClub().getId()));
		
//		if (!identity.hasRole("Admin")) {
//			restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id), identity.getSelectedClub().getId()));
//			if (!identity.isAbleToModifyOthersScores()) {
//				restrictions.getExpressions().add(builder.equal(users.get(User_.username), identity.getCurrentUser().getUsername()));
//			}
//		}
//		if (filters.containsKey("user.name")) {
//			restrictions.getExpressions().add(builder.like(builder.lower(users.get(User_.name)), filters.get("user.name")+"%"));
//		}
		if ("name".equalsIgnoreCase(sortField)) {
			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
				query.orderBy(builder.asc(users.get(User_.name)));
			else
				query.orderBy(builder.desc(users.get(User_.name)));
//		} else if ("handicap".equalsIgnoreCase(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(users.get(User_.h)));
//			else
//				query.orderBy(builder.desc(courses.get(Course_.name)));
		}
		query.where(restrictions);
		query.select(users);
		List<User> results = entityManager.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);

		return results;
	}

}
