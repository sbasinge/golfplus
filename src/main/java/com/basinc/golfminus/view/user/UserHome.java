package com.basinc.golfminus.view.user;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.i18n.DefaultBundleKey;
import com.basinc.golfminus.security.Identity;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class UserHome implements Serializable {

	private static final long serialVersionUID = 3445299132373040937L;

	// @Inject
	private Logger log = Logger.getLogger(getClass());

	@Inject
	EntityManager entityManager;

	@Inject
	Identity identity;

	@Inject
	private Messages messages;

	private User userSelection;

	@Begin(timeout = 300000)
	public void selectUser(final String username) {

		// NOTE get a fresh reference that's managed by the extended persistence
		// context
		userSelection = entityManager.find(User.class, username);
		log.info("User Selected");
	}


	public User getUserSelection() {
		return userSelection;
	}

	public void setUserSelection(User userSelection) {
		this.userSelection = userSelection;
	}

	@End
	public void save() {
		log.info("Updating User");
		messages.info(new DefaultBundleKey("account_Changed")).defaults("Account successfully updated.");
		entityManager.persist(userSelection);
		entityManager.flush();
	}

	@End
	public void cancel() {
		userSelection = null;
		log.info("Cancel updates to User");
	}


}
