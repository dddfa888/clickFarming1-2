package com.ruoyi.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.framework.web.service.TokenService;
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
import com.ruoyi.business.domain.MRewardRecord;
import com.ruoyi.business.service.IMRewardRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 奖励记录Controller
 * 
 * @author ruoyi
 * @date 2025-06-28
 */
@RestController
@RequestMapping("/api/rewardRecord")
public class MRewardRecordController extends BaseController
{
    @Autowired
    private IMRewardRecordService mRewardRecordService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询奖励记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MRewardRecord mRewardRecord)
    {
        startPage();
        List<MRewardRecord> list = mRewardRecordService.selectMRewardRecordList(mRewardRecord);
        return getDataTable(list);
    }

    /**
     * 查询一个用户的奖励记录
     */
    @GetMapping("/selectByUserId/{userId}")
    public TableDataInfo selectByUserId(@PathVariable("userId") Long userId)
    {
        startPage();
        List<MRewardRecord> list = mRewardRecordService.selectByUserId(userId);
        return getDataTable(list);
    }

    /**
     * 查询当前用户的奖励记录
     */
    @GetMapping("/selectSimpleByUserId")
    public AjaxResult selectByCurrentUser(HttpServletRequest request)
    {
        //startPage();
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        return success(mRewardRecordService.selectSimpleByUserId(userId));
    }

    /**
     * 导出奖励记录列表
     */
    @Log(title = "奖励记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MRewardRecord mRewardRecord)
    {
        List<MRewardRecord> list = mRewardRecordService.selectMRewardRecordList(mRewardRecord);
        ExcelUtil<MRewardRecord> util = new ExcelUtil<MRewardRecord>(MRewardRecord.class);
        util.exportExcel(response, list, "奖励记录数据");
    }

    /**
     * 获取奖励记录详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mRewardRecordService.selectMRewardRecordById(id));
    }

    /**
     * 新增奖励记录
     */
    @Log(title = "奖励记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MRewardRecord mRewardRecord)
    {
        return toAjax(mRewardRecordService.insertMRewardRecord(mRewardRecord));
    }

    /**
     * 修改奖励记录
     */
    @Log(title = "奖励记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MRewardRecord mRewardRecord)
    {
        return toAjax(mRewardRecordService.updateMRewardRecord(mRewardRecord));
    }

    /**
     * 删除奖励记录
     */
    @Log(title = "奖励记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mRewardRecordService.deleteMRewardRecordByIds(ids));
    }
}
