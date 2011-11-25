package com.basinc.golfminus.view.club;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.faces.context.conversation.Begin;
import org.jboss.seam.faces.context.conversation.End;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.account.MembershipAccepted;
import com.basinc.golfminus.account.MembershipRejected;
import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.ClubRole;
import com.basinc.golfminus.domain.MembershipRequest;
import com.basinc.golfminus.domain.Role;
import com.basinc.golfminus.security.Identity;

@Transactional
@ConversationScoped
@Named
/**
 * Replicate Seam2 EntityHome concept where this would be a backing bean for a page that displays a Single Club
 * 
 * @author Scott
 *
 */
public class ClubHome implements Serializable  {
	private static final long serialVersionUID = -6349388717592316249L;

	private static Logger log = Logger.getLogger(ClubHome.class);

	@Inject
	EntityManager entityManager;

    @Inject Identity identity;
    

    @Inject
    @MembershipAccepted
    private Event<MembershipRequest> accecptedEventSrc;

    @Inject
    @MembershipRejected
    private Event<MembershipRequest> rejectedEventSrc;

    private Club clubSelection;
    
    @Begin(timeout=600000)
    public void selectClub(final Integer id) {
    	log.info("Selecting club ...."+id);
        // NOTE get a fresh reference that's managed by the extended persistence context
        clubSelection = entityManager.find(Club.class, id.intValue());
    }

	public Club getClubSelection() {
		return clubSelection;
	}

    public String acceptMembership(final Integer id) {
        MembershipRequest membershipRequest = entityManager.find(MembershipRequest.class, id.intValue());
        clubSelection.addMember(membershipRequest.getUser());
        clubSelection.removeMemberShipRequest(membershipRequest);
        ClubRole clubRole = entityManager.createNamedQuery("findClubRoleByNameAndClub",ClubRole.class).setParameter("roleName", Role.MEMBER_ROLE_NAME).setParameter("clubName", membershipRequest.getClub().getName()).getSingleResult();
        membershipRequest.getUser().addClubRole(clubRole);
        entityManager.remove(membershipRequest);
        entityManager.flush();
    	accecptedEventSrc.fire(membershipRequest);
    	return "";
    }

    public String rejectMembership(final Integer id) {
        MembershipRequest membershipRequest = entityManager.find(MembershipRequest.class, id.intValue());
        clubSelection.removeMemberShipRequest(membershipRequest);
        entityManager.remove(membershipRequest);
        entityManager.flush();
    	rejectedEventSrc.fire(membershipRequest);
    	return "";
    }

    @End
    public void save() {
    	log.info("Updating Club");
    	entityManager.persist(clubSelection);
        entityManager.flush();
    }
    
    @End
    public void cancel() {
    	clubSelection = null;
    	log.info("Cancel updates to Club");
    }

}
