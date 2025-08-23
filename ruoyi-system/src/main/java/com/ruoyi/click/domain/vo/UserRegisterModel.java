package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterModel {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String loginAccount;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String loginPassword;

    /**
     * 资金密码
     */
    @NotBlank(message = "资金密码不能为空")
    private String fundPassword;

    /**
     * 邀请码
     */
    @NotBlank(message = "邀请码不能为空")
    private String invitationCode;
}
