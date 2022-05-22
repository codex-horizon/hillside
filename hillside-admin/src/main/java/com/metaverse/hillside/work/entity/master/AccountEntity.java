package com.metaverse.hillside.work.entity.master;

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
@javax.persistence.Table(name = "account")
@Table(appliesTo = "account", comment = "账户表")
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity extends AbstractPoEntity implements Serializable {

    @Column(name = "category", unique = false, nullable = false, columnDefinition = "tinyint(1) comment '账号分类'")
    private Integer category;

    @Column(name = "account", unique = true, nullable = false, columnDefinition = "varchar(255) comment '账号'")
    private String account;

    @Column(name = "password", unique = false, nullable = false, columnDefinition = "varchar(255) comment '密码'")
    private String password;

    @Column(name = "nick_name", unique = false, nullable = false, columnDefinition = "varchar(255) comment '昵称'")
    private String nickName;

    @Column(name = "avatar_url", unique = false, nullable = false, columnDefinition = "varchar(255) comment '头像url'")
    private String avatarUrl;

    @Column(name = "state", unique = false, nullable = false, columnDefinition = "tinyint(1) comment '账号状态'")
    private Integer state;
}
