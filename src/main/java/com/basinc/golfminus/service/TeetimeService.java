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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

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
    public ResultWrapper find(@QueryParam(value="limit") int limit, @QueryParam(value="skip") int skip) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
        
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(TeeTime.class)));
        
        List<TeeTime> results = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(limit).getResultList();
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        return new ResultWrapper(count, results);
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
	
	@DELETE
	public void deleteTeeTime(int id) {
		log.warn("Attempting to delete teetime: "+id);
		TeeTime teeTime = entityManager.find(TeeTime.class, id);
		entityManager.joinTransaction();
		entityManager.remove(teeTime);
		entityManager.flush();
	}

	class ResultWrapper {
		Long count;
		List<TeeTime> teetimes;
		
		public ResultWrapper(Long count, List<TeeTime> teetimes) {
			this.count = count;
			this.teetimes = teetimes;
		}

		public Long getCount() {
			return count;
		}

		public void setCount(Long count) {
			this.count = count;
		}

		public List<TeeTime> getTeetimes() {
			return teetimes;
		}

		public void setTeetimes(List<TeeTime> teetimes) {
			this.teetimes = teetimes;
		}
		
		
	}

}

