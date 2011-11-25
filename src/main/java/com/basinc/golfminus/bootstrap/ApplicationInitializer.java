package com.basinc.golfminus.bootstrap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;

import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.domain.Address;
import com.basinc.golfminus.domain.Club;
import com.basinc.golfminus.domain.ClubRole;
import com.basinc.golfminus.domain.Course;
import com.basinc.golfminus.domain.Facility;
import com.basinc.golfminus.domain.Role;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.SiteMessage;
import com.basinc.golfminus.domain.TeeSet;
import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.Tournament;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.enums.State;
import com.basinc.golfminus.enums.TeeType;
import com.basinc.golfminus.enums.TournamentType;

//@ApplicationScoped
public class ApplicationInitializer {
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private UserTransaction utx;

	// @Inject
	private Logger log = Logger.getLogger(getClass());

	private final List<ClubRole> clubRoles = new ArrayList<ClubRole>();
	private final List<Club> clubs = new ArrayList<Club>();
	private final List<Facility> facilities = new ArrayList<Facility>();

	private Facility champions;

	private Facility turnberry;

	private Facility pinehills;

	private Facility splitrock;

	private Facility westchester;

	private Facility glendornoch;

	private Facility prestwick;

	private Facility tidewater;

	private Facility granddunes;

	private Facility glenross;

	private Facility blacklick;

	private Facility ral;

	private Facility pheonix;

	private Facility mentel;

	private Facility spBay;

	private Facility foxFire;

	private Facility linksAtGroveport;

	private Facility upperLansdowne;

	private Facility thornApple;

	private Facility windyKnoll;

	private Facility indianSprings;

	private Facility golfClubOfDublin;

	private ClubRole memberCMGC;

	private ClubRole teeTimeCMGC;

	private ClubRole clubAdminCMGC;

	private ClubRole adminCMGC;

	private Facility arcadian;

	private Facility pineLakes;

	private Facility safari;

	private Facility nationalRoad;

	private Facility treeLinks;

	private Facility eastGolfClub;

	private Facility reidParkNorth;

	private Facility hickoryHills;

	private Facility unnamed;

	private Facility cookscreek;

	private Facility woodhaven;

	private Facility gladespings;

	private Facility stonehaven;

	private Facility chillicc;

	private Facility patriotspoint;

	private Facility thelakes;

	public ApplicationInitializer() throws ParseException {
		// populate role, user, club and member seed data
		Club cmgc = new Club("CMGC");
		cmgc.setWebsiteUrl("http://columbusmetrogolfclub.blogspot.com/");
		cmgc.setRssFeedUrl("http://columbusmetrogolfclub.blogspot.com/feeds/posts/default");
		Role adminRole = new Role("Admin");
		Role clubAdminRole = new Role("ClubAdmin");
		Role memberRole = new Role("Member");
		Role teeTimeRole = new Role("TeeTimeChair");
		adminCMGC = new ClubRole(cmgc, adminRole);
		clubAdminCMGC = new ClubRole(cmgc, clubAdminRole);
		memberCMGC = new ClubRole(cmgc, memberRole);
		teeTimeCMGC = new ClubRole(cmgc, teeTimeRole);
		

		clubRoles.addAll(Arrays.asList(adminCMGC, clubAdminCMGC, memberCMGC, teeTimeCMGC));
		clubs.addAll(Arrays.asList(cmgc));

		Course playersClub = new Course("Players Club", new TeeSet(TeeType.WHITE, 72.2, 129));
		playersClub.addTeeSet(new TeeSet(TeeType.BLUE, 74.2, 132));
		foxFire = new Facility("FoxFire", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), playersClub);
		spBay = new Facility("Sandpiper Bay", new Address("123 main", "City", State.SC, "Zip", 39.464, -82.545), new Course(
				"Sandpiper Bay", new TeeSet(TeeType.WHITE, 69.5, 128)));
		mentel = new Facility("Mentel Memorial", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Mentel Memorial", new TeeSet(TeeType.WHITE, 70.6, 118)));
		pheonix = new Facility("The Pheonix", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"The Pheonix", new TeeSet(TeeType.WHITE, 68.8, 116)));
		ral = new Facility("Royal American", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Royal American", new TeeSet(TeeType.WHITE, 70.4, 122)));
		blacklick = new Facility("Blacklick", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Blacklick", new TeeSet(TeeType.WHITE, 69.6, 119)));
		glenross = new Facility("Glenross", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Glenross", new TeeSet(TeeType.WHITE, 68.4, 116)));
		granddunes = new Facility("Grande Dunes", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Grande Dunes", new TeeSet(TeeType.WHITE, 70.8, 128)));
		tidewater = new Facility("Tidewater", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Tidewater", new TeeSet(TeeType.WHITE, 70.4, 130)));
		prestwick = new Facility("Prestwick C.C.", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Prestwick C.C.", new TeeSet(TeeType.WHITE, 71.1, 131)));
		glendornoch = new Facility("Glen Dornoch", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Glen Dornoch", new TeeSet(TeeType.WHITE, 70.2, 127)));
		westchester = new Facility("Westchester", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Westchester", new TeeSet(TeeType.WHITE, 69.8, 122)));
		splitrock = new Facility("Split Rock", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Split Rock", new TeeSet(TeeType.WHITE, 69.5, 120)));
		pinehills = new Facility("Pine Hills", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Pine Hills", new TeeSet(TeeType.WHITE, 68.4, 119)));
		turnberry = new Facility("Turnberry", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Turnberry", new TeeSet(TeeType.WHITE, 69.3, 120)));
		champions = new Facility("Champions", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Champions", new TeeSet(TeeType.WHITE, 69.5, 129)));
		linksAtGroveport = new Facility("Links at Groveport", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Links at Groveport", new TeeSet(TeeType.WHITE, 70.1, 121)));
		upperLansdowne = new Facility("Upper Lansdowne", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Upper Lansdowne", new TeeSet(TeeType.WHITE, 68.3, 109)));
		thornApple = new Facility("Thorn Apple", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Thorn Apple", new TeeSet(TeeType.WHITE, 71.1, 114)));
		windyKnoll = new Facility("Windy Knoll", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Windy Knoll", new TeeSet(TeeType.WHITE, 69.2, 125)));
		indianSprings = new Facility("Indian Springs", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Indian Springs", new TeeSet(TeeType.WHITE, 72.0, 135)));
		golfClubOfDublin = new Facility("Golf Club of Dublin", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Golf Club of Dublin", new TeeSet(TeeType.WHITE, 69.3, 122)));
		arcadian = new Facility("Arcadian Shores", new Address("123 main", "City", State.SC, "Zip", 39.464, -82.545), new Course(
				"Arcadian Shores", new TeeSet(TeeType.WHITE, 69.2, 126)));
		pineLakes = new Facility("Pine Lakes", new Address("123 main", "City", State.SC, "Zip", 39.464, -82.545), new Course(
				"Pine Lakes", new TeeSet(TeeType.WHITE, 70.5, 128)));
		safari = new Facility("Safari", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Safari", new TeeSet(TeeType.WHITE, 70.0, 114)));
		nationalRoad = new Facility("National Road", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"National Road", new TeeSet(TeeType.WHITE, 71.6, 132)));
		treeLinks = new Facility("Tree Links", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Tree Links", new TeeSet(TeeType.WHITE, 70.8, 125)));
		eastGolfClub = new Facility("East Golf Club", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"East Golf Club", new TeeSet(TeeType.WHITE, 71.1, 132)));
		reidParkNorth = new Facility("Reid Park", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Reid Park North", new TeeSet(TeeType.WHITE, 71.3, 126)));
		hickoryHills = new Facility("Hickory Hills", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Hickory Hills", new TeeSet(TeeType.WHITE, 72.7, 133)));
		unnamed = new Facility("???", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"???", new TeeSet(TeeType.WHITE, 71.5, 134)));
		cookscreek = new Facility("Cooks Creek", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Cooks Creek", new TeeSet(TeeType.WHITE, 68.5, 122)));
		woodhaven = new Facility("Wood Haven", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Wood Haven", new TeeSet(TeeType.WHITE, 70.1, 133)));
		gladespings = new Facility("Glade Springs", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Glade Springs, Cobb", new TeeSet(TeeType.WHITE, 70.6, 133)));
		stonehaven = new Facility("Stone Haven", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Stone Haven", new TeeSet(TeeType.WHITE, 70.6, 133)));
		chillicc = new Facility("Chillicothe CC", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Chillicothe CC", new TeeSet(TeeType.WHITE, 69.1, 117)));
		patriotspoint = new Facility("Patriots Point", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"Patriots Point", new TeeSet(TeeType.WHITE, 70.4, 121)));
		thelakes = new Facility("The Lakes", new Address("123 main", "City", State.OH, "Zip", 39.464, -82.545), new Course(
				"The Lakes", new TeeSet(TeeType.WHITE, 70.7, 124)));
		
		facilities.addAll(Arrays.asList(foxFire, spBay, mentel, pheonix, ral, blacklick, glenross, granddunes, tidewater, prestwick, glendornoch,
				westchester, splitrock, pinehills, turnberry, champions, linksAtGroveport, upperLansdowne, thornApple, windyKnoll, indianSprings,
				golfClubOfDublin, arcadian, pineLakes, safari, nationalRoad, treeLinks, eastGolfClub, reidParkNorth, hickoryHills,
				unnamed, cookscreek, woodhaven, gladespings, stonehaven, chillicc, patriotspoint, thelakes));

	}

//	public void onStartup(@Observes @Initialized ServletContext ctx) {
	public void onStartup(ServletContext ctx) {
		log.info("Importing seed data for application " + ctx.getServletContextName());
		// use manual transaction control since this is a managed bean
		try {
			utx.begin();
			User scott = entityManager.find(User.class, "sbasinge");
			if (scott != null) {
				log.info("Seed data already exists, not loading.");
				utx.commit();
				return;
			}
			persist(facilities);
			persist(clubs);
			persist(clubRoles);
			
			
//			persist(teeTimes);
//


			Club cmgc = entityManager.find(Club.class, 1);

			scott = new User("Scott Basinger", "sbasinge", "sbasinge@gmail.com", "password");
			persist(scott);
			scott.addClubRole(adminCMGC);
			cmgc.addMember(scott);
			
			SiteMessage message = new SiteMessage(scott,"Welcome to the CMGC!");
			persist(message);

			scott.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "04/03/2010", 104, 104));
			scott.addScore(new Score(spBay.getCourses().get(0).getTeeSets().get(0), "02/22/2010", 90, 89));
			scott.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "07/10/2010", 93, 90));
			scott.addScore(new Score(pheonix.getCourses().get(0).getTeeSets().get(0), "07/25/2010", 91, 90));
			scott.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/28/2010", scott, 84, 84));
			scott.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/29/2010", scott, 94, 92));
			scott.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "10/09/2010", scott, 90, 88));
			scott.addScore(new Score(glenross.getCourses().get(0).getTeeSets().get(0), "10/16/2010", scott, 87, 87));
			scott.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", scott, 113, 100));
			scott.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", scott, 110, 108));
			scott.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", scott, 98, 95));
			scott.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", scott, 99, 98));
			scott.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", scott, 92, 92));
			scott.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/15/2011", scott, 84, 83));
			scott.addScore(new Score(splitrock.getCourses().get(0).getTeeSets().get(0), "06/18/2011", scott, 82, 82));
			scott.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", scott, 101, 96));
			scott.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", scott, 87, 86));
			scott.addScore(new Score(pinehills.getCourses().get(0).getTeeSets().get(0), "07/30/2011", scott, 84, 84));
			scott.addScore(new Score(turnberry.getCourses().get(0).getTeeSets().get(0), "08/14/2011", scott, 88, 86));
			scott.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "08/20/2011", scott, 98, 96));

			User jeff = new User("Jeff McCorkle", "jmccorkle", "someemail@gmail.com", "password");
			persist(jeff);
			jeff.addClubRole(clubAdminCMGC);
			cmgc.addMember(jeff);
			jeff.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", 100, 100));
			jeff.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", 95,95));
			jeff.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", 92,90));
			jeff.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", 90, 90));
			jeff.addScore(new Score(thornApple.getCourses().get(0).getTeeSets().get(0), "03/19/2011", jeff, 89, 89));
			jeff.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", jeff, 81, 81));
			jeff.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/15/2011", jeff, 93, 93));
			jeff.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "06/05/2011", jeff, 83, 83));
			jeff.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "06/12/2011", jeff, 87, 87));
			jeff.addScore(new Score(splitrock.getCourses().get(0).getTeeSets().get(0), "06/18/2011", jeff, 81, 81));
			jeff.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", jeff, 90, 90));
			jeff.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "07/10/2011", jeff, 84, 84));
			jeff.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", jeff, 90, 89));
			jeff.addScore(new Score(upperLansdowne.getCourses().get(0).getTeeSets().get(0), "07/24/2011", jeff, 85, 85));
			jeff.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "07/31/2011", jeff, 90, 89));
			jeff.addScore(new Score(turnberry.getCourses().get(0).getTeeSets().get(0), "08/14/2011", jeff, 84, 83));
			jeff.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "08/20/2011", jeff, 90, 90));
			jeff.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/04/2011", jeff, 79, 79));
			jeff.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/18/2011", jeff, 85, 85));
			jeff.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2011", jeff, 95, 94));
			
			User steve = new User("Steve Hawley", "shawley", "someemail3@gmail.com", "password");
			persist(steve);
			steve.addClubRole(teeTimeCMGC);
			cmgc.addMember(steve);
			steve.addScore(new Score(pheonix.getCourses().get(0).getTeeSets().get(0), "10/31/2010", 80, 80));
			steve.addScore(new Score(thornApple.getCourses().get(0).getTeeSets().get(0), "03/19/2011", 82, 82));
			steve.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "04/10/2011", 84, 83));
			steve.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", steve, 82, 82));
			steve.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "05/31/2011", 81, 80));
			steve.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "06/12/2011", steve, 82, 79));
			steve.addScore(new Score(splitrock.getCourses().get(0).getTeeSets().get(0), "06/18/2011", steve, 75,75));
			steve.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", steve, 86, 84));
			steve.addScore(new Score(windyKnoll.getCourses().get(0).getTeeSets().get(0), "07/04/2011", steve, 82, 81));
			steve.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "07/10/2011", steve, 76, 76));
			steve.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", steve, 75, 75));
			steve.addScore(new Score(upperLansdowne.getCourses().get(0).getTeeSets().get(0), "07/24/2011", steve, 82, 82));
			steve.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "08/07/2011", steve, 83, 83));
			steve.addScore(new Score(turnberry.getCourses().get(0).getTeeSets().get(0), "08/14/2011", steve, 80, 80));
			steve.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "08/20/2011", steve, 86, 86));
			steve.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/04/2011", steve, 79, 79));
			steve.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "09/05/2011", steve, 86, 85));
			steve.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/18/2011", steve, 80, 80));
			steve.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 86, 86));
			steve.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2011", steve, 94, 88));

			User skern = new User("Scott Kern", "skern", "skern@gmail.com", "password");
			persist(skern);
			skern.addClubRole(memberCMGC);
			cmgc.addMember(skern);
			skern.addScore(new Score(thornApple.getCourses().get(0).getTeeSets().get(0), "03/19/2011", 93, 93));
			skern.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "04/10/2011", 98, 97));
			skern.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", skern, 93, 92));
			skern.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/15/2011", skern, 96, 95));
			skern.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "06/05/2011", skern, 86, 86));
			skern.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "06/12/2011", skern, 87, 87));
			skern.addScore(new Score(splitrock.getCourses().get(0).getTeeSets().get(0), "06/18/2011", skern, 88, 88));
			skern.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", skern, 101, 96));
			skern.addScore(new Score(windyKnoll.getCourses().get(0).getTeeSets().get(0), "07/04/2011", skern, 94, 94));
			skern.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "07/10/2011", skern, 90, 89));
			skern.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", skern, 89, 88));
			skern.addScore(new Score(upperLansdowne.getCourses().get(0).getTeeSets().get(0), "07/24/2011", skern, 81, 81));
			skern.addScore(new Score(golfClubOfDublin.getCourses().get(0).getTeeSets().get(0), "07/29/2011", skern, 86, 84));
			skern.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "08/07/2011", skern, 82, 82));
			skern.addScore(new Score(turnberry.getCourses().get(0).getTeeSets().get(0), "08/14/2011", skern, 88, 87));
			skern.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "08/20/2011", skern, 92, 92));
			skern.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "09/05/2011", skern, 88, 88));
			skern.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/18/2011", skern, 100, 98));
			skern.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 98, 96));
			skern.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2011", skern, 103, 96));
			
			User vresor = new User("Vince Resor", "vresor", "someemail2@gmail.com", "password");
			persist(vresor);
			vresor.addClubRole(memberCMGC);
			cmgc.addMember(vresor);
			vresor.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "10/03/2009", 92, 91));
			vresor.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "10/10/2009", 98, 96));
			vresor.addScore(new Score(arcadian.getCourses().get(0).getTeeSets().get(0), "02/19/2010", vresor, 92, 92));
			vresor.addScore(new Score(pineLakes.getCourses().get(0).getTeeSets().get(0), "02/20/2010", vresor, 103, 96));
			vresor.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/21/2010", vresor, 90, 89));
			vresor.addScore(new Score(spBay.getCourses().get(0).getTeeSets().get(0), "02/22/2010", vresor, 96, 90));
			vresor.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "04/17/2010", vresor, 109, 107));
			vresor.addScore(new Score(safari.getCourses().get(0).getTeeSets().get(0), "05/15/2010", vresor, 93, 91));
			vresor.addScore(new Score(nationalRoad.getCourses().get(0).getTeeSets().get(0), "05/29/2010", vresor, 101, 97));
			vresor.addScore(new Score(treeLinks.getCourses().get(0).getTeeSets().get(0), "06/26/2010", vresor, 114, 105));
			vresor.addScore(new Score(pheonix.getCourses().get(0).getTeeSets().get(0), "07/25/2010", vresor, 108, 96));
			vresor.addScore(new Score(eastGolfClub.getCourses().get(0).getTeeSets().get(0), "08/26/2010", vresor, 101, 101));
			vresor.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/28/2010", vresor, 96, 94));
			vresor.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/29/2010", vresor, 99, 96));
			vresor.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", vresor, 113, 105));
			vresor.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", vresor, 115, 111));
			vresor.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", vresor, 108, 105));
			vresor.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", vresor, 106, 105));
			vresor.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", 94, 93));
			vresor.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", vresor, 111, 108));
			
			User jkempton = new User("John Kempton", "jkempton", "jkempton@gmail.com", "password");
			persist(jkempton);
			jkempton.addClubRole(memberCMGC);
			cmgc.addMember(jkempton);
			jkempton.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2010", 95, 94));
			jkempton.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "10/09/2010", jkempton, 93, 92));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "10/10/2010", jkempton, 97, 89));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "10/15/2010", jkempton, 88, 88));
			jkempton.addScore(new Score(glenross.getCourses().get(0).getTeeSets().get(0), "10/16/2010", jkempton, 97, 92));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "10/24/2010", jkempton, 97, 94));
			jkempton.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", jkempton, 91, 90));
			jkempton.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", jkempton, 89, 89));
			jkempton.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", jkempton, 96, 93));
			jkempton.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", jkempton, 87, 87));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "03/20/2011", jkempton, 91, 90));
			jkempton.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", jkempton, 83, 83));
			jkempton.addScore(new Score(upperLansdowne.getCourses().get(0).getTeeSets().get(0), "07/24/2011", jkempton, 91, 91));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "07/31/2011", jkempton, 90,89));
			jkempton.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "08/07/2011", jkempton, 90, 89));
			jkempton.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "08/19/2011", jkempton, 88, 87));
			jkempton.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/04/2011", jkempton, 89, 89));
			jkempton.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/18/2011", jkempton, 81, 81));
			jkempton.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 91, 90));
			jkempton.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2011", jkempton, 85, 82));
			
			
			User tkirk = new User("Tom Kirk", "tkirk", "tkirk@gmail.com", "password");
			persist(tkirk);
			tkirk.addClubRole(memberCMGC);
			cmgc.addMember(tkirk);
			tkirk.addScore(new Score(reidParkNorth.getCourses().get(0).getTeeSets().get(0), "09/29/2009", 94, 94));
			tkirk.addScore(new Score(arcadian.getCourses().get(0).getTeeSets().get(0), "02/19/2010", tkirk, 101, 101));
			tkirk.addScore(new Score(pineLakes.getCourses().get(0).getTeeSets().get(0), "02/20/2010", tkirk, 106, 101));
			tkirk.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/21/2010", tkirk, 104, 103));
			tkirk.addScore(new Score(spBay.getCourses().get(0).getTeeSets().get(0), "02/22/2010", tkirk, 108, 100));
			tkirk.addScore(new Score(safari.getCourses().get(0).getTeeSets().get(0), "05/15/2010", tkirk, 90, 90));
			tkirk.addScore(new Score(treeLinks.getCourses().get(0).getTeeSets().get(0), "06/26/2010", tkirk, 98, 97));
			tkirk.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/28/2010", tkirk, 97, 93));
			tkirk.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/29/2010", tkirk, 97, 97));
			tkirk.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", tkirk, 99, 98));
			tkirk.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", tkirk, 105, 103));
			tkirk.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", tkirk, 101, 99));
			tkirk.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", tkirk, 119, 114));
			tkirk.addScore(new Score(pinehills.getCourses().get(0).getTeeSets().get(0), "07/30/2011", tkirk, 90, 90));
			tkirk.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "07/31/2011", tkirk, 91, 90));
			tkirk.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "08/07/2011", tkirk, 88,86));
			tkirk.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "08/18/2011", 95, 94));
			tkirk.addScore(new Score(mentel.getCourses().get(0).getTeeSets().get(0), "09/04/2011", 86, 86));
			tkirk.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 91, 90));
			tkirk.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "10/02/2011", tkirk, 100, 99));

			User rjenks = new User("Richard Jenks", "rjenks", "rjenks@gmail.com", "password");
			persist(rjenks);
			rjenks.addClubRole(memberCMGC);
			cmgc.addMember(rjenks);
			rjenks.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "07/17/2010", 89, 89));
			rjenks.addScore(new Score(pheonix.getCourses().get(0).getTeeSets().get(0), "07/25/2010", 93, 89));
			rjenks.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "08/14/2010", 96, 94));
			rjenks.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/28/2010", 91, 90));
			rjenks.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/29/2010", 93, 91));
			rjenks.addScore(new Score(glenross.getCourses().get(0).getTeeSets().get(0), "10/16/2010", 97, 97));
			rjenks.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", 111, 110));
			rjenks.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", 95, 95));
			rjenks.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", 101, 101));
			rjenks.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", 103, 103));
			rjenks.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011", 81, 81));
			rjenks.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "05/31/2011", 100,99));
			rjenks.addScore(new Score(hickoryHills.getCourses().get(0).getTeeSets().get(0), "06/17/2011", 104,102));
			rjenks.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(1), "06/25/2011", 101,99));
			rjenks.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "07/08/2011", 93,90));
			rjenks.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", 91,91));
			rjenks.addScore(new Score(pinehills.getCourses().get(0).getTeeSets().get(0), "07/30/2011", 90,90));
			rjenks.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "08/18/2011", 95,94));
			rjenks.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "09/05/2011", 88, 88));
			rjenks.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 90,87));

			User ehawley = new User("Ed Hawley", "ehawley", "ehawley@gmail.com", "password");
			persist(ehawley);
			ehawley.addClubRole(memberCMGC);
			cmgc.addMember(ehawley);
			ehawley.addScore(new Score(reidParkNorth.getCourses().get(0).getTeeSets().get(0), "09/28/2009", 96, 93));
			ehawley.addScore(new Score(reidParkNorth.getCourses().get(0).getTeeSets().get(0), "09/29/2009", 105,105));
			ehawley.addScore(new Score(champions.getCourses().get(0).getTeeSets().get(0), "10/03/2009", 117,109));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "10/21/2009", 101,99));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "04/17/2010", 99,96));
			ehawley.addScore(new Score(safari.getCourses().get(0).getTeeSets().get(0), "04/22/2010", 117,114));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "05/15/2010", 95,95));
			ehawley.addScore(new Score(nationalRoad.getCourses().get(0).getTeeSets().get(0), "05/26/2010", 97,96));
			ehawley.addScore(new Score(treeLinks.getCourses().get(0).getTeeSets().get(0), "05/29/2010", 98,97));
			ehawley.addScore(new Score(treeLinks.getCourses().get(0).getTeeSets().get(0), "06/02/2010", 112,108));
			ehawley.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "06/26/2010", 105,104));
			ehawley.addScore(new Score(ral.getCourses().get(0).getTeeSets().get(0), "08/28/2010", 99,99));
			ehawley.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "08/29/2010",95,94));
			ehawley.addScore(new Score(westchester.getCourses().get(0).getTeeSets().get(0), "05/14/2011",93,93));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "05/15/2011", 90,90));
			ehawley.addScore(new Score(windyKnoll.getCourses().get(0).getTeeSets().get(0), "05/31/2011", 110,107));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "07/04/2011", 94,92));
			ehawley.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/08/2011", 97,97));
			ehawley.addScore(new Score(indianSprings.getCourses().get(0).getTeeSets().get(0), "07/16/2011", 101,99));
			ehawley.addScore(new Score(unnamed.getCourses().get(0).getTeeSets().get(0), "09/25/2011", 99,97));

			User jomccorkle = new User("John McCorkle", "jomccorkle", "jomccorkle@gmail.com", "password");
			persist(jomccorkle);
			jomccorkle.addClubRole(memberCMGC);
			cmgc.addMember(jomccorkle);
			jomccorkle.addScore(new Score(pineLakes.getCourses().get(0).getTeeSets().get(0), "02/20/2010", 117,114));
			jomccorkle.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/21/2010", 117,113));
			jomccorkle.addScore(new Score(spBay.getCourses().get(0).getTeeSets().get(0), "02/22/2010", 116,114));
			jomccorkle.addScore(new Score(cookscreek.getCourses().get(0).getTeeSets().get(0), "04/09/2010", 99,99));
			jomccorkle.addScore(new Score(woodhaven.getCourses().get(0).getTeeSets().get(0), "06/03/2010", 113,113));
			jomccorkle.addScore(new Score(gladespings.getCourses().get(0).getTeeSets().get(0), "06/04/2010", 120,115));
			jomccorkle.addScore(new Score(stonehaven.getCourses().get(0).getTeeSets().get(0), "06/05/2010", 112,109));
			jomccorkle.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "07/18/2010", 105,103));
			jomccorkle.addScore(new Score(pheonix.getCourses().get(0).getTeeSets().get(0), "07/25/2010", 95,95));
			jomccorkle.addScore(new Score(chillicc.getCourses().get(0).getTeeSets().get(0), "07/31/2010", 101, 101));
			jomccorkle.addScore(new Score(linksAtGroveport.getCourses().get(0).getTeeSets().get(0), "08/08/2010", 113,106));
			jomccorkle.addScore(new Score(patriotspoint.getCourses().get(0).getTeeSets().get(0), "08/16/2010", 106,106));
			jomccorkle.addScore(new Score(granddunes.getCourses().get(0).getTeeSets().get(0), "02/25/2011", 111, 108));
			jomccorkle.addScore(new Score(tidewater.getCourses().get(0).getTeeSets().get(0), "02/26/2011", 116,114));
			jomccorkle.addScore(new Score(prestwick.getCourses().get(0).getTeeSets().get(0), "02/27/2011", 121,121));
			jomccorkle.addScore(new Score(glendornoch.getCourses().get(0).getTeeSets().get(0), "02/28/2011", 115,107));
			jomccorkle.addScore(new Score(foxFire.getCourses().get(0).getTeeSets().get(0), "06/05/2011",107,107));
			jomccorkle.addScore(new Score(blacklick.getCourses().get(0).getTeeSets().get(0), "07/16/2011", 108,105));
			jomccorkle.addScore(new Score(thelakes.getCourses().get(0).getTeeSets().get(0), "09/29/2011", 102,102));

			jeff.calculateHandicap();
			steve.calculateHandicap();
			scott.calculateHandicap();
			skern.calculateHandicap();
			vresor.calculateHandicap();
			jkempton.calculateHandicap();
			tkirk.calculateHandicap();
			rjenks.calculateHandicap();
			ehawley.calculateHandicap();
			jomccorkle.calculateHandicap();

			Calendar fivedaysfromnow = Calendar.getInstance();
			fivedaysfromnow.add(Calendar.DATE, 5);
			Calendar sixdaysfromnow = Calendar.getInstance();
			sixdaysfromnow.add(Calendar.DATE, 6);
			Calendar sevendaysfromnow = Calendar.getInstance();
			sevendaysfromnow.add(Calendar.DATE, 7);
			Calendar eightdaysfromnow = Calendar.getInstance();
			eightdaysfromnow.add(Calendar.DATE, 8);
			List<User> players = new ArrayList<User>();
			players.add(scott);
			players.add(jeff);
			TeeTime teetime1 = new TeeTime(fivedaysfromnow.getTime(), champions.getCourses().get(0).getTeeSets().get(0), champions.getCourses().get(0), players, false, 4);
			teetime1.setOrganizer(steve);
			for (TeeTimeParticipant participant : teetime1.getParticipants()) {
				participant.addScore(90, 90);
			}
			persist(teetime1);
			TeeTime teetime2 = new TeeTime(sixdaysfromnow.getTime(), champions.getCourses().get(0).getTeeSets().get(0), champions.getCourses().get(0), players, false, 4);
			teetime2.setOrganizer(steve);
			for (TeeTimeParticipant participant : teetime2.getParticipants()) {
				participant.addScore(89, 89);
			}
			persist(teetime2);
			TeeTime teetime3 = new TeeTime(sevendaysfromnow.getTime(), champions.getCourses().get(0).getTeeSets().get(0), champions.getCourses().get(0), players, false, 4);
			teetime3.setOrganizer(steve);
			for (TeeTimeParticipant participant : teetime3.getParticipants()) {
				participant.addScore(87, 87);
			}
			persist(teetime3);
			TeeTime teetime4 = new TeeTime(eightdaysfromnow.getTime(), champions.getCourses().get(0).getTeeSets().get(0), champions.getCourses().get(0), players, false, 4);
			teetime4.setOrganizer(steve);
			for (TeeTimeParticipant participant : teetime4.getParticipants()) {
				participant.addScore(85, 85);
			}
			persist(teetime4);
			
			Tournament tournament1 = new Tournament();
			tournament1.setName("Example tournament");
			tournament1.setType(TournamentType.NET_SCORE);
			tournament1.addTeeTime(teetime1);
			tournament1.addTeeTime(teetime2);
			tournament1.addTeeTime(teetime3);
			tournament1.addTeeTime(teetime4);
			persist(tournament1);
			
			utx.commit();
			log.info("Seed data successfully imported");
		} catch (Exception e) {
			log.error("Import failed. Seed data will not be available.", e);
			try {
				if (utx.getStatus() == Status.STATUS_ACTIVE) {
					try {
						utx.rollback();
					} catch (Exception rbe) {
						log.error("Error rolling back transaction", rbe);
					}
				}
			} catch (Exception se) {
			}
		}
	}

	private void persist(List<?> entities) {
		for (Object e : entities) {
			persist(e);
		}
	}

	private void persist(Object entity) {
		// use a try-catch block here so we can capture identity
		// of entity that fails to persist
		try {
			entityManager.persist(entity);
			 entityManager.flush();
		} catch (ConstraintViolationException e) {
			throw new PersistenceException("Cannot persist invalid entity: " + entity);
		} catch (PersistenceException e) {
			throw new PersistenceException("Error persisting entity: " + entity, e);
		}
	}

}
