package com.basinc.golfminus.view.tournament;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.Tournament;
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
public class TournamentList extends PersistenceUtil {
	private static Logger log = LoggerFactory.getLogger(TournamentList.class);
	
    private List<Tournament> tournaments;
    
    @Begin
    public void find() {
        queryAll();
    }

    private void queryAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tournament> query = builder.createQuery(Tournament.class);
        Root<Tournament> root = query.from(Tournament.class);
        query.select(root);
        List<Tournament> results = entityManager.createQuery(query).getResultList();
        setTournaments(results);
	}

	@Produces
    @Named(value="tournaments")
	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	@End
	public void deleteTournament(int id) {
		log.warn("Attempting to delete torunament: "+id);
		Tournament tournament = entityManager.find(Tournament.class, id);
	    getEntityManager().joinTransaction();
		entityManager.remove(tournament);
		entityManager.flush();
	}
	
//	@End
    public void addTournament() {
    	log.info("Add new Tournament");
    }

	public boolean isAvailableForDelete(int id) {
		Tournament tournament = entityManager.find(Tournament.class, id);
		return !tournament.hasScores();
	}

}
