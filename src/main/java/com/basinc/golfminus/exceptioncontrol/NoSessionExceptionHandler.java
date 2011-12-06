package com.basinc.golfminus.exceptioncontrol;

import java.io.IOException;

import javax.enterprise.inject.IllegalProductException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.i18n.DefaultBundleKey;
import com.basinc.golfminus.util.ViewUtil;
 
@HandlesExceptions
public class NoSessionExceptionHandler {
    @Inject private FacesContext facesContext;
    @Inject private Messages messages;

    /**
     * Handles IllegalProducer exception which is first thing fired when the session has timed out.
     * 
     * @param event
     * @param log
     */
    public void illegalProducerExceptionHandler(@Handles CaughtException<IllegalProductException> event, Logger log) {
    	String requestUrl = ViewUtil.getRequestURL();
        log.info("Session ended: " + requestUrl);
        try {
            messages.error(new DefaultBundleKey("session_ended")).defaults("Your session has timed out.  Please login again.");
            facesContext.getExternalContext().redirect("/home.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
