package com.ruoyi.web.controller.click;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.click.domain.MNotify;
import com.ruoyi.click.service.IMNotifyService;
import com.ruoyi.click.service.IMUserService;
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
 * 通知Controller
 *
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/api/notify")
public class MNotifyController extends BaseController {
    @Autowired
    private IMNotifyService mNotifyService;

    @Autowired
    private IMUserService mUserService;

    @Autowired
    private TokenService tokenService;


    @GetMapping("/userList")
    public AjaxResult userList(HttpServletRequest request) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MNotify mNotify = new MNotify();
        mNotify.setUserId(userId);
        List<MNotify> mNotifies = mNotifyService.selectMNotifyList(mNotify);
        //批量修改通知状态为已读
        for (MNotify notify : mNotifies) {
            notify.setRead(true);
            mNotifyService.updateMNotify(notify);
        }

        return success(mNotifies);
    }

    /**
     * 查询通知列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MNotify mNotify) {
        startPage();
        List<MNotify> list = mNotifyService.selectMNotifyList(mNotify);
        return getDataTable(list);
    }

    /**
     * 导出通知列表
     */
    @Log(title = "通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MNotify mNotify) {
        List<MNotify> list = mNotifyService.selectMNotifyList(mNotify);
        ExcelUtil<MNotify> util = new ExcelUtil<MNotify>(MNotify.class);
        util.exportExcel(response, list, "通知数据");
    }

    /**
     * 获取通知详细信息
     */
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid) {
        return success(mNotifyService.selectMNotifyByUid(uid));
    }

    /**
     * 新增通知
     */
    @Log(title = "通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MNotify mNotify) {
        MUser mUser = mUserService.selectMUserByUid(mNotify.getUserId());
        if (mUser != null) {
            mNotify.setUserName(mUser.getLoginAccount());
        }
        return toAjax(mNotifyService.insertMNotify(mNotify));
    }

    /**
     * 修改通知
     */
    @Log(title = "通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MNotify mNotify) {
        MNotify notifyByUid = mNotifyService.selectMNotifyByUid(mNotify.getUid());
        if (!Objects.equals(notifyByUid.getUserId(), mNotify.getUserId())) {
            MUser mUser = mUserService.selectMUserByUid(mNotify.getUserId());
            if (mUser != null) {
                mNotify.setUserName(mUser.getLoginAccount());
            }
        }
        return toAjax(mNotifyService.updateMNotify(mNotify));
    }

    /**
     * 删除通知
     */
    @Log(title = "通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable Long[] uids) {
        return toAjax(mNotifyService.deleteMNotifyByUids(uids));
    }


    @PostMapping("/countNum")
    public AjaxResult countNum(@RequestBody MNotify mNotify) {
        long num = mNotifyService.countNum(mNotify);
        return success(num);
    }

    @GetMapping("/countNumByUser")
    public AjaxResult countNumByUser(HttpServletRequest request) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MNotify mNotify = new MNotify();
        mNotify.setUserId(userId);
        long num = mNotifyService.countNum(mNotify);
        return success(num);
    }

}
