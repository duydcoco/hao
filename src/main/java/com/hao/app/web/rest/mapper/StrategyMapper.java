package com.hao.app.web.rest.mapper;

import com.hao.app.domain.*;
import com.hao.app.web.rest.dto.StrategyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Strategy and its DTO StrategyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StrategyMapper {

    StrategyDTO strategyToStrategyDTO(Strategy strategy);

    List<StrategyDTO> strategiesToStrategyDTOs(List<Strategy> strategies);

    Strategy strategyDTOToStrategy(StrategyDTO strategyDTO);

    List<Strategy> strategyDTOsToStrategies(List<StrategyDTO> strategyDTOs);
}
