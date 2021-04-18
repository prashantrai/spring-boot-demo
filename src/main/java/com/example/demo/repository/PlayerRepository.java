package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Player;

//@RepositoryRestResource(collectionResourceRel = "players", path = "players")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByTeamId(long teamId);
    List<Player> findByTeamName(String teamName);
    
    Player findByName(String name);
    
    // For LIKE feature, e.g. %name%
    List<Player> findByNameContaining(String name);
    
    // For LIKE feature, e.g. name%
    List<Player> findByNameStartsWith(String name);
    
    //@Query( "select o from Player o where name  in :ids" )
    //List<Player> findByInventoryIds(@Param("ids") List<Long> inventoryIdList);
}