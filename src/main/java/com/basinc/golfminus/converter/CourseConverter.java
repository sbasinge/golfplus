package com.basinc.golfminus.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.conversion.Converter;

import com.basinc.golfminus.domain.Course;

@RequestScoped
@FacesConverter("courseConverter")
public class CourseConverter extends Converter<Course> {
	private static Logger log = Logger.getLogger(CourseConverter.class);
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public Course toObject(UIComponent comp, String value) {
		if (value != null) {
			try {
				Integer intValue = new Integer(value);
				log.trace("Looking up Course "+intValue);
				Course course = em.find(Course.class, intValue);
				log.info("Found Course "+course.getName());
				course.getTeeSets().size(); //avoid lazy init errors?
				return course;
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	@Override
	public String toString(UIComponent comp, Course value) {
		if (value != null) {
			log.debug("Returning Course "+value.getId());
			return value.getId()+"";
		}
		return null;
	}


}
