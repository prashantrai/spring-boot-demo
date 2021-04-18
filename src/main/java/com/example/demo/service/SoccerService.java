package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Player;
import com.example.demo.model.Team;

public interface SoccerService {
	List<Team> getAllTeams();
	Team getTeamById(long id) throws Exception;
	
	List<Player> getAllPlayers();
	Page<Player> getAllPlayersPageable(Pageable pageable);
	List<Player> getAllTeamPlayers(long teamId);
	List<Player> getPlayerByNameStartsWith(String name);
	List<Player> getAllPlayersByTeamName(String teamName);
	Player getPlayerById(long playerId);
	Player getPlayerByName(String name);
	
	Player addPlayerToTeam(Player player) throws Exception;
	Player updatePlayer(Player playerEntity) throws Exception;
	void deletePlayerById(long id) throws Exception;
}
