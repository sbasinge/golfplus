package com.basinc.golfminus.bootstrap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.jboss.logging.Logger;

class MyExtension implements Extension {
	Logger log = Logger.getLogger(getClass());

	   void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
	      log.warn("beginning the scanning process");
	   }

	   <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {
		   log.warn("scanning type: " + pat.getAnnotatedType().getJavaClass().getName());
	   } 

	   void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
		   log.warn("finished the scanning process");
	   }

	}