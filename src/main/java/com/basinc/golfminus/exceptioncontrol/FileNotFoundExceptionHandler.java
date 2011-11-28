package com.basinc.golfminus.exceptioncontrol;

import java.io.FileNotFoundException;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

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
public class FileNotFoundExceptionHandler {
    @Inject private FacesContext facesContext;
    @Inject private Messages messages;

	@Inject
	private UserTransaction utx;

    /**
     * Handles the exception thrown at the end of a conversation redirecting
     * the flow to a pretty page instead of printing a stacktrace on the screen.
     *
     * @param event
     * @param log
     */
    public void fileNotFoundExceptionHandler(@Handles CaughtException<FileNotFoundException> event, Logger log) {
        log.info("Requested page does not exist: " + event.getException().getMessage());
        messages.info(new DefaultBundleKey("page_not_found")).defaults("The requested page does not exist.  Please start again.");
        event.markHandled();
    }
}