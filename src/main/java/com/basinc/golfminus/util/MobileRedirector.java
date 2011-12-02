package com.basinc.golfminus.util;

import javax.inject.Inject;
import javax.inject.Named;

import com.basinc.golfminus.security.Identity;

@Named
public class MobileRedirector {
	@Inject Identity identity;
	
	public void redirectToMobile() {
		if (ViewUtil.isMobile()) {
			redirectToMobile(true);
		}
	}
	
	public void redirectToMobile(boolean force) {
		if (force)
			ViewUtil.redirect("mobile/home.jsf");
	}
	
	public void redirectToLanding() {
		if (identity.isLoggedIn()) {
			ViewUtil.redirect("main.jsf");
		}
	}

}
