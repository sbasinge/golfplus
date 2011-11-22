package com.basinc.golfminus.view.scores;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.transaction.Transactional;
import org.primefaces.model.LazyDataModel;

import com.basinc.golfminus.domain.Score;
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

	public void deleteScore(int id) {
		log.warn("Attempting to delete score: "+id);
		Score score2 = entityManager.find(Score.class, id);
	    getEntityManager().joinTransaction();
		entityManager.remove(score2);
		entityManager.flush();
	}
	
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
