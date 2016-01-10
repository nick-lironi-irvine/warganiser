package org.warganiser.server.tournament;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.warganiser.server.participant.Participant;
import org.warganiser.server.player.Player;

@Entity
@Table(name = "Tournament")
public class Tournament {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column
	private Integer points;
	
	@OneToMany(mappedBy = "tournament", orphanRemoval = true, fetch = FetchType.LAZY, cascade = (CascadeType.PERSIST))
	private Set<Participant> participants = new HashSet<>();

	public Tournament() {
		// Default for Hibernate
		super();
	}

	public Tournament(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Set<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Participant> participants) {
		this.participants = participants;
	}

	public void addParticipant(Player player) {
		this.participants.add(new Participant(this, player));
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Tournament))
			return false;
		Tournament castOther = (Tournament) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name).toString();
	}

}
