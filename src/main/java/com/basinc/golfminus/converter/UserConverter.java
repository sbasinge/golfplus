package com.basinc.golfminus.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.faces.conversion.Converter;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.User;

@RequestScoped
@FacesConverter("userConverter")
public class UserConverter extends Converter<User> {
	private static Logger log = Logger.getLogger(UserConverter.class);
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public User toObject(UIComponent comp, String value) {
		if (value != null) {
			try {
				log.trace("Looking up User "+value);
				User user = em.find(User.class, value);
				if (user != null)
					log.info("Found User "+user.getName());
				return user;
			} catch (NumberFormatException e) {}

		}
		return null;
	}

	@Override
	public String toString(UIComponent comp, User value) {
		if (value != null) {
			log.debug("Returning User "+value.getUsername());
			return value.getUsername()+"";
		}
		return null;
	}



}
