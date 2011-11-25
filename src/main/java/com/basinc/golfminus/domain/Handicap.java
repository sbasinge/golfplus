package com.basinc.golfminus.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.jboss.solder.core.Veto;

@Entity
@Veto
public class Handicap extends BaseEntity {

    @NotNull
	@Column
	private BigDecimal handicap;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
	private Date date;

    public Handicap() {}
    
    public Handicap(BigDecimal handicap, Date date) {
    	setHandicap(handicap);
    	setDate(date);
    }
    
	public BigDecimal getHandicap() {
		return handicap;
	}

	public void setHandicap(BigDecimal handicap) {
		this.handicap = handicap;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Handicap [handicap=" + handicap + ", date=" + date + "]";
	}
}
