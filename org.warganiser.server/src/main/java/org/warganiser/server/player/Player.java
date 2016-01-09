package org.warganiser.server.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Represents a player (person) who may play in several Tournaments over time.
 */
@Entity
@Table(name = "Players")
public class Player {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;

	public Player() {
		// Default for Hibernate
		super();
	}

	public Player(String name) {
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

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Player))
			return false;
		Player castOther = (Player) other;
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
