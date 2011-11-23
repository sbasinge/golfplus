package com.basinc.golfminus.view.user;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.User;
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
public class UserList extends PersistenceUtil {
	
//  @Inject
  private Logger log = Logger.getLogger(getClass());

    @Inject Identity identity;
    
    private List<User> users;
    
    @Begin
    public void find() {
    	queryUsers();
    }

    private void queryUsers() {
    	log.info("Finding members for club "+identity.getSelectedClub().getName());
    	Club club = entityManager.find(Club.class,identity.getSelectedClub().getId());
    	List<User> results = club.getMembers();
        setUsers(results);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
