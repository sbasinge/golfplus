package com.basinc.golfminus.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.jboss.seam.international.status.Messages;
import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.ClubRole;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.i18n.DefaultBundleKey;

@Transactional
@Stateful
@SessionScoped
@Named
public class Identity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(Identity.class);
	
	@Inject Credentials credentials;

    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Inject private Messages messages;

    @Inject private HttpSession session;
    
	private User user;
	private Set<String> roles = new HashSet<String>();
	private Club selectedClub;
	
	
	public void login() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        User testUser = entityManager.find(User.class, credentials.getUsername());
        if (testUser != null && testUser.getPassword() != null && testUser.getPassword().equals(credentials.getPassword())) {
        	user=testUser;
        	if (selectedClub == null && user.getClubs().size()==1) { //eager fetch the clubs to avoid lazy init error on club selection pulldown
        		selectedClub = user.getClubs().get(0);
        	}
        	if (selectedClub != null) { //selectedClub could have been set by rest url
        		populateRoles();
        	}
            messages.info(new DefaultBundleKey("identity_loggedIn"), user.getName()).defaults("You're signed in as {0}")
            .params(user.getName());

        } else {
        	// perhaps add code here to report a failed login
            messages.error(new DefaultBundleKey("identity_loginFailed")).defaults("Invalid username or password.");

        }
	}

	public void logout() {
		session.invalidate();
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	@Produces
	@SessionScoped
    @Authenticated
    @Named("currentUser")
	public User getCurrentUser() {
		return user;
	}

    public boolean hasRole(String roleName) {
    	boolean retVal = false;
    	retVal = roles.contains(roleName);
    	return retVal;
    }
    
    public boolean isClubListVisible() {
    	boolean retVal = hasRole("Admin");
    	return retVal;
    }

    public boolean isCourseListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin");
    	return retVal;
    }

    public boolean isMemberRequestsVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin");
    	return retVal;
    }

    public boolean isTeeTimeListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member");
    	return retVal;
    }

    public boolean isTeeTimeListDeleteable() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair");
    	return retVal;
    }

    public boolean isTeeTimeListUpdateable() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair");
    	return retVal;
    }

    public boolean isScoreListVisible() {
    	boolean retVal = hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member");
    	return retVal;
    }

    public boolean isMemberListVisible() {
    	boolean retVal = isClubSelected() && (hasRole("Admin") || hasRole("ClubAdmin") || hasRole("TeeTimeChair") || hasRole("Member"));
    	return retVal;
    }

    public boolean isTournamentListVisible() {
    	boolean retVal = isScoreListVisible();
    	return retVal;
    }

    public boolean isAbleToModifyOthersScores() {
    	boolean retVal = isClubSelected() && (hasRole("Admin") || hasRole("ClubAdmin"));
    	return retVal;
    }


  @Produces
  @SessionScoped
  @Named("currentClub")
	public Club getSelectedClub() {
		return selectedClub;
	}


	public void setSelectedClub(Club selectedClub) {
		if (selectedClub != null) {
			log.info("Selected club "+selectedClub.getName());
			this.selectedClub = selectedClub;
		}
	}

	public boolean isClubSelected() {
		return selectedClub != null;
	}
	
	public void populateRoles() {
		log.info("populateRoles - Selected club "+selectedClub.getName());
    	for (ClubRole clubRole : user.getClubRoles()) {
    		if (clubRole.getClub().equals(selectedClub)) {
    			log.info("Adding role "+clubRole.getRole().getName());
    			roles.add(clubRole.getRole().getName()); 	//TODO should be based on selectedClub
    		}
    	}

	}
	
}
