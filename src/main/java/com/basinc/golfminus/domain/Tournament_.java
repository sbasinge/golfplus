package com.basinc.golfminus.domain;

import com.basinc.golfminus.enums.TournamentType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-30T08:08:02.169-0400")
@StaticMetamodel(Tournament.class)
public class Tournament_ extends BaseEntity_ {
	public static volatile SingularAttribute<Tournament, String> name;
	public static volatile SingularAttribute<Tournament, TournamentType> type;
	public static volatile ListAttribute<Tournament, TeeTime> teeTimes;
}
