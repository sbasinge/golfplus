package com.basinc.golfminus.domain;

import javax.persistence.Entity;

import org.jboss.seam.solder.core.Veto;

@Entity
@Veto
public class NotificationPreference extends BaseEntity {
	private boolean notifyByEmail;
	private boolean notifyByText;
	private boolean notifyOnSite;
	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}
	public void setNotifyByEmail(boolean notifyByEmail) {
		this.notifyByEmail = notifyByEmail;
	}
	public boolean isNotifyByText() {
		return notifyByText;
	}
	public void setNotifyByText(boolean notifyByText) {
		this.notifyByText = notifyByText;
	}
	public boolean isNotifyOnSite() {
		return notifyOnSite;
	}
	public void setNotifyOnSite(boolean notifyOnSite) {
		this.notifyOnSite = notifyOnSite;
	}
	
}
