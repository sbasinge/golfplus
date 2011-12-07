package com.basinc.golfminus.view.club;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.security.Identity;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class ClubList implements Serializable {
	
	private static final long serialVersionUID = -9064374876336583036L;

	private Logger log = Logger.getLogger(getClass());

	@Inject
	EntityManager entityManager;

	@Inject Identity identity;

    private List<Club> clubs;
    
    private String clubName;
    
    public void find() {
        queryClubs();
    }

    private void queryClubs() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Club> query = builder.createQuery(Club.class);
        Root<Club> club = query.from(Club.class);
        query.select(club);
        List<Club> results = entityManager.createQuery(query).getResultList();
        setClubs(results);
//        lazyDataModel = new LazyLoadClubModel();
	}

	public List<Club> getClubs() {
		return clubs;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public void populateClub() {
		if (getClubName() != null) {
			Club chosenClub = entityManager.createNamedQuery("findClubByName",Club.class).setParameter("name", getClubName()).getSingleResult();
			if (chosenClub != null)
				identity.setSelectedClub(chosenClub);
		}
	}

}
