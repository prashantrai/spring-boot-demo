package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.APIException;
import com.example.demo.model.Player;
import com.example.demo.service.SoccerService;

//import org.springframework.data.domain.PageRequest;

@RestController
@RequestMapping("players")
public class PlayerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private SoccerService soccerService;
	
	/*
	 *  List all players (with team) - Works but no pagination
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Player>> getAllPlayers() {
		LOGGER.info("getAllPlayers called");
		List<Player> teams = soccerService.getAllPlayers();
		return new ResponseEntity<List<Player>>(teams, HttpStatus.OK);
	}
	
	/*
	 *  List all players (with team) - with Pagination
	 *  
	 *  This approach is called Static Paging where spring data is taking care of offset and limit. 
	 *  We can also implement Dynamic Paging (little complex i.e. more coding can be seen here https://dzone.com/articles/pagination-in-springboot-applications)
	 *  
	 *  Reference: https://www.javainuse.com/spring/SpringBootUsingPagination
	 *  
	 *  URL: /players/pageable?page=0&size=3&sort=name
	 */
	@GetMapping(value="pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Page<Player>> getAllPlayersPageable(Pageable pageable) {
		LOGGER.info("getAllPlayers called");
		Page<Player> teams = soccerService.getAllPlayersPageable(pageable);
		return new ResponseEntity<Page<Player>>(teams, HttpStatus.OK);
	}

	/*
	 *  Get player for given id
	 */
	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Player> getPlayerById(@PathVariable("id") long id) {
		LOGGER.info("getPlayerById called");
		Player player = soccerService.getPlayerById(id);
		return new ResponseEntity<Player>(player, HttpStatus.OK);
	}
	
	/*
	 *  Get a player for given name 
	 *  */
	@GetMapping(value = "names/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Player> getPlayerByName(@PathVariable("name") String name) {
		Player player = soccerService.getPlayerByName(name);  // returns the exact match
		return new ResponseEntity<Player>(player, HttpStatus.OK);
	}
	
	/* Get all players matching input string 
	 * 
	 *  e.g. players/names?name=abc
	 */
	@GetMapping(value = "names", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Player>> getPlayerByNameStartsWith(@RequestParam String name) {
		List<Player> players = soccerService.getPlayerByNameStartsWith(name); // returns all start with the input param
		return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
	}
	
	// Get player for given team 
	// List all players for given teamId
	@GetMapping(value = "teams/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Player>> getAllTeamPlayers(@PathVariable("teamId") long teamId) {
		List<Player> players = soccerService.getAllTeamPlayers(teamId);
		return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
	}
	
	/* Get all players matching input string/teamName
	 * 
	 *  e.g. players/teams?teamName=Barcelona2
	 */
	@GetMapping(value = "teams", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Player>> getPlayerByTeamName(@RequestParam String teamName) {
		List<Player> players = soccerService.getAllPlayersByTeamName(teamName); // returns all start with the input param
		return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
	}
	
	
	// Get players match all the query string params (multiple)
	/*private ResponseEntity<List<Player>> getPlayersByMatchingMoreSearchParamters(@RequestParam Map<String, String> allParams) {
		List<Player> players = soccerService.getPlayerByName(name);  // returns the exact match
//		List<Player> players = soccerService.getPlayerByNameStartsWith(name); // returns all start with the input param
		return new ResponseEntity<List<Player>>(players, HttpStatus.OK);
	}*/
	/*
	private ResponseEntity<List<Team>> getAllTeams(Pageable pageable) {
	LOGGER.info("getAllTeams called");
	Page<Team> teamEntities = teamRepository.findAll(pageable);
    
    PagedModel<AlbumModel> collModel = pagedResourcesAssembler
                        .toModel(teamEntities, albumModelAssembler);
     
    return new ResponseEntity<>(collModel,HttpStatus.OK);
	}*/
	

	// add player
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Player> addPlayerToTeam(@RequestBody Player player) {
		Player newPlayer;
		try {
			newPlayer = soccerService.addPlayerToTeam(player);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
		return new ResponseEntity<Player>(newPlayer, HttpStatus.CREATED);
	}
	
	/* Another version of addPlayer in case we only want to return the HTTP STATUS and not response payload 
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> addPlayerToTeam(@RequestBody Player player) throws Exception {
		Player newPlayer = soccerService.addPlayerToTeam(player);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	*/
	
	// add player
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
		Player newPlayer;
		try {
			newPlayer = soccerService.updatePlayer(player);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
		return new ResponseEntity<Player>(newPlayer, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity<?> deleteMovie(@PathVariable("id") int id) {
		try {
			soccerService.deletePlayerById(id);
		} catch (Exception e) {
			throw new APIException(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}

/*
 * Create players, search (by id, by name, by team) list players (all, by team)
 * update player data
 * 
 * 1. Create players
 * 2. Search Player - by id, by name, by team
 * 3. list all players (all, by team)  - try implementing pagination for this as well
 * 4. Update players
 * 5. API Exception
 * 6. Junit
 * 7. Pagination
 * 
 * ------ Above can be same as Employee with Department/Manager relation
 * 1. List employees with their department  (pagination can be part of it)
 * 	  a. Provide link for all the department with each employee
 * 
 * 2. List all the department with their employees (pagination can be part of it)
 *    a. Add link for each employee profile e.g. /employee/{id}
 *    
 * 
 * 
 * Refer below for Employee-Dept API for Add and Update - It's Good
 * https://asbnotebook.com/2019/09/10/jpa-one-to-many-example-spring-boot/
 * 
 * Refer below for Library and Book example: 
 * 
 * https://hellokoding.com/jpa-one-to-many-relationship-mapping-example-with-spring-boot-maven-and-mysql/
 */

/*
 * Useful URLs
 * 
 * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-
 * recursion
 * 
 * https://stackoverflow.com/questions/52656517/no-serializer-found-for-class-org-hibernate-proxy-pojo-bytebuddy-bytebuddyinterc
 * https://www.javacodemonk.com/difference-between-getone-and-findbyid-in-spring-data-jpa-3a96c3ff
 * 
 * For Some Custom API Exception Example: 
 * https://www.javaguides.net/2018/09/spring-boot-2-jpa-mysql-crud-example.html
 * 
 * 
 * Look here for a Employee-Department Expample: https://github.com/apoorva-joshi/EmployeeManagement/blob/master/employee-management/src/main/java/com/employee/management/controller/EmployeeController.java
 * 
 * Query Methods: 
 * 	https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-introduction-to-query-methods/
 * 
 * 
 * https://reflectoring.io/spring-boot-data-jpa-test/
 * 
 */
