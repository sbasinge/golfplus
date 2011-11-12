package com.basinc.golfminus.notifier;

import java.text.SimpleDateFormat;

import javax.enterprise.event.Observes;

import com.basinc.golfminus.account.MembershipAccepted;
import com.basinc.golfminus.account.MembershipRejected;
import com.basinc.golfminus.domain.MembershipRequest;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.security.Registered;
import com.basinc.golfminus.view.club.HandicapCalculated;
import com.basinc.golfminus.view.scores.ScoreUpdated;
import com.basinc.golfminus.view.teetime.TeeTimeAdded;
import com.basinc.golfminus.view.teetime.TeeTimeDeleted;
import com.basinc.golfminus.view.teetime.TeeTimeUpdated;

public interface MessageNotifier {
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	public abstract void registrationCompleted(@Observes @Registered MembershipRequest membershipRequest);

	public abstract void membershipRejected(@Observes @MembershipRejected MembershipRequest membershipRequest);

	public abstract void membershipAccepted(@Observes @MembershipAccepted MembershipRequest membershipRequest);

	public abstract void handicapCalculated(@Observes @HandicapCalculated User user);
	
	public abstract void scoreUpdated(@Observes @ScoreUpdated Score score);

	public abstract void teetimeAdded(@Observes @TeeTimeAdded TeeTime teetime);

	public abstract void teetimeUpdated(@Observes @TeeTimeUpdated TeeTimeParticipant participant);

	public abstract void teetimeDeleted(@Observes @TeeTimeDeleted TeeTime teetime);

}