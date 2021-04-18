package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER") 
public class Player {

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

    @Column(name = "num")
    private int num;

    @Column(name = "position")
    private String position;
    
    @ManyToOne //(fetch = FetchType.LAZY) - it was giving serialization error/json mapping error - it works though for more details look at getone vs findbyid
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    //@RestResource(path = "playerTeam", rel="team")
    //@JsonIgnore
    /* Below helps avoid infinite recursion issue.
     * 
     * This is working - commented just to change the approach, uncomment this and in Team.java when you need to show the list of players for each team
     * @JsonBackReference // https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
     * private Team team;
    */
    
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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
    
}
