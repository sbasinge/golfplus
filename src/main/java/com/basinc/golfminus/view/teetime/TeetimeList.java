package com.basinc.golfminus.view.teetime;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
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
public class TeetimeList extends PersistenceUtil {
	
	private Logger log = Logger.getLogger(getClass());

	@Inject Identity identity;

    @Inject
    @TeeTimeDeleted
    private Event<TeeTime> teeTimeDeletedEventSrc;

    @Inject
    @TeeTimeUpdated
    private Event<TeeTimeParticipant> teeTimeUpdatedEventSrc;

    private List<TeeTime> teeTimes;
    
    @Begin
    public void find() {
        queryTeeTimes();
    }

    private void queryTeeTimes() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
        
        List<TeeTime> results = entityManager.createQuery(query).getResultList();
        setTeeTimes(results);
	}

	public void deleteTeeTime(int id) {
		log.warn("Attempting to delete teetime: "+id);
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
	    getEntityManager().joinTransaction();
		entityManager.remove(teeTime);
		entityManager.flush();
		teeTimeDeletedEventSrc.fire(teeTime);
	}

	public String signUp(int id) {
		log.warn("Attempting to sign up for tee time: "+id);
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
	    getEntityManager().joinTransaction();
	    TeeTimeParticipant participant = new TeeTimeParticipant(teeTime,identity.getCurrentUser());
	    teeTime.getParticipants().add(participant);
	    teeTimeUpdatedEventSrc.fire(participant);
	    return "";
	}

	@End
    public void addTeeTime() {
    	log.info("Add new Tee Time");
    }

	/**
	 * Show the signup link if the user is not already signed up and the teetime is in the future.
	 */
	public boolean isAvailableForSignup(int id) {
		boolean retVal = false;
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
	    retVal = !teeTime.isUserSignedUp(identity.getCurrentUser());
	    return retVal;
	}

	public boolean isAvailableForEnterScores(int id) {
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
		return !teeTime.hasScores();
	}
	
	public boolean isAvailableForDelete(int id) {
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
		return !teeTime.hasScores();
	}

	public List<TeeTime> getTeeTimes() {
		return teeTimes;
	}

	public void setTeeTimes(List<TeeTime> teeTimes) {
		this.teeTimes = teeTimes;
	}

}
