package com.basinc.golfminus.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.faces.conversion.Converter;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.TeeTime;

@RequestScoped
@FacesConverter("teetimeConverter")
public class TeetimeConverter extends Converter<TeeTime> {
	private static Logger log = Logger.getLogger(TeetimeConverter.class);
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public TeeTime toObject(UIComponent comp, String value) {
		if (value != null) {
			try {
				Integer intValue = new Integer(value);
				log.trace("Looking up TeeTime "+intValue);
				TeeTime teetime = em.find(TeeTime.class, intValue);
				log.info("Found TeeTime "+teetime.getCourse().getName());
				return teetime;
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	@Override
	public String toString(UIComponent comp, TeeTime value) {
		if (value != null) {
			log.debug("Returning TeeTime "+value.getId());
			return value.getId()+"";
		}
		return null;
	}


}
