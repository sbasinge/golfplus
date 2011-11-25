package com.basinc.golfminus.view.tournament;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.Tournament;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class TournamentList implements Serializable {
	private static final long serialVersionUID = 9012979567903673696L;
	private static Logger log = LoggerFactory.getLogger(TournamentList.class);
	
	@Inject
	EntityManager entityManager;

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
	
	public void deleteTournament(int id) {
		log.warn("Attempting to delete torunament: "+id);
		Tournament tournament = entityManager.find(Tournament.class, id);
		entityManager.remove(tournament);
		entityManager.flush();
	}
	
    public void addTournament() {
    	log.info("Add new Tournament");
    }

	public boolean isAvailableForDelete(int id) {
		Tournament tournament = entityManager.find(Tournament.class, id);
		return !tournament.hasScores();
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

}
