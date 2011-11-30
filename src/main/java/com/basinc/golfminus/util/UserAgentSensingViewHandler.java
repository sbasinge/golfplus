package com.basinc.golfminus.util;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAgentSensingViewHandler extends ViewHandlerWrapper {
	private static Logger log = LoggerFactory.getLogger(UserAgentSensingViewHandler.class);
	
	private ViewHandler delegate;
	
	public UserAgentSensingViewHandler(ViewHandler delegate) {
		this.delegate = delegate;
	}
	
	public ViewHandler getWrapped() {
		return delegate;
	}

    public String calculateRenderKitId(FacesContext context) {
    	if(ViewUtil.isMobile()) {
    		return "PRIMEFACES_MOBILE";
    	} else {
    		return getWrapped().calculateRenderKitId(context);
    	}
    }
    
}
