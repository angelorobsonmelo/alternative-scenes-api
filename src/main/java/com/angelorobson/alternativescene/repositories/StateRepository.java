package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
