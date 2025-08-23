package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BackOperateVo {

    @NotNull(message = "ID不能为空")
    private Long id;

    private String notes;
}
