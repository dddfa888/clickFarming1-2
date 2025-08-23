package com.ruoyi.click.domain.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class WithdrawVo {

//    @DecimalMin(value = "0.01", inclusive = true, message = "余额必须大于0")
//    private BigDecimal amount;
    //前端填写的数字可能是其他格式，以逗号为小数点，例如：1234.56写作 1234,56，无法直接解析成BigDecimal，需要服务端加以判断和转换，所以先接收字符串形式。
    @NotBlank(message = "取款数额不能为空")
    private String amount;

    @NotBlank(message = "资金密码不能为空")
    private String fundPassword;
}
