package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BalanceModel {

    @NotNull(message = "用户ID不能为空")
    private Long uid;

    @NotNull(message = "类型: 1:充值  2:返现")
    private int type1;

    //@NotNull(message = "请选择是增加还是减少")
    //private boolean increaseDecrease;

    //@DecimalMin(value = "0.01", inclusive = true, message = "余额必须大于0")
    @NotNull(message = "余额数值不能为空")
    private BigDecimal balance;


    private String reason;
}
