package com.metaverse.hillside.work.dto;

import com.metaverse.hillside.work.dto.groups.ValidatedGroups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    @NotNull(message = "用户Id 空", groups = ValidatedGroups.Modify.class)
    private Long id;

    /**
     * 账户Id
     */
    @NotNull(message = "账户Id 空", groups = {
            ValidatedGroups.Add.class
    })
    private Long accountId;

    /**
     * 性别
     */
    @NotNull(message = "性别 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private Integer gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private Integer age;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    private String name;

    /**
     * 电子邮箱
     */
    @NotBlank(message = "电子邮箱 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    @Email(message = "电子邮箱 格式有误")
    private String email;

    /**
     * 移动手机
     */
    @NotBlank(message = "移动手机 空", groups = {
            ValidatedGroups.Add.class,
            ValidatedGroups.Modify.class
    })
    @Length(min = 11, max = 11, message = "移动手机 长度限制为{max}")
    private String phone;
}
