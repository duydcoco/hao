package com.hao.app.web.rest.mapper;

import com.hao.app.domain.*;
import com.hao.app.web.rest.dto.ApiKeyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ApiKey and its DTO ApiKeyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApiKeyMapper {

    ApiKeyDTO apiKeyToApiKeyDTO(ApiKey apiKey);

    List<ApiKeyDTO> apiKeysToApiKeyDTOs(List<ApiKey> apiKeys);

    ApiKey apiKeyDTOToApiKey(ApiKeyDTO apiKeyDTO);

    List<ApiKey> apiKeyDTOsToApiKeys(List<ApiKeyDTO> apiKeyDTOs);
}
