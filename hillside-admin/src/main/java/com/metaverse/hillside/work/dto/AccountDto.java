package com.metaverse.hillside.work.dto;

import com.metaverse.hillside.work.dto.groups.ValidatedGroups;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AccountDto implements Serializable {

    @NotNull(message = "账号Id 空", groups = ValidatedGroups.Modify.class)
    private Long id;

    @NotNull(message = "账号分类 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private Integer category;

    @NotNull(message = "账号状态 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private Integer state;

    @NotBlank(message = "账号 空", groups = {
            ValidatedGroups.Login.class,
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private String account;

    @NotBlank(message = "密码 空", groups = {
            ValidatedGroups.Login.class,
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private String password;

    @NotBlank(message = "昵称 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private String nickName;

    @NotBlank(message = "头像url 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private String avatarUrl;
}
