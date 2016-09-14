package com.hao.app.repository;

import com.hao.app.domain.Strategy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Strategy entity.
 */
@SuppressWarnings("unused")
public interface StrategyRepository extends JpaRepository<Strategy,Long> {

}
