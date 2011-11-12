package com.basinc.golfminus.view.teeset;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.basinc.golfminus.domain.TeeSet;

@Stateful
@ConversationScoped
@Named
public class TeesetList {
    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager em;

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
        List<TeeSet> results = em.createQuery(sql).getResultList();
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
