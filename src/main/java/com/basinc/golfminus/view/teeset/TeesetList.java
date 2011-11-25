package com.basinc.golfminus.view.teeset;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.basinc.golfminus.domain.TeeSet;

@ConversationScoped
@Named
public class TeesetList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4247834040686462965L;

	@Inject
	EntityManager entityManager;

    private List<TeeSet> tees;
    
    public void find() {
        queryTeesets();
    }

    private void queryTeesets() {
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery<TeeSet> query = builder.createQuery(TeeSet.class);
//        Root<TeeSet> root = query.from(TeeSet.class);
//        Fetch<TeeSet, Course> course = root.fetch(TeeSet_.course);
//        query.select(root);
//        List<TeeSet> results = em.createQuery(query).getResultList();
        
        String sql = "select t from TeeSet t join fetch t.course c order by c.name";
        List<TeeSet> results = entityManager.createQuery(sql).getResultList();
        setTees(results);
	}

	@Produces
    @Named(value="teesets")
	public List<TeeSet> getTees() {
		return tees;
	}

	public void setTees(List<TeeSet> tees) {
		this.tees = tees;
	}

}
