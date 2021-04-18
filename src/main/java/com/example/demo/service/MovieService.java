package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	public List<Movie> getAllMovies() {
		List<Movie> movies = new ArrayList<>();
		movieRepository.findAll().forEach(movie -> movies.add(movie));
		
		return movies;
	}
	
	public Movie getMovieById(int id) {
		Optional<Movie> movie = movieRepository.findById(id);
		if(!movie.isPresent()) {
			return null;
		}
		return movieRepository.findById(id).get();
	}
	
	public void saveOrUpdate(Movie movie) {
		movieRepository.save(movie);
	}
	
	public void deleteById(int id) {
		movieRepository.deleteById(id);
	}
}
