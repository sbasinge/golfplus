package com.basinc.golfminus.view.course;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Course_;
import com.basinc.golfminus.domain.Facility;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class CourseList implements Serializable {
	
	private static final long serialVersionUID = -844626457473901819L;
	private static Logger log = Logger.getLogger(CourseList.class);
	
	@Inject
	EntityManager entityManager;

    private List<Course> courses;
    
    @Begin
    public void find() {
        queryCourses();
    }

    private void queryCourses() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        query.select(root);
        query.orderBy(builder.asc(root.get(Course_.name)));
        List<Course> results = entityManager.createQuery(query).getResultList();
        setCourses(results);
	}

    @End
    public void addCourse() {
    	log.info("Add new  Course");
    }

//    @End
	public void deleteCourse(int id) {
		log.warn("Attempting to delete Course: "+id);
		Course course = entityManager.find(Course.class, id);
	    Facility facility = course.getFacility();
	    if (facility != null) {
	    	facility.removeCourse(course);
	    	if (facility.getCourses().size()==0) {
	    		entityManager.remove(facility);
	    	}
	    }
	    entityManager.remove(course);
		entityManager.flush();
		find();
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

}
