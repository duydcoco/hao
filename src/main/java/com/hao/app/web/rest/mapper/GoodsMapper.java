package com.hao.app.web.rest.mapper;

import com.hao.app.domain.*;
import com.hao.app.web.rest.dto.GoodsDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Goods and its DTO GoodsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsMapper {

    GoodsDTO goodsToGoodsDTO(Goods goods);

    List<GoodsDTO> goodsToGoodsDTOs(List<Goods> goods);

    Goods goodsDTOToGoods(GoodsDTO goodsDTO);

    List<Goods> goodsDTOsToGoods(List<GoodsDTO> goodsDTOs);
}
