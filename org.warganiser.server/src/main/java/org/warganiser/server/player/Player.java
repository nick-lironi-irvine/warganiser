package org.warganiser.server.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
