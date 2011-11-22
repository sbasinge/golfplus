package com.basinc.golfminus.view.tournament;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.transaction.Transactional;
import org.primefaces.model.LazyDataModel;
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
	
    @Inject private LazyDataModel<Tournament> lazyDataModel;

	public void deleteTournament(int id) {
		log.warn("Attempting to delete torunament: "+id);
		Tournament tournament = entityManager.find(Tournament.class, id);
	    getEntityManager().joinTransaction();
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

	public LazyDataModel<Tournament> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Tournament> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}

}
