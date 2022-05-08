package com.metaverse.hillside.work.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class TempAccountDto implements Serializable {

    @NotBlank(message = "账号 空")
    private String account;

    @NotBlank(message = "密码 空")
    private String password;
}
