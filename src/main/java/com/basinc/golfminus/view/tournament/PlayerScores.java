package com.basinc.golfminus.view.tournament;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.User;

/**
 * Holder for player scores.
 * The first entry will always be completed for each player as it will container the player's name
 * The remaining entries will be completed if the player played that round.
 * 
 * @author SBasinger
 *
 */
public class PlayerScores implements Comparable<PlayerScores> {
	private static Logger log = LoggerFactory.getLogger(PlayerScores.class);
	
	private User user;
	private List<TeeTimeParticipant> playerScores = new ArrayList<TeeTimeParticipant>();
	private Integer total;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<TeeTimeParticipant> getPlayerScores() {
		return playerScores;
	}

	public void setPlayerScores(List<TeeTimeParticipant> playerScores) {
		this.playerScores = playerScores;
	}
	
	public void addScore(TeeTimeParticipant participant) {
		getPlayerScores().add(participant);
		if (participant != null) {
			setTotal(0);
			calculateTotal();
			log.info("Score added for {} gives them {} scores. ",user.getName(),playerScores.size());
			log.info("         and a total of {}",getTotal());
		} else {
			setTotal(null);
		}
		calculateTotal();
	}


	private void calculateTotal() {
		setTotal(0);
		for (TeeTimeParticipant participant : getPlayerScores()) {
			if (participant == null) {
				setTotal(null);
				return;
			}
			if (participant.getScoreToPar() != null && getTotal() != null)
				setTotal(getTotal()+participant.getScoreToPar());
			else 
				setTotal(null);
		}
		return;
	}

	public int compareTo(PlayerScores score2) {
		if (getTotal() == null && score2.getTotal()==null)
			return 0;
		if (getTotal() == null)
			return -1;
		if (score2.getTotal()==null)
			return 1;
		return getTotal().compareTo(score2.getTotal());
	}
	
}
