package com.basinc.golfminus.view.user;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.security.Identity;

@Stateful
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class UserList {
	
//  @Inject
  private Logger log = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Inject Identity identity;
    
    private List<User> users = new ArrayList<User>();
    
    public void find() {
    	queryUsers();
    }

    private void queryUsers() {
    	log.info("Finding members for club "+identity.getSelectedClub().getName());
    	Club club = em.find(Club.class,identity.getSelectedClub().getId());
    	List<User> results = club.getMembers();
        setUsers(results);
	}

	@Produces
    @Named(value="users")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users.clear();
		for (User user : users) {
			this.users.add(user);
		}
	}

}
