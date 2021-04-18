package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Movie;

/**
 * interface which will extend CrudRepository. We don’t have to provide
 * implementation, Spring data will automatically do it for us.
 *
 * JpaRepository extends PagingAndSortingRepository which in turn extends
 * CrudRepository
 * 
 *   >CrudRepository mainly provides CRUD functions. 
 *   >PagingAndSortingRepository provides methods to do pagination and sorting records. 
 *   >JpaRepository provides some JPA-related methods such as flushing the persistence context and
 * 		deleting records in a batch.
 */
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}