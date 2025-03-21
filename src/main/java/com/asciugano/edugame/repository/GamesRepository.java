package com.asciugano.edugame.repository;

import com.asciugano.edugame.model.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Games, Integer> {
}
