package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TEAM") 
public class Team {

	/* Below works as well - a simple way to generate id
	
	Below strategies (AUTO and SEQUENCE) were not working and were giving Primary Key error if we insert 
	value from SQL editor first and then try to add record using API but IDENTITY is working
	
	@GeneratedValue(strategy=GenerationType.AUTO) 
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="player_sequence")  
	@SequenceGenerator(name="player_sequence", sequenceName = "PLAYER_SEQ")
	*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	/* Below helps avoid infinite recursion issue.
	 * 
	 * below is working - commented because just to change the approach
	 * un-comment when you need to show list of employees for each team
	 *  */
	//@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "team")
	//@JsonManagedReference // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
	
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
	private List<Player> players;

	
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

	/*
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	*/
}
