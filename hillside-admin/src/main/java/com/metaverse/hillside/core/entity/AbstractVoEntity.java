package com.metaverse.hillside.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * 抽象实体基类
 */
@Data
@MappedSuperclass
public abstract class AbstractVoEntity implements Serializable {

    private Long id;

    private Integer status;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createdDate;
//    private ZonedDateTime createdDate = ZonedDateTime.now();
//    private ZonedDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDate;
//    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();
//    private ZonedDateTime lastModifiedDate;

}
