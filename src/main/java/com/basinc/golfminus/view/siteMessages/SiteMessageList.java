package com.basinc.golfminus.view.siteMessages;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jboss.seam.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.SiteMessage;
import com.basinc.golfminus.domain.SiteMessage_;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.domain.User_;
import com.basinc.golfminus.security.Identity;
import com.basinc.golfminus.util.PersistenceUtil;

@Transactional
@Stateless
@Named
public class SiteMessageList extends PersistenceUtil {
	private static Logger log = LoggerFactory.getLogger(SiteMessageList.class);
	
    @Inject Identity identity;

    private List<SiteMessage> messages;
    
    public void find() {
    	queryMessages();
    }

    private void queryMessages() {
    	if (identity != null && identity.isLoggedIn()) {
    		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    		CriteriaQuery<SiteMessage> query = builder.createQuery(SiteMessage.class);
    		Root<SiteMessage> root = query.from(SiteMessage.class);
    		Join<SiteMessage, User> users = root.join(SiteMessage_.user);
    		
    		Predicate restrictions = builder.conjunction();
    		restrictions.getExpressions().add(builder.isFalse(root.get(SiteMessage_.messageRead)));
    		restrictions.getExpressions().add(builder.equal(users.get(User_.username),identity.getCurrentUser().getUsername()));
    		query.where(restrictions);
    		query.select(root);
    		List<SiteMessage> results = entityManager.createQuery(query).getResultList();
    		setMessages(results);
    	} else {
    		log.info("Not querying messages as user is not logged in.");
    	}
	}

    @Produces
    @Named("siteMessages")
	public List<SiteMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<SiteMessage> messages) {
		this.messages = messages;
	}

	public void markMessageRead(int messageId) {
		log.warn("Marking message {} as read.",messageId);
		entityManager.joinTransaction();
		SiteMessage message = entityManager.find(SiteMessage.class, messageId);
		message.setMessageRead(true);
		messages.remove(message);
		entityManager.persist(message);
		entityManager.flush();
		queryMessages();
	}

}