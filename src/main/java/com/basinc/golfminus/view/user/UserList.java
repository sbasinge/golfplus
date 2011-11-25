package com.basinc.golfminus.view.user;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.security.Authenticated;
import com.basinc.golfminus.view.club.HandicapCalculated;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class UserList implements Serializable {

	private static final long serialVersionUID = 2058418239177449283L;

	private Logger log = LoggerFactory.getLogger(UserList.class);

	@Inject
	EntityManager entityManager;

	@Inject
	@Authenticated
	Club currentClub;

	@Inject
	@HandicapCalculated
	private Event<User> handicapCalculatedEventSrc;

	private List<User> users;

	@Begin(timeout = 300000)
	public void find() {
		queryUsers();
	}

	private void queryUsers() {
		log.info("Finding members for club " + currentClub.getName());
		Club club = entityManager.find(Club.class, currentClub.getId());
		List<User> results = club.getMembers();
		setUsers(results);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void calculateHandicap(User user) {
		log.info("Calculating handicap for {}.", user.getName());
		if (user.calculateHandicap()) {
			entityManager.flush();
			entityManager.refresh(user);
			handicapCalculatedEventSrc.fire(user);
			queryUsers();
		}
	}

}
