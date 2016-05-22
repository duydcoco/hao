package com.hao.app.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Goods entity.
 */
public class GoodsDTO implements Serializable {

    private Long id;

    private String name;

    private Double value;

    private LocalDate lastUpdateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    public LocalDate getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDate lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GoodsDTO goodsDTO = (GoodsDTO) o;

        if ( ! Objects.equals(id, goodsDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", lastUpdateTime='" + lastUpdateTime + "'" +
            '}';
    }
}
