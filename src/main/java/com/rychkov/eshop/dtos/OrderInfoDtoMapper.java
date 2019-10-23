package com.rychkov.eshop.dtos;

import net.minidev.json.JSONObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderInfoDtoMapper {
    OrderInfoDto jsonObjectToOrderInfoDto(JSONObject jsonObject);
    JSONObject orderInfoDtoToJsonObject(OrderInfoDto orderInfoDto);
}
