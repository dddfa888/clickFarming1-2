package com.ruoyi.web.controller.click;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.click.domain.MNotify;
import com.ruoyi.click.service.IMNotifyService;
import com.ruoyi.click.service.IMUserService;
import com.ruoyi.common.annotation.FrontAccess;
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
    @FrontAccess
    public TableDataInfo userList(HttpServletRequest request) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MNotify mNotify = new MNotify();
        mNotify.setUserId(userId);

        // 1. 先查询当前用户的通知（查询时还未标记为已读）
        List<MNotify> records = mNotifyService.selectMNotifyList(mNotify);
        List<MNotify> safeRecords = (records != null) ? records : new ArrayList<>();

        // 2. 按用户ID批量标记所有通知为已读（高效，单条SQL）
        if (!safeRecords.isEmpty()) { // 有记录才更新
            mNotifyService.updateAllReadByUserId(userId);
        }

        // 3. 提取返回字段（不变）
        List<Map<String, Object>> result = safeRecords.stream().map(notify -> {
            Map<String, Object> data = new HashMap<>(2);
            data.put("title", notify.getTitle() != null ? notify.getTitle() : "");
            data.put("content", notify.getContent() != null ? notify.getContent() : "");
            return data;
        }).collect(Collectors.toList());

        // 4. 返回结果（不变）
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setRows(result);
        dataInfo.setTotal(result.size());
        dataInfo.setCode(0);
        dataInfo.setMsg("查询成功");

        return dataInfo;
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
        mNotify.setIsRead(0);
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
    @FrontAccess
    public AjaxResult countNumByUser(HttpServletRequest request) {
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MNotify mNotify = new MNotify();
        mNotify.setUserId(userId);
        long num = mNotifyService.countNum(mNotify);
        return success(num);
    }

    /**
     * 铃铛右上角展示未读
     * @return
     */
    @GetMapping("/unread")
    @FrontAccess
    public AjaxResult unread(){
        // 获取未读数量
        int unreadCount = mNotifyService.selectUnread(getUserId());
        return AjaxResult.success("查询成功", unreadCount);
    }

}
