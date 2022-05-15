package com.metaverse.hillside.work.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    /**
     * 账户Id
     */
    @NotNull(message = "账户Id 空")
    private Long accountId;

    /**
     * 性别
     */
    @NotNull(message = "性别 空")
    private Integer gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄 空")
    private Integer age;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名 空")
    private String name;

    /**
     * 电子邮箱
     */
    @NotBlank(message = "电子邮箱 空")
    @Email(message = "电子邮箱 格式有误")
    private String email;

    /**
     * 移动手机
     */
    @NotBlank(message = "移动手机 空")
    @Length(min = 11, max = 11, message = "移动手机 长度限制为{max}")
    private String mobilePhone;
}
