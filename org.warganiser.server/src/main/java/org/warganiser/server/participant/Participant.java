package org.warganiser.server.participant;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.warganiser.server.player.Player;
import org.warganiser.server.tournament.Tournament;

/**
 * Represents a {@link Player} taking part in a {@link Tournament}
 */
@Entity
@Table(name = "Participant")
public class Participant {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name="tournament_id", foreignKey = @ForeignKey(name = "FK_Tournament"))
	private Tournament tournament;
	
	@ManyToOne()
	@JoinColumn(name="player_id", foreignKey = @ForeignKey(name = "FK_Player"))
	private Player player;
	
	public Participant(){
		super();
	}

	public Participant(Tournament tournament, Player player) {
		super();
		this.tournament = tournament;
		this.player = player;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Participant))
			return false;
		Participant castOther = (Participant) other;
		return new EqualsBuilder().append(tournament, castOther.tournament).append(player, castOther.player).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(tournament).append(player).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("tournament", tournament).append("player", player)
				.toString();
	}
	
}
