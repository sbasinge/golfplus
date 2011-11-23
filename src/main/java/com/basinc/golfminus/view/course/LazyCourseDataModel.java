package com.basinc.golfminus.view.course;

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

import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Course_;
import com.basinc.golfminus.security.Identity;

@Model
public class LazyCourseDataModel extends LazyDataModel<Course> {
	private static final long serialVersionUID = 7902239197061428521L;

	@Inject EntityManager entityManager;
	@Inject	Identity identity;

	@Override
	public List<Course> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> courses = query.from(Course.class);
        query.select(courses);
        query.orderBy(builder.asc(courses.get(Course_.name)));
		
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
//		if ("user.name".equalsIgnoreCase(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(users.get(User_.name)));
//			else
//				query.orderBy(builder.desc(users.get(User_.name)));
//		} else if ("teeSet.course.name".equalsIgnoreCase(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(courses.get(Course_.name)));
//			else
//				query.orderBy(builder.desc(courses.get(Course_.name)));
//		} else if ("date".equalsIgnoreCase(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(scores.get(Score_.date)));
//			else
//				query.orderBy(builder.desc(scores.get(Score_.date)));
//		} else if ("teeSet.courseRating".equalsIgnoreCase(sortField)) {
//			if ("ASCENDING".equalsIgnoreCase(sortOrder.name()))
//				query.orderBy(builder.asc(teeSets.get(TeeSet_.courseRating)));
//			else
//				query.orderBy(builder.desc(teeSets.get(TeeSet_.courseRating)));
//		}
//		query.where(restrictions);
		query.select(courses);
		List<Course> results = entityManager.createQuery(query).setFirstResult(first).setMaxResults(pageSize).getResultList();
		int rowCount = entityManager.createQuery(query).getResultList().size();
		setRowCount(rowCount);
		return results;
	}

	@Override
    public Object getRowKey(Course course) {
		return course.getId();
    }

	@Override
    public Course getRowData(String rowKey) {
		return entityManager.find(Course.class, Integer.parseInt(rowKey));
    }

}
