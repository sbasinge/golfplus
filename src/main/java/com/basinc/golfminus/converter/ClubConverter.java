package com.basinc.golfminus.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.jboss.seam.faces.conversion.Converter;

import com.basinc.golfminus.domain.Club;

@RequestScoped
@FacesConverter("clubConverter")
public class ClubConverter extends Converter<Club> {
	private static Logger log = Logger.getLogger(ClubConverter.class);
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public Club toObject(UIComponent comp, String value) {
		if (value != null) {
			try {
				Integer intValue = new Integer(value);

				log.trace("Looking up club "+intValue);
				Club club = em.find(Club.class, intValue);
				log.info("Found club "+club.getName());
				return club;
			} catch (NumberFormatException e) {}
		}
		return null;
	}

	@Override
	public String toString(UIComponent comp, Club value) {
		if (value != null) {
			log.debug("Returning club "+value.getId());
			return value.getId()+"";
		}
		return null;
	}


}
