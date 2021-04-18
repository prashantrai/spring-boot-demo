package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;


@Service
public class SoccerServiceImpl implements SoccerService {

	@Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
	
    
    @Override
	public List<Team> getAllTeams() {
		List<Team> teams = new ArrayList<>();
		teamRepository.findAll().forEach(team -> teams.add(team));
		
		return teams;
	}
    
    /**
	 * Search and return team for input teamId
	 */
	@Override
	public List<Player> getAllTeamPlayers(long teamId) {
        List<Player> players = playerRepository.findByTeamId(teamId);
        return players;
	}
	
	/**
	 * Search and return players by teamName
	 */
	@Override
	public List<Player> getAllPlayersByTeamName(String teamName) {
        List<Player> players = playerRepository.findByTeamName(teamName);
        return players;
	}

	@Override
	public List<Player> getAllPlayers() {
		List<Player> players = new ArrayList<>();
		playerRepository.findAll().forEach(player -> players.add(player));
		return players;
	}
	
	// getAllPlayers() with Pagination
	@Override
	public Page<Player> getAllPlayersPageable(Pageable pageable) {
		Page<Player> pageResult = playerRepository.findAll(pageable);
		return pageResult;
	}

	@Override
	public Player getPlayerById(long playerId) {
		Optional<Player> player = playerRepository.findById(playerId);
		if(!player.isPresent()) {
			return null;
		}
//		return playerRepository.findById(playerId).get();
		return player.get();
	}

	/**
	 * Search and return by player name
	 */
	@Override
	public Player getPlayerByName(String name) {
		Player player = playerRepository.findByName(name);
		
		return player;
	}
	
	/**
	 * Search and return by player name
	 */
	@Override
	public List<Player> getPlayerByNameStartsWith(String name) {
		List<Player> players = playerRepository.findByNameStartsWith(name);
		
		return players;
	}

	/**
	 * Returns team for input id
	 */
	@Override
	public Team getTeamById(long id) throws Exception {
		Optional<Team> team = teamRepository.findById(id);
		if(!team.isPresent()) {
			throw new Exception("resource not found: team doen't exist");
		}
		return team.get();
	}
	
	/**
	 * Add player to the team
	 */
	@Override	
	public Player addPlayerToTeam(Player player) throws Exception {
		
		String name = player.getName();
		String position = player.getPosition();
		int number = player.getNum();
		long teamId = player.getTeam().getId();
		
		Optional<Team> team = teamRepository.findById(teamId);

		if(!team.isPresent()) {
			throw new Exception("resource not found: team doen't exist");
		}
		
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setPosition(position);
        newPlayer.setNum(number);
        newPlayer.setTeam(team.get());
        newPlayer = playerRepository.save(newPlayer);
        
        return newPlayer;
        
	}
	
	
	/**
	 * Update player
	 * 
	 * @param playerEntity
	 * @return
	 * @throws Exception
	 */
	@Override
	public Player updatePlayer(Player playerEntity) throws Exception {
		Long id 		= playerEntity.getId();
		String name 	= playerEntity.getName();
		String position = playerEntity.getPosition();
		int number 		= playerEntity.getNum();
		long teamId 	= playerEntity.getTeam().getId();
		
		Optional<Team> team = teamRepository.findById(teamId);
		if(!team.isPresent()) {
			throw new Exception("resource not found: team doen't exist");
		}
		
		Optional<Player> player = playerRepository.findById(id);
		if(!player.isPresent()) {
			throw new Exception("resource not found: player doen't exist");
		}
		
		Player newPlayerEntity = player.get();
		newPlayerEntity.setName(name);
		newPlayerEntity.setNum(number);
		newPlayerEntity.setPosition(position);
		newPlayerEntity.setTeam(team.get());
		
		newPlayerEntity = playerRepository.save(newPlayerEntity);
		
		return newPlayerEntity;
	}
	
	/**
	 * Delete player
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void deletePlayerById(long id) throws Exception {
		
		Optional<Player> player = playerRepository.findById(id);
		if(!player.isPresent()) {
			throw new Exception("resource not found: player doesn't exist");
		}
		
		playerRepository.deleteById(id);
	}
	
}
