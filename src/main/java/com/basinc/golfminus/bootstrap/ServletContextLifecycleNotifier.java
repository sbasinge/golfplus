package com.basinc.golfminus.bootstrap;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextLifecycleNotifier implements ServletContextListener
{
   @Inject @Any
   private Event<ServletContext> servletContextEvent;
   
//   @Override
   public void contextInitialized(ServletContextEvent sce)
   {
      servletContextEvent.select(new AnnotationLiteral<Initialized>() {}).fire(sce.getServletContext());
   }

//   @Override
   public void contextDestroyed(ServletContextEvent sce)
   {
      servletContextEvent.select(new AnnotationLiteral<Destroyed>() {}).fire(sce.getServletContext());
   }
}
