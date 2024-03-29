package com.basinc.golfminus.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.util.JSONPObject;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Course_;
import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTime_;

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
    public JSONPObject find(@QueryParam(value="callback") @DefaultValue(value="callback") String callback, @QueryParam(value="limit") int limit, @QueryParam(value="skip") int skip, @QueryParam(value="orderBy") String orderBy, @QueryParam(value="orderAsc") boolean orderAsc) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTime> query = builder.createQuery(TeeTime.class);
        Root<TeeTime> teeTime = query.from(TeeTime.class);
        Join<TeeTime, Course> course = teeTime.join(TeeTime_.course);
        if (orderBy != null) {
        	if ("id".equalsIgnoreCase(orderBy) && orderAsc)
        		query.orderBy(builder.asc(teeTime.get(TeeTime_.id)));
        	else if ("id".equalsIgnoreCase(orderBy) && !orderAsc)
        		query.orderBy(builder.desc(teeTime.get(TeeTime_.id)));
        	else if ("date".equalsIgnoreCase(orderBy) && orderAsc)
        		query.orderBy(builder.asc(teeTime.get(TeeTime_.date)));
        	else if ("date".equalsIgnoreCase(orderBy) && !orderAsc)
        		query.orderBy(builder.desc(teeTime.get(TeeTime_.date)));
        	else if ("numPlayers".equalsIgnoreCase(orderBy) && orderAsc)
        		query.orderBy(builder.asc(teeTime.get(TeeTime_.numPlayers)));
        	else if ("numPlayers".equalsIgnoreCase(orderBy) && !orderAsc)
        		query.orderBy(builder.desc(teeTime.get(TeeTime_.numPlayers)));
        	else if ("course.name".equalsIgnoreCase(orderBy) && orderAsc)
        		query.orderBy(builder.asc(course.get(Course_.name)));
        	else if ("course.name".equalsIgnoreCase(orderBy) && !orderAsc)
        		query.orderBy(builder.desc(course.get(Course_.name)));
        }
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        countQuery.select(builder.count(countQuery.from(TeeTime.class)));
        
        List<TeeTime> results = entityManager.createQuery(query).setFirstResult(skip).setMaxResults(limit).getResultList();
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        JSONPObject po = new JSONPObject(callback, new ResultWrapper(count, results));
        return po;
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/{id}")	
	public JSONPObject get(@QueryParam(value="callback") @DefaultValue(value="callback") String callback, @PathParam("id") int id) {
		TeeTime result = entityManager.find(TeeTime.class, id);
        return new JSONPObject(callback, result);
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/{id}")	
	public void deleteTeeTime(@PathParam("id") int id) {
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

