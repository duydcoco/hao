package com.hao.app.repository;

import com.hao.app.domain.Goods;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Goods entity.
 */
@SuppressWarnings("unused")
public interface GoodsRepository extends JpaRepository<Goods,Long> {

}
