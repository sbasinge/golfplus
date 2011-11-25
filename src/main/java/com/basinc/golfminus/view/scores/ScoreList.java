package com.basinc.golfminus.view.scores;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.Club_;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.Score_;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.domain.User_;
import com.basinc.golfminus.security.Identity;
import com.basinc.golfminus.util.PersistenceUtil;

@Transactional
@Stateful
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class ScoreList extends PersistenceUtil {

	private static final long serialVersionUID = 7971094658288348790L;

	private Logger log = Logger.getLogger(getClass());

	@Inject Identity identity;

	private List<Score> scores;

	/**
	 * Start a conversation here so the pagination table will continue to use the found data.
	 */
	@Begin
	public void find() {
		queryScores();
	}

	private void queryScores() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Score> query = builder.createQuery(Score.class);
		Root<Score> score = query.from(Score.class);
		Join<Score, User> users = score.join(Score_.user);
		ListJoin<User, Club> clubs = users.join(User_.clubs);

		Predicate restrictions = builder.conjunction();
		if (!identity.hasRole("Admin")) {
			restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id), identity.getSelectedClub().getId()));
			if (!identity.isAbleToModifyOthersScores()) {
				restrictions.getExpressions().add(builder.equal(users.get(User_.username), identity.getCurrentUser().getUsername()));
			}
		}
		query.where(restrictions);
		query.select(score);
		List<Score> results = entityManager.createQuery(query).getResultList();
		setScores(results);
	}

	public void deleteScore(int id) {
		log.warn("Attempting to delete score: " + id);
		Score score2 = entityManager.find(Score.class, id);
		getEntityManager().joinTransaction();
		entityManager.remove(score2);
		entityManager.flush();
		queryScores();
	}

	@End
	public void addScore() {
		log.info("Add new  score");
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

}
