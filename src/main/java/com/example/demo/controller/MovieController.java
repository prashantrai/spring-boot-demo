package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;

@RestController
@RequestMapping("movies")
public class MovieController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private MovieService movieService;

	//@GetMapping("/movies") // this works too
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<List<Movie>> getAllMovies() {
		
		LOGGER.info("getAllMovies called");
		
		// return movieService.getAllMovies(); //this works too
		List<Movie> movies = movieService.getAllMovies();
		return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
	}

//	@GetMapping("/movies/{id}") // this works too
	@GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<Movie> getMovieById(@PathVariable("id") int id) {
		
		LOGGER.info("getMovieById invoked for id: {}", id);
		
		Movie movie = movieService.getMovieById(id);
		if (movie == null) {
			// return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //this works too
			return new ResponseEntity<Movie>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Movie>(movie, HttpStatus.OK);
	}

//	@PostMapping("/movies") // this works too
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> saveMovie(@RequestBody Movie movie) {
		movieService.saveOrUpdate(movie);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

//	@DeleteMapping("/movies/{id}") // works
	@DeleteMapping("{id}")
	private ResponseEntity<?> deleteMovie(@PathVariable("id") int id) {
		movieService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

/*
 * TODO 1. Return http status or json in case of success 2. Return error json on
 * case of error 3. Create API exception class
 */
