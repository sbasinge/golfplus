package com.basinc.golfminus.util;

import javax.inject.Named;

@Named
public class MobileRedirector {
	public void redirectToMobile() {
		if (ViewUtil.isMobile()) {
			redirectToMobile(true);
		}
	}
	
	public void redirectToMobile(boolean force) {
		if (force)
			ViewUtil.redirect("mobile/home.jsf");
	}

}
