package com.basinc.golfminus.notifier;

import javax.enterprise.event.Observes;
import javax.servlet.http.HttpSession;

import org.jboss.solder.servlet.event.Destroyed;
import org.jboss.solder.servlet.event.Initialized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionObserver {
	private static Logger log = LoggerFactory.getLogger(SessionObserver.class);
	
	public void observeSessionInitialized(@Observes @Initialized HttpSession session) {
		// Do something with the "session" object upon being initialized
		log.info("Session Initialized");
	}
	
	public void observeSessionDestroyed(@Observes @Destroyed HttpSession session) {
		// Do something with the "session" object upon being initialized
		log.info("Session Destroyed");
	}

}
