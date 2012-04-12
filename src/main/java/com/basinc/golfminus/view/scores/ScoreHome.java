package com.basinc.golfminus.view.scores;

import java.io.Serializable;
import java.util.Calendar;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.security.Identity;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class ScoreHome implements Serializable {
	private static final long serialVersionUID = 2284573513592942656L;

	private Logger log = Logger.getLogger(getClass());

	@Inject
	EntityManager entityManager;

	@Inject
	Identity identity;

	@Inject
	@ScoreUpdated
	private Event<Score> scoreUpdatedEventSrc;

	private Score scoreSelection;

	@Begin(timeout = 300000)
	public void selectScore(final String idStr) {
    	Integer id = Integer.parseInt(idStr);

		log.info("selecting score " + id);

		// NOTE get a fresh reference that's managed by the extended persistence
		// context
		scoreSelection = entityManager.find(Score.class, id);
	}

	@Produces
	@ConversationScoped
	@Named("score")
	public Score getScoreSelection() {
		return scoreSelection;
	}

	public void setScoreSelection(Score score) {
		scoreSelection = score;
	}

	@End
	public void save() {
		entityManager.persist(scoreSelection);
		entityManager.flush();
		scoreUpdatedEventSrc.fire(scoreSelection);
	}

	@End
	public void cancel() {
		scoreSelection = null;
		log.info("Cancel updates to score");
	}

	@Begin
	public void newScore() {
		Score score = new Score();
		score.setDate(Calendar.getInstance().getTime());
		// score.setGrossScore(0);
		// score.setNetScore(0);
		setScoreSelection(score);
		if (!identity.isAbleToModifyOthersScores()) {
			score.setUser(identity.getCurrentUser());
		}
	}
}
