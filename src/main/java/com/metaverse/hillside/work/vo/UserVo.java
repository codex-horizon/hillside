package com.metaverse.hillside.work.vo;

import com.metaverse.hillside.core.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends AbstractVoEntity implements Serializable {

    /**
     * 账户Id
     */
    private Long accountId;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 移动手机
     */
    private String mobilePhone;
}
