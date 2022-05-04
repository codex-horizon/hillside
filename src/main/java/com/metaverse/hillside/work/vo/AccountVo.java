package com.metaverse.hillside.work.vo;

import com.metaverse.hillside.core.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountVo extends AbstractVoEntity implements Serializable {

    /**
     * 账号分类
     */
    private Integer category;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像url
     */
    private String avatarUrl;
}
