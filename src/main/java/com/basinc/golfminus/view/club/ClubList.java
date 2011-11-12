package com.basinc.golfminus.view.club;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;

import com.basinc.golfminus.domain.Club;

@Stateful
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class ClubList {
	
//    @Inject
    private Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    private List<Club> clubs;
    
    public void find() {
        queryClubs();
    }

    private void queryClubs() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Club> query = builder.createQuery(Club.class);
        Root<Club> club = query.from(Club.class);
        query.select(club);
        List<Club> results = em.createQuery(query).getResultList();
        setClubs(results);
	}

	@Produces
    @Named(value="clubs")
	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

}
