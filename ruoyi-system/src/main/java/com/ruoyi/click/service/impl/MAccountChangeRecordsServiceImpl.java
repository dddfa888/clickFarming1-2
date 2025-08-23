package com.ruoyi.click.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.business.mapper.OrderReceiveRecordMapper;
import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.mapper.MMoneyInvestWithdrawMapper;
import com.ruoyi.click.mapper.MUserMapper;
import com.ruoyi.click.mapper.UserGradeMapper;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.mapper.MAccountChangeRecordsMapper;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;

/**
 * 账变记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-16
 */
@Service
public class MAccountChangeRecordsServiceImpl implements IMAccountChangeRecordsService
{
    @Autowired
    private MAccountChangeRecordsMapper mAccountChangeRecordsMapper;
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private UserGradeMapper userGradeMapper;
    @Autowired
    private OrderReceiveRecordMapper orderReceiveRecordMapper;
    @Autowired
    private MMoneyInvestWithdrawMapper mMoneyInvestWithdrawMapper;

    /**
     * 查询账变记录
     *
     * @param uuid 账变记录主键
     * @return 账变记录
     */
    @Override
    public MAccountChangeRecords selectMAccountChangeRecordsByUuid(String uuid)
    {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsByUuid(uuid);
    }

    /**
     * 查询账变记录列表
     *
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录
     */
    @Override
    public List<MAccountChangeRecords> selectMAccountChangeRecordsList(MAccountChangeRecords mAccountChangeRecords)
    {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsList(mAccountChangeRecords);
    }

    /**
     * 查询账变记录列表-个人的奖励历史记录
     *
     * @param
     * @return 账变记录
     */
    @Override
    public List<Map<String,Object>> selectMAccountChangeForeByUser()
    {
        MAccountChangeRecords mAccountChangeRecords = new MAccountChangeRecords();
        mAccountChangeRecords.setUid(getUserId().toString());
        mAccountChangeRecords.setTransactionType(3); // 3:专用于标记订单利润
        return mAccountChangeRecordsMapper.selectMAccountChangeForeByUser(mAccountChangeRecords);
    }

    /**
     * 个人信息与近两日利润 前台《订单详细信息》页
     *
     * @param
     * @return 用户信息
     */
    @Override
    public Map<String,Object> getUserProfitInfo()
    {
        //用户、等级信息
        MUser mUser = mUserMapper.selectMUserByUid(getUserId());
        UserGrade userGrade = userGradeMapper.selectUserGradeBySortNum(mUser.getLevel());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        String strYesterday = formatter.format(localDate.minusDays(1));
        String strToday = formatter.format(localDate);
        String strTomorrow = formatter.format(localDate.plusDays(1));

        //今日已付款订单数量
        Map<String,Object> param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("processStatus", OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
        param.put("date1", strToday);
        param.put("date2", strTomorrow);
        long finishNum = orderReceiveRecordMapper.countNumByUserDate(param);

        //近2日订单利润 （前端的“昨天折扣”和“今天折扣”）
        param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("processStatus", OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
        //param.put("transactionType", 3); // 3:专用于标记订单利润，用于查账变表
        param.put("date1", strYesterday);
        param.put("date2", strToday);
        BigDecimal profitYesterday = orderReceiveRecordMapper.sumAmountByUserDate(param);
        param.put("date1", strToday);
        param.put("date2", strTomorrow);
        BigDecimal profitToday = orderReceiveRecordMapper.sumAmountByUserDate(param);
        //近2日提现数额 —— 无用
        /*param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("processStatus", OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
        param.put("date1", strYesterday);
        param.put("date2", strToday);
        BigDecimal withdrawYesterday = mMoneyInvestWithdrawMapper.sumAmountByUserDateType(param);
        param.put("date1", strToday);
        param.put("date2", strTomorrow);
        BigDecimal withdrawToday = mMoneyInvestWithdrawMapper.sumAmountByUserDateType(param);*/

        Map<String, Object> res = new HashMap<>();
        res.put("userBalance", mUser.getAccountBalance().setScale(2, RoundingMode.HALF_UP)); //用户余额
        res.put("userLevel", userGrade.getGradeName()); //用户等级
        res.put("orderNum", finishNum + "/" + userGrade.getBuyProdNum()); //已付款订单数量
        res.put("numYesterday", profitYesterday.setScale(2, RoundingMode.HALF_UP).toString()); //昨天折扣
        res.put("numToday", profitToday.setScale(2, RoundingMode.HALF_UP).toString()); //今天折扣
        res.put("withdrawalAddress", mUser.getWithdrawalAddress()); //提现地址  用于判断，页面不显示
        Date nowDate = DateUtils.getNowDate();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(nowDate);
        res.put("localTime", timeStr); //当前时间
        return res;
    }

    /**
     * 新增账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    @Override
    public int insertMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords)
    {
        mAccountChangeRecords.setCreateTime(DateUtils.getNowDate());
        return mAccountChangeRecordsMapper.insertMAccountChangeRecords(mAccountChangeRecords);
    }

    /**
     * 修改账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    @Override
    public int updateMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords)
    {
        mAccountChangeRecords.setUpdateTime(DateUtils.getNowDate());
        return mAccountChangeRecordsMapper.updateMAccountChangeRecords(mAccountChangeRecords);
    }

    /**
     * 批量删除账变记录
     *
     * @param uuids 需要删除的账变记录主键
     * @return 结果
     */
    @Override
    public int deleteMAccountChangeRecordsByUuids(String[] uuids)
    {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuids(uuids);
    }

    /**
     * 删除账变记录信息
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    @Override
    public int deleteMAccountChangeRecordsByUuid(String uuid)
    {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuid(uuid);
    }
}
