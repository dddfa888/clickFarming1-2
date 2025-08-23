package com.ruoyi.web.controller.click;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import com.ruoyi.click.service.IMUserService;
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
 * 账变记录Controller
 *
 * @author ruoyi
 * @date 2025-06-16
 */
@RestController
@RequestMapping("/api/records")
public class MAccountChangeRecordsController extends BaseController
{
    @Autowired
    private IMAccountChangeRecordsService mAccountChangeRecordsService;

    @Autowired
    private IMUserService mUserService;

    @Autowired
    private TokenService tokenService;


    /**
     * 获取个人账变记录
     * @param request
     * @return
     */
    @GetMapping("/userList")
    public TableDataInfo userList(HttpServletRequest request)
    {
        startPage();
        Long userId = tokenService.getLoginUser(request).getmUser().getUid();
        MAccountChangeRecords mAccountChangeRecords = new MAccountChangeRecords();

        mAccountChangeRecords.setUid(String.valueOf(userId));
        mAccountChangeRecords.setTransactionType(1);
        mAccountChangeRecords.setType(0);
        List<MAccountChangeRecords> list = mAccountChangeRecordsService.selectMAccountChangeRecordsList(mAccountChangeRecords);

        return getDataTable(list);
    }

    /**
     * 查询账变记录列表
     */
    @GetMapping("/list")
    public TableDataInfo list(MAccountChangeRecords mAccountChangeRecords)
    {
        startPage();
        if(!StringUtils.isEmpty(mAccountChangeRecords.getLoginAccount())){
            MUser one = mUserService.getOne(new LambdaQueryWrapper<MUser>().eq(MUser::getLoginAccount, mAccountChangeRecords.getLoginAccount()));
            mAccountChangeRecords.setUid(String.valueOf(one.getUid()));
        }
        List<MAccountChangeRecords> list = mAccountChangeRecordsService.selectMAccountChangeRecordsList(mAccountChangeRecords);
        TableDataInfo dataTable = getDataTable(list);
        List<MAccountChangeRecords> rows = (List<MAccountChangeRecords>) dataTable.getRows();
        for (MAccountChangeRecords mAccountChangeRecord : rows) {
            MUser one = mUserService.selectMUserByUid(Long.valueOf(mAccountChangeRecord.getUid()));
            if (one != null){
                mAccountChangeRecord.setUserName(one.getLoginAccount());
            }else {
                mAccountChangeRecord.setUserName("该用户已删除");
            }
        }
        dataTable.setRows(rows);
        return dataTable;
    }

    /**
     * 个人的奖励历史记录
     */
    @GetMapping(value = "/selectMAccountChangeForeByUser")
    public TableDataInfo selectMAccountChangeForeByUser()
    {
        //startPage(); //暂时不分页
        List<Map<String,Object>> list = mAccountChangeRecordsService.selectMAccountChangeForeByUser();
        TableDataInfo dataTable = getDataTable(list);
        return dataTable;
    }

    /**
     * 个人信息与近两日利润 前台《订单详细信息》页
     */
    @GetMapping(value = "/getUserProfitInfo")
    public AjaxResult getUserProfitInfo()
    {
        return success(mAccountChangeRecordsService.getUserProfitInfo());
    }

    /**
     * 导出账变记录列表
     */
    @Log(title = "账变记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MAccountChangeRecords mAccountChangeRecords)
    {
        List<MAccountChangeRecords> list = mAccountChangeRecordsService.selectMAccountChangeRecordsList(mAccountChangeRecords);
        ExcelUtil<MAccountChangeRecords> util = new ExcelUtil<MAccountChangeRecords>(MAccountChangeRecords.class);
        util.exportExcel(response, list, "账变记录数据");
    }

    /**
     * 获取账变记录详细信息
     */
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(mAccountChangeRecordsService.selectMAccountChangeRecordsByUuid(uuid));
    }

    /**
     * 新增账变记录
     */
    @Log(title = "账变记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MAccountChangeRecords mAccountChangeRecords)
    {
        return toAjax(mAccountChangeRecordsService.insertMAccountChangeRecords(mAccountChangeRecords));
    }

    /**
     * 修改账变记录
     */
    @Log(title = "账变记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MAccountChangeRecords mAccountChangeRecords)
    {
        return toAjax(mAccountChangeRecordsService.updateMAccountChangeRecords(mAccountChangeRecords));
    }

    /**
     * 删除账变记录
     */
    @Log(title = "账变记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(mAccountChangeRecordsService.deleteMAccountChangeRecordsByUuids(uuids));
    }
}
