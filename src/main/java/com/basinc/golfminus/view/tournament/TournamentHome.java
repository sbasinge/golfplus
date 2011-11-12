package com.basinc.golfminus.view.tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.TeeTime_;
import com.basinc.golfminus.domain.Tournament;
import com.basinc.golfminus.security.Identity;
import com.basinc.golfminus.util.PersistenceUtil;

@Transactional
@Stateful
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class TournamentHome extends PersistenceUtil {
	private static Logger log = Logger.getLogger(TournamentHome.class);

    @Inject Identity identity;

    private Tournament selection;
    private List<PlayerScores> playerScores = new ArrayList<PlayerScores>();
    private List<TeeTime> availableTeeTimes = new ArrayList<TeeTime>();
    private List<TeeTime> selectedTeeTimes = new ArrayList<TeeTime>();
    
    @Begin
    public void selectTournament(final Integer id) {
        // NOTE get a fresh reference that's managed by the extended persistence context
    	selection = entityManager.find(Tournament.class, id.intValue());
    	populateAvailableTeeTimes();
    }

    
    public void populateAvailableTeeTimes() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
        Join<TeeTime, Tournament> torunament = teeTime.join(TeeTime_.tournament, JoinType.LEFT);

        Predicate restrictions = builder.conjunction();
        Path<Date> ttDate = teeTime.get(TeeTime_.date);
//       	restrictions.getExpressions().add(builder.greaterThanOrEqualTo(ttDate,Calendar.getInstance().getTime()));
       	restrictions.getExpressions().add(builder.isNull(torunament));
        query.where(restrictions);

        availableTeeTimes = entityManager.createQuery(query).getResultList();
    	
	}


	@Produces
    @RequestScoped
    @Named("tournament")
	public Tournament getSelection() {
		return selection;
	}

    @End
    public void save() {
    	entityManager.joinTransaction();
    	for (TeeTime teeTime : getSelectedTeeTimes()) {
    		TeeTime selectedTeeTime = entityManager.find(TeeTime.class, teeTime.getId());  //get a fresh reference
    		selection.addTeeTime(selectedTeeTime);
    	}
    	entityManager.persist(selection);
    }
    
    @End
    public void cancel() {
    	selection = null;
    	log.info("Cancel updates to Tournament");
    }

	public void populateLeaderboard() {
		if (selection != null) {
			int teeTimeCounter = 0;
			for (TeeTime teeTime : selection.getTeeTimes()) {
				// a player may have played previous rounds but not this one,
				// or they haven't played previous rounds but played this one.
				for (TeeTimeParticipant participant : teeTime.getParticipants()) {
					PlayerScores foundPlayersheet = null;
					for (PlayerScores playersheet : playerScores) {
						if (playersheet.getUser().equals(participant.getUser())) {
							foundPlayersheet = playersheet;
							break;
						}
					}
					if (foundPlayersheet != null) {
						//add a teetimeparticipant to the sheet.
						foundPlayersheet.addScore(participant);
					} else {
						foundPlayersheet = new PlayerScores();
						foundPlayersheet.setUser(participant.getUser());
						if (teeTimeCounter > 0) {
							//the player didn't participate in previous rounds.  Add null entries?
							for (int i=0; i < teeTimeCounter; i++) {
								foundPlayersheet.addScore(null);
							}
						}
						foundPlayersheet.addScore(participant);
						playerScores.add(foundPlayersheet);
					}
				}
				teeTimeCounter++;
			}
		}
		//sort them by total asc
		Collections.sort(playerScores);
	}

	public List<PlayerScores> getPlayerScores() {
		return playerScores;
	}

	public void setPlayerScores(List<PlayerScores> playerScores) {
		this.playerScores = playerScores;
	}
	
	public void newTournament() {
		selection = new Tournament();
		populateAvailableTeeTimes();
	}


	public List<TeeTime> getAvailableTeeTimes() {
		return availableTeeTimes;
	}


	public void setAvailableTeeTimes(List<TeeTime> availableTeeTimes) {
		this.availableTeeTimes = availableTeeTimes;
	}


	public List<TeeTime> getSelectedTeeTimes() {
		return selectedTeeTimes;
	}


	public void setSelectedTeeTimes(List<TeeTime> selectedTeeTimes) {
		this.selectedTeeTimes = selectedTeeTimes;
	}

}
