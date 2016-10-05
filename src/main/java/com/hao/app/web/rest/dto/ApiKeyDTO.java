package com.hao.app.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the ApiKey entity.
 */
public class ApiKeyDTO implements Serializable {

    private Long id;

    @NotNull
    private Long groupId;

    @NotNull
    private String kongKeyId;

    @NotNull
    private String keyValue;

    @NotNull
    private String status;

    @NotNull
    private LocalDate createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public String getKongKeyId() {
        return kongKeyId;
    }

    public void setKongKeyId(String kongKeyId) {
        this.kongKeyId = kongKeyId;
    }
    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApiKeyDTO apiKeyDTO = (ApiKeyDTO) o;

        if ( ! Objects.equals(id, apiKeyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApiKeyDTO{" +
            "id=" + id +
            ", groupId='" + groupId + "'" +
            ", kongKeyId='" + kongKeyId + "'" +
            ", keyValue='" + keyValue + "'" +
            ", status='" + status + "'" +
            ", createTime='" + createTime + "'" +
            '}';
    }
}
