package com.basinc.golfminus.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.faces.conversion.Converter;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.TeeSet;

@RequestScoped
@FacesConverter("teesetConverter")
public class TeesetConverter extends Converter<TeeSet> {
	private static Logger log = Logger.getLogger(TeesetConverter.class);
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public TeeSet toObject(UIComponent comp, String value) {
		if (value != null) {
			try {
				Integer intValue = new Integer(value);
				log.trace("Looking up TeeSet "+intValue);
				TeeSet teeset = em.find(TeeSet.class, intValue);
				log.info("Found TeeSet "+teeset.getName());
				return teeset;
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	@Override
	public String toString(UIComponent comp, TeeSet value) {
		if (value != null) {
			log.debug("Returning TeeSet "+value.getId());
			return value.getId()+"";
		}
		return null;
	}


}
