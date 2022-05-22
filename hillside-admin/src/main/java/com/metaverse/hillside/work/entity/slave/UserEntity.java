package com.metaverse.hillside.work.entity.slave;

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
@javax.persistence.Table(name = "user")
@Table(appliesTo = "user", comment = "用户表")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AbstractPoEntity implements Serializable {

    @Column(name = "account_id", unique = false, nullable = false, columnDefinition = "bigint(20) comment '账户Id'")
    private Long accountId;

    @Column(name = "gender", unique = false, nullable = false, columnDefinition = "tinyint(1) comment '性别'")
    private Integer gender;

    @Column(name = "age", unique = false, nullable = false, columnDefinition = "int(3) comment '年龄'")
    private Integer age;

    @Column(name = "name", unique = false, nullable = false, columnDefinition = "varchar(25) comment '姓名'")
    private String name;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "varchar(50) comment '电子邮箱'")
    private String email;

    @Column(name = "phone", unique = true, nullable = false, columnDefinition = "varchar(11) comment '移动手机'")
    private String phone;

}
