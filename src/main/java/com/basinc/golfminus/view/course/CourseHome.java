package com.basinc.golfminus.view.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Address;
import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Facility;
import com.basinc.golfminus.domain.GeoLocation;
import com.basinc.golfminus.domain.TeeSet;
import com.basinc.golfminus.enums.TeeType;
import com.basinc.golfminus.security.Identity;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class CourseHome implements Serializable {
	private static final long serialVersionUID = -6377129797782701665L;

	private static Logger log = Logger.getLogger(CourseHome.class);

    @Inject Identity identity;
    
	@Inject
	EntityManager entityManager;

    private Course selection;
    
    private List<TeeSet> teeSets = new ArrayList<TeeSet>();
    
    @Begin(timeout=300000)
    public void selectCourse(final Integer id) {
        // NOTE get a fresh reference that's managed by the extended persistence context
    	selection = entityManager.find(Course.class, id.intValue());
    	populateTeeSets();
    }

    @Produces
    @ConversationScoped
    @Named("course")
	public Course getSelection() {
		return selection;
	}

    public void setSelection(Course course) {
    	selection = course;
    }
    
    @End
    public void save() {
    	log.info("Updating Course");
    	for (TeeSet teeSet : teeSets) {
    		if (teeSet.getCourseRating() > 0 && teeSet.getId() == 0) {
    			selection.addTeeSet(teeSet);
    		}
    	}
    	selection.getFacility().setName(selection.getName());
    	entityManager.persist(selection.getFacility());
    	entityManager.persist(selection);
    	entityManager.flush();
    }
    
    @End
    public void cancel() {
    	selection = null;
    	log.info("Cancel updates to Course");
    }

    @Begin
    public void newCourse() {
    	Course newCourse = new Course();
    	Facility facility = new Facility();
    	facility.addCourse(newCourse);
    	Address address = new Address();
    	address.setGeoLocation(new GeoLocation());
    	facility.setAddress(address);
    	for (TeeType teeType : TeeType.values()) {
    		teeSets.add(new TeeSet(teeType));
    	}
    	setSelection(newCourse);
    }

	public List<TeeSet> getTeeSets() {
		return teeSets;
	}

	public void setTeeSets(List<TeeSet> teeSets) {
		this.teeSets = teeSets;
	}

	private void populateTeeSets() {
    	for (TeeType teeType : TeeType.values()) {
    		TeeSet foundTeeSet = new TeeSet(teeType);
        	for (TeeSet teeSet : selection.getTeeSets()) {
        		if (teeSet.getTeeType().equals(teeType)) {
        			foundTeeSet = teeSet;
        			break;
        		}
        	}
    		teeSets.add(foundTeeSet);
    	}
	}
	
}
