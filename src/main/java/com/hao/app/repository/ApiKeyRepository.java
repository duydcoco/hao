package com.hao.app.repository;

import com.hao.app.domain.ApiKey;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApiKey entity.
 */
@SuppressWarnings("unused")
public interface ApiKeyRepository extends JpaRepository<ApiKey,Long> {
    List<ApiKey> findByGroupId(Long groupId);
}
