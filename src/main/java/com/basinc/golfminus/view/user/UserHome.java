package com.basinc.golfminus.view.user;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.i18n.DefaultBundleKey;
import com.basinc.golfminus.security.Identity;
import com.basinc.golfminus.util.PersistenceUtil;

@Transactional
@Stateful
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class UserHome extends PersistenceUtil {

//  @Inject
  private Logger log = Logger.getLogger(getClass());

    @Inject Identity identity;

    @Inject
    private Messages messages;

    private User userSelection;
    
    @Begin
    public void selectUser(final String username) {

        // NOTE get a fresh reference that's managed by the extended persistence context
        userSelection = entityManager.find(User.class, username);
        log.info("User Selected");
    }

    @Produces
    @ConversationScoped
    @Named("user")
	public User getUserSelection() {
		return userSelection;
	}

    @End
    public void save() {
    	log.info("Updating User");
        messages.info(new DefaultBundleKey("account_Changed")).defaults("Account successfully updated.");
    	persist(userSelection);
    }
    
    @End
    public void cancel() {
    	userSelection = null;
    	log.info("Cancel updates to User");
    }

}
