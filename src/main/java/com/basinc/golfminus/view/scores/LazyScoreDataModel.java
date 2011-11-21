package com.basinc.golfminus.view.scores;

import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.Club_;
import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Course_;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.Score_;
import com.basinc.golfminus.domain.TeeSet;
import com.basinc.golfminus.domain.TeeSet_;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.domain.User_;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyScoreDataModel extends LazyDataModel<Score> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	@Override
	public List<Score> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Score> query = builder.createQuery(Score.class);
		Root<Score> scores = query.from(Score.class);
		Join<Score, User> users = scores.join(Score_.user);
		ListJoin<User, Club> clubs = users.join(User_.clubs);
		Join<Score, TeeSet> teeSets = scores.join(Score_.teeSet);
		Join<TeeSet, Course> courses = teeSets.join(TeeSet_.course);
		
		Predicate restrictions = builder.conjunction();
		if (!identity.hasRole("Admin")) {
			restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id), identity.getSelectedClub().getId()));
			if (!identity.isAbleToModifyOthersScores()) {
				restrictions.getExpressions().add(builder.equal(users.get(User_.username), identity.getCurrentUser().getUsername()));
			}
		}
		if (filters.containsKey("user.name")) {
			restrictions.getExpressions().add(builder.like(builder.lower(users.get(User_.name)), filters.get("user.name")+"%"));
		}
		if ("user.name".equalsIgnoreCase(sortField)) {
			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
				query.orderBy(builder.asc(users.get(User_.name)));
			else
				query.orderBy(builder.desc(users.get(User_.name)));
		} else if ("teeSet.course.name".equalsIgnoreCase(sortField)) {
			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
				query.orderBy(builder.asc(courses.get(Course_.name)));
			else
				query.orderBy(builder.desc(courses.get(Course_.name)));
		} else if ("date".equalsIgnoreCase(sortField)) {
			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
				query.orderBy(builder.asc(scores.get(Score_.date)));
			else
				query.orderBy(builder.desc(scores.get(Score_.date)));
		} else if ("teeSet.courseRating".equalsIgnoreCase(sortField)) {
			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
				query.orderBy(builder.asc(teeSets.get(TeeSet_.courseRating)));
			else
				query.orderBy(builder.desc(teeSets.get(TeeSet_.courseRating)));
		}
		query.where(restrictions);
		query.select(scores);
		List<Score> results = entityManager.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);
		return results;
	}

}
