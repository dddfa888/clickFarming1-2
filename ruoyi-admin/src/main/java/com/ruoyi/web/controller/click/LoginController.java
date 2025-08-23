package com.ruoyi.web.controller.click;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.click.domain.vo.LoginModel;
import com.ruoyi.click.domain.vo.UserRegisterModel;
import com.ruoyi.click.service.IMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private IMUserService mUserService;

    @Autowired
    private TokenService tokenService;
    /**
     * 注册
     */
    @PostMapping("/register")
    public AjaxResult register(@Validated @RequestBody UserRegisterModel model) {
        mUserService.userRegister(model);
        return AjaxResult.success();
    }

    @PostMapping("/login")
    public AjaxResult login(@Validated @RequestBody LoginModel model) {
        MUser user = mUserService.getUserOne(model.getLoginAccount(), model.getLoginPassword());

        if (user.getStatus()==0) {
            return AjaxResult.warn("用户被禁用");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setmUser(user);
        loginUser.setUserId(Long.valueOf(user.getUid()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(model.getLoginAccount(), model.getLoginPassword());
        authenticationToken.setDetails(loginUser);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = tokenService.createToken(loginUser);
        // 获取用户是否认证和获取用户金额
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        String address = AddressUtils.getRealAddressByIP(ip);
        user.setLastLoginIp(ip);
        user.setLastLoginIpAddress(address);
        mUserService.updateMUser(user);

        HashMap<String, Object> map = new HashMap<>();
        user.setLoginPassword("***************");
        user.setFundPassword("***************");
        map.put("token", token);
        map.put("date", user);
        return AjaxResult.success(map);
    }
}
