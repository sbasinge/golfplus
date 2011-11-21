package com.basinc.golfminus.view.club;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.logging.Logger;
import org.jboss.seam.transaction.Transactional;
import org.primefaces.model.LazyDataModel;

import com.basinc.golfminus.domain.Club;
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
public class ClubList extends PersistenceUtil {
	
//    @Inject
    private Logger log = Logger.getLogger(getClass());

	@Inject Identity identity;

    private List<Club> clubs;
    
    private String clubName;
    
    @Inject private LazyDataModel<Club> lazyDataModel;
    
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

	@Produces
    @Named(value="clubs")
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
			identity.setSelectedClub(chosenClub);
		}
	}

	public LazyDataModel<Club> getLazyDataModel() {
		return lazyDataModel;
	}

	public void setLazyDataModel(LazyDataModel<Club> lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
}
