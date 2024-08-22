package com.yuki.Domain.Entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangPasswordDto {

    @NotBlank(message = "旧密码不能为空")
    private String passwordOld;

    @NotBlank(message = "新密码不能为空")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirm;
}
