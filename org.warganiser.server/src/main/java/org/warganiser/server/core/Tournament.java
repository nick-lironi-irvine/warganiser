package org.warganiser.server.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Tournament", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Tournament {

	@Id
	private Long id;

	private String name;

	public Tournament(String name) {
		this.setName(name);
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
