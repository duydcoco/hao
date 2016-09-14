package com.hao.app.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Strategy entity.
 */
public class StrategyDTO implements Serializable {

    private Long id;

    private String divType;

    private String divData;

    private Integer msgCode;

    private String errorMsg;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDivType() {
        return divType;
    }

    public void setDivType(String divType) {
        this.divType = divType;
    }
    public String getDivData() {
        return divData;
    }

    public void setDivData(String divData) {
        this.divData = divData;
    }
    public Integer getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StrategyDTO strategyDTO = (StrategyDTO) o;

        if ( ! Objects.equals(id, strategyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StrategyDTO{" +
            "id=" + id +
            ", divType='" + divType + "'" +
            ", divData='" + divData + "'" +
            ", msgCode='" + msgCode + "'" +
            ", errorMsg='" + errorMsg + "'" +
            '}';
    }
}
