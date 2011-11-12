package com.basinc.golfminus.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;
import org.jboss.seam.transaction.Transactional;

import com.basinc.golfminus.domain.TeeTime;

@Transactional
@Stateless
@Path(value = "/teetimes")
/**
 * Replicate the Seam2 concept where this would be a backing bean for a page that displays a List of Clubs.
 * 
 * @author Scott
 *
 */
public class TeetimeService {
	
	private Logger log = Logger.getLogger(getClass());

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
    EntityManager entityManager; 

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/list")	
    public List<TeeTime> find() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
        
        List<TeeTime> results = entityManager.createQuery(query).getResultList();
        return results;
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/strings")	
	public List<String> findStrings() {
		List<String> strings = new ArrayList<String>();
		strings.add("Hello");
		strings.add("World");
		return strings;
	}
	
	public void deleteTeeTime(int id) {
		log.warn("Attempting to delete teetime: "+id);
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
		entityManager.joinTransaction();
		entityManager.remove(teeTime);
		entityManager.flush();
	}

}
