package com.metaverse.hillside.work.entity;

import com.metaverse.hillside.core.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "data_source")
@Table(appliesTo = "data_source", comment = "数据源表")
@EntityListeners(AuditingEntityListener.class)
public class DataSourceEntity extends AbstractPoEntity implements Serializable {

    @Column(name = "driver_class_name", unique = false, nullable = false, columnDefinition = "varchar(255) comment '驱动名称'")
    private String driverClassName;

    @Column(name = "database_name", unique = false, nullable = false, columnDefinition = "varchar(255) comment '数据库名称'")
    private String databaseName;

    @Column(name = "jdbc_url", unique = false, nullable = false, columnDefinition = "varchar(255) comment 'jdbc连接'")
    private String jdbcUrl;

    @Column(name = "username", unique = false, nullable = false, columnDefinition = "varchar(255) comment '用户名'")
    private String username;

    @Column(name = "password", unique = false, nullable = false, columnDefinition = "varchar(255) comment '密码'")
    private String password;

}
