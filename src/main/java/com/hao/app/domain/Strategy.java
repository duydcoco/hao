package com.hao.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Strategy.
 */
@Entity
@Table(name = "strategy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "strategy")
public class Strategy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "div_type")
    private String divType;

    @Column(name = "div_data")
    private String divData;

    @Column(name = "msg_code")
    private Integer msgCode;

    @Column(name = "error_msg")
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
        Strategy strategy = (Strategy) o;
        if(strategy.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, strategy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Strategy{" +
            "id=" + id +
            ", divType='" + divType + "'" +
            ", divData='" + divData + "'" +
            ", msgCode='" + msgCode + "'" +
            ", errorMsg='" + errorMsg + "'" +
            '}';
    }
}
