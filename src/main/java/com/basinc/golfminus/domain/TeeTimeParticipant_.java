package com.basinc.golfminus.domain;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-27T12:13:38.201-0400")
@StaticMetamodel(TeeTimeParticipant.class)
public class TeeTimeParticipant_ extends BaseEntity_ {
	public static volatile SingularAttribute<TeeTimeParticipant, TeeTime> teetime;
	public static volatile SingularAttribute<TeeTimeParticipant, User> user;
	public static volatile SingularAttribute<TeeTimeParticipant, Score> score;
	public static volatile SingularAttribute<TeeTimeParticipant, BigDecimal> courseIndex;
	public static volatile SingularAttribute<TeeTimeParticipant, BigDecimal> bestBallIndex;
}
