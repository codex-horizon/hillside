package com.metaverse.hillside.work.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    @NotNull(message = "账号Id 空")
    private Long id;

    @NotNull(message = "账号分类 空")
    private Integer category;

    @NotBlank(message = "账号 空")
    private String account;

    @NotBlank(message = "密码 空")
    private String password;

    @NotBlank(message = "昵称 空")
    private String nickName;

    @NotBlank(message = "头像url 空")
    private String avatarUrl;
}
