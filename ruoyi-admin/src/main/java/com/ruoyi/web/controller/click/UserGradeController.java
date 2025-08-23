package com.ruoyi.web.controller.click;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.service.IMUserService;
import com.ruoyi.click.service.IUserGradeService;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户等级Controller
 *
 * @author ruoyi
 * @date 2025-06-15
 */
@RestController
@RequestMapping("/api/grade")
public class UserGradeController extends BaseController
{
    @Autowired
    private IUserGradeService userGradeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IMUserService mUserService;

    /**
     * 查询用户等级列表
     * @param request
     * @return
     */
    @GetMapping("/userList")
    public AjaxResult userList(HttpServletRequest request)
    {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MUser mUser = mUserService.selectMUserByUid(userId);
        UserGrade userGrade = new UserGrade();
        List<UserGrade> list = userGradeService.selectUserGradeList(userGrade);

        HashMap<String, Object> map = new HashMap<>();
        map.put("userGrade",list);
        map.put("level",mUser.getLevel());
        return success(map);
    }

    /**
     * 查询用户等级列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserGrade userGrade)
    {
        startPage();
        List<UserGrade> list = userGradeService.selectUserGradeList(userGrade);
        return getDataTable(list);
    }

    /**
     * 导出用户等级列表
     */
    @Log(title = "用户等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserGrade userGrade)
    {
        List<UserGrade> list = userGradeService.selectUserGradeList(userGrade);
        ExcelUtil<UserGrade> util = new ExcelUtil<UserGrade>(UserGrade.class);
        util.exportExcel(response, list, "用户等级数据");
    }

    /**
     * 获取用户等级详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userGradeService.selectUserGradeById(id));
    }

    /**
     * 新增用户等级
     */
    @Log(title = "用户等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserGrade userGrade)
    {
        return toAjax(userGradeService.insertUserGrade(userGrade));
    }

    /**
     * 修改用户等级
     */
    @Log(title = "用户等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserGrade userGrade)
    {
        return toAjax(userGradeService.updateUserGrade(userGrade));
    }

    /**
     * 删除用户等级
     */
    @Log(title = "用户等级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userGradeService.deleteUserGradeByIds(ids));
    }
}
