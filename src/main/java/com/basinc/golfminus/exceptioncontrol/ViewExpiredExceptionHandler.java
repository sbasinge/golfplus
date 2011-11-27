package com.basinc.golfminus.exceptioncontrol;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.i18n.DefaultBundleKey;

/**
 * handler those exceptions generated by view expired management
 *
 * @author Scott
 */

@HandlesExceptions
public class ViewExpiredExceptionHandler {
    @Inject private FacesContext facesContext;
    @Inject private Messages messages;


    /**
     * Handles the exception thrown at the end of a conversation redirecting
     * the flow to a pretty page instead of printing a stacktrace on the screen.
     *
     * @param event
     * @param log
     */
    public void viewExpiredExceptionHandler(@Handles CaughtException<ViewExpiredException> event, Logger log) {
        log.info("View Expired: " + event.getException().getMessage());
        try {
            messages.info(new DefaultBundleKey("view_expired")).defaults("The requested page has expired and cannot be restored.  Please start again.");
            facesContext.getExternalContext().redirect("view_expired");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}