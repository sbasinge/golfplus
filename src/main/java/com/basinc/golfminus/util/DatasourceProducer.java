package com.basinc.golfminus.util;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.solder.core.ExtensionManaged;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 * @ExtensionManaged
 * @Produces
 * @PersistenceUnit
 * @ConversationScoped EntityManagerFactory producerField;
 */
public class DatasourceProducer implements Serializable {
	private static final long serialVersionUID = -5267593171036179836L;
	
	@ExtensionManaged
	@Produces
	@PersistenceUnit
	@ConversationScoped
	private EntityManagerFactory emf;

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

}