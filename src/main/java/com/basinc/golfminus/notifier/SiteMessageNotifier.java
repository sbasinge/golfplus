package com.basinc.golfminus.notifier;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.basinc.golfminus.account.MembershipAccepted;
import com.basinc.golfminus.account.MembershipRejected;
import com.basinc.golfminus.domain.MembershipRequest;
import com.basinc.golfminus.domain.Role;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.SiteMessage;
import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.security.Identity;
import com.basinc.golfminus.security.Registered;
import com.basinc.golfminus.util.PersistenceUtil;
import com.basinc.golfminus.view.club.HandicapCalculated;
import com.basinc.golfminus.view.scores.ScoreUpdated;
import com.basinc.golfminus.view.teetime.TeeTimeAdded;
import com.basinc.golfminus.view.teetime.TeeTimeDeleted;
import com.basinc.golfminus.view.teetime.TeeTimeUpdated;

public class SiteMessageNotifier extends PersistenceUtil implements MessageNotifier {
    @Inject Identity identity;
    
	public void registrationCompleted(@Observes @Registered MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.getUser(),"Registration has been completed.");
		entityManager.persist(message);
		for (User user : membershipRequest.getClub().locateMembersWithRole(Role.CLUBADMIN_ROLE_NAME)) {
			SiteMessage adminMessage = new SiteMessage(user,membershipRequest.getUser().getName()+" has registered to join your club.  Please accept/reject their membership using the Members page.");
			entityManager.persist(adminMessage);
		}
	}

	public void membershipRejected(@Observes @MembershipRejected MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.getUser(),"Membership to club "+membershipRequest.getClub().getName()+" has been rejected.");
		entityManager.persist(message);

	}

	public void membershipAccepted(@Observes @MembershipAccepted MembershipRequest membershipRequest) {
		SiteMessage message = new SiteMessage(membershipRequest.getUser(),"Membership to club "+membershipRequest.getClub().getName()+" has been accepted.");
		entityManager.persist(message);
	}

	public void handicapCalculated(@Observes @HandicapCalculated User user) {
		if (user.getHandicapCalculateNotificationType().isNotifyOnSite()) {
			SiteMessage message = new SiteMessage(user,"Handicap recalculated to "+user.getHandicap());
			entityManager.persist(message);
		}
	}

	public void scoreUpdated(@Observes @ScoreUpdated Score score) {
		SiteMessage message = new SiteMessage(score.getUser(),"A new score has been added for "+score.getTeeSet().getCourse().getName()+" on "+ sdf.format(score.getDate()));
		entityManager.persist(message);
		
	}

	public void teetimeAdded(@Observes @TeeTimeAdded TeeTime teetime) {
		if (teetime.isNotificationOn()) {
			for (User user : identity.getSelectedClub().getMembers()) {
				if (user.getNewTeeTimeNotificationType().isNotifyOnSite()) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been added at "+teetime.getCourse().getName()+" on "+sdf.format(teetime.getDate()));
					entityManager.persist(message);
				}
			}
		}
	}

	public void teetimeUpdated(@Observes @TeeTimeUpdated TeeTimeParticipant participant) {
		if (participant.getTeetime().isNotificationOn()) {
			for (User user : identity.getSelectedClub().getMembers()) {
				if (!user.getUsername().equals(participant.getUser().getUsername()) && user.getTeeTimeFullNotificationType().isNotifyOnSite()) {
					SiteMessage message = new SiteMessage(user,participant.getUser().getName()+" has reserved a spot on the Tee Time at "+participant.getTeetime().getCourse().getName()+" on "+sdf.format(participant.getTeetime().getDate())+". There are now "+participant.getTeetime().getNumOpenSpots()+" spots left.");
					entityManager.persist(message);
				}
			}
		}
	}

	public void teetimeDeleted(@Observes @TeeTimeDeleted TeeTime teetime) {
		if (teetime.isNotificationOn()) {
			for (User user : identity.getSelectedClub().getMembers()) {
				if (user.getTeeTimeFullNotificationType().isNotifyOnSite()) {
					SiteMessage message = new SiteMessage(user,"Tee Time has been deleted for "+teetime.getCourse().getName()+" on "+sdf.format(teetime.getDate()));
					entityManager.persist(message);
				}
			}
		}
	}

}
