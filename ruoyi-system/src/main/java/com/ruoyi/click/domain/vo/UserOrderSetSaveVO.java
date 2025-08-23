package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserOrderSetSaveVO {

    @NotNull(message = "用户主键不能为空")
    private Long userId;

    //@NotBlank(message = "配置不能为空")
    private String orderSetData;
}
