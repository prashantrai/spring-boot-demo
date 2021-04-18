package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.APIException;
import com.example.demo.model.Team;
import com.example.demo.service.SoccerService;

//import org.springframework.data.domain.Page;	
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("teams")
public class TeamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private SoccerService soccerService;
	
	//@Autowired
	//private TeamRepository teamRepository; //for pagination
	
	// List all teams
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Team>> getAllTeams() {
		LOGGER.info("getAllTeams called");
		List<Team> players = soccerService.getAllTeams();
		return new ResponseEntity<List<Team>>(players, HttpStatus.OK);
	}
	
	/*private ResponseEntity<List<Team>> getAllTeams(Pageable pageable) {
		LOGGER.info("getAllTeams called");
		Page<Team> teamEntities = teamRepository.findAll(pageable);
        
        PagedModel<AlbumModel> collModel = pagedResourcesAssembler
                            .toModel(teamEntities, albumModelAssembler);
         
        return new ResponseEntity<>(collModel,HttpStatus.OK);
	}*/

	// List all players for given teamId
	@GetMapping(value = "{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Team> getAllTeamPlayers(@PathVariable("teamId") long teamId) {
		Team team;
		try {
			team = soccerService.getTeamById(teamId);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
		return new ResponseEntity<Team>(team, HttpStatus.OK);
	}

}


