package com.basinc.golfminus.view.scores;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.primefaces.model.LazyDataModel;

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
	
  private Logger log = Logger.getLogger(getClass());

    @Inject Identity identity;
    @Inject private LazyDataModel<Score> lazyDataModel;

    private List<Score> scores;
    
    /**
     * Start a conversation here and never end it, it will timeout in default of 5 seconds.
     */
    @Begin
    public void find() {
    	queryScores();
    }

    /**
     * TODO this really should limit the results based on the current user's role
     * admin can see all.
     * clubadmin can see all for their club
     * members can only see their own.
     * 
     * So we should lookup currentUser role and club to apply the limiting criteria.
     */
    private void queryScores() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Score> query = builder.createQuery(Score.class);
        Root<Score> score = query.from(Score.class);
        Join<Score, User> users = score.join(Score_.user);
        ListJoin<User, Club> clubs = users.join(User_.clubs);
        
        Predicate restrictions = builder.conjunction();
        if (!identity.hasRole("Admin")) {
        	restrictions.getExpressions().add(builder.equal(clubs.get(Club_.id),identity.getSelectedClub().getId()));
        	if (!identity.isAbleToModifyOthersScores()) {
        		restrictions.getExpressions().add(builder.equal(users.get(User_.username),identity.getCurrentUser().getUsername()));
        	}
        }
        query.where(restrictions);
        query.select(score);
        List<Score> results = entityManager.createQuery(query).getResultList();
        setScores(results);
	}

    @Produces
    @Named("scores")
	public List<Score> getScores() {
		return scores == null ? new ArrayList<Score>() : scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public void deleteScore(int id) {
		log.warn("Attempting to delete score: "+id);
		Score score2 = entityManager.find(Score.class, id);
	    getEntityManager().joinTransaction();
	    if (scores != null)	
	    	scores.remove(score2);
		entityManager.remove(score2);
		entityManager.flush();
	}
	
	@End
    public void addScore() {
    	log.info("Add new  score");
    }

	public LazyDataModel<Score> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Score> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

}
