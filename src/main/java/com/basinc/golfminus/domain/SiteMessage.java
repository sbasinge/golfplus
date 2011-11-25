package com.basinc.golfminus.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.jboss.solder.core.Veto;

@Entity
@Veto
public class SiteMessage extends BaseEntity {
	
	@OneToOne
	private User user;
	
	private boolean messageRead = false;
	
	private String messageText;
	
	public SiteMessage() {};
	public SiteMessage(User user, String message) {
		setUser(user);
		setMessageText(message);
	};
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public boolean isMessageRead() {
		return messageRead;
	}
	public void setMessageRead(boolean messageRead) {
		this.messageRead = messageRead;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

}
