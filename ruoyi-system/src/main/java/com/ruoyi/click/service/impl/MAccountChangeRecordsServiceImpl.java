package com.ruoyi.click.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class MAccountChangeRecordsServiceImpl implements IMAccountChangeRecordsService {
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
    public MAccountChangeRecords selectMAccountChangeRecordsByUuid(String uuid) {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsByUuid(uuid);
    }

    /**
     * 查询账变记录列表
     *
     * @param mAccountChangeRecords 账变记录
     * @return 账变记录
     */
    @Override
    public List<MAccountChangeRecords> selectMAccountChangeRecordsList(MAccountChangeRecords mAccountChangeRecords) {
        return mAccountChangeRecordsMapper.selectMAccountChangeRecordsList(mAccountChangeRecords);
    }

    /**
     * 查询账变记录列表-个人的奖励历史记录
     *
     * @param
     * @return 账变记录
     */
    @Override
    public List<Map<String, Object>> selectMAccountChangeForeByUser() {
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
    public Map<String, Object> getUserProfitInfo() {
        //用户、等级信息
        MUser mUser = mUserMapper.selectMUserByUid(getUserId());
        UserGrade userGrade = userGradeMapper.selectUserGradeBySortNum(mUser.getLevel());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        // 如果当前时间在 0 点 - 1:59:59 之间，把日期减一天
        if (now.getHour() < 2) {
            now = now.minusDays(1);
        }

        // 昨天 02:00:00
        String strYesterday = formatter.format(now.toLocalDate().minusDays(1).atTime(2, 0));
        // 今天 02:00:00
        String strToday = formatter.format(now.toLocalDate().atTime(2, 0));
        // 明天 02:00:00
        String strTomorrow = formatter.format(now.toLocalDate().plusDays(1).atTime(2, 0));

        //今日已付款订单数量
        Map<String, Object> param = new HashMap<>();
        param.put("userId", getUserId());
//        param.put("processStatus", OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
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

        // 处理昨天折扣（兼容null，格式化为20,25）
        String formattedYesterday;
        if (profitYesterday != null) {
            // 保留2位小数后替换小数点为逗号
            formattedYesterday = profitYesterday.setScale(2, RoundingMode.HALF_UP)
                    .toString()
                    .replace(".", ",");
        } else {
            // 若为null，返回空字符串或默认值（根据前端需求选择）
            formattedYesterday = ""; // 或 "0,00"
        }
        res.put("numYesterday", formattedYesterday);

        // 处理今天折扣（兼容null，格式化为20,25）
        String formattedToday;
        if (profitToday != null) {
            formattedToday = profitToday.setScale(2, RoundingMode.HALF_UP)
                    .toString()
                    .replace(".", ",");
        } else {
            formattedToday = ""; // 或 "0,00"
        }
        res.put("numToday", formattedToday);

        //res.put("numYesterday", profitYesterday.setScale(2, RoundingMode.HALF_UP).toString()); //昨天折扣
        //res.put("numToday", profitToday.setScale(2, RoundingMode.HALF_UP).toString()); //今天折扣
        res.put("withdrawalAddress", mUser.getWithdrawalAddress()); //提现地址  用于判断，页面不显示
        Date nowDate = DateUtils.getNowDate();  // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(nowDate);
        res.put("localTime", timeStr); //当前时间


        //查询最近一条订单
        OrderReceiveRecord orderReceiveRecord = orderReceiveRecordMapper.selectLastOrder(getUserId());
        if (orderReceiveRecord != null) {
            res.put("lastOrderStatus", orderReceiveRecord.getProcessStatus());
        }
        return res;
    }

    /**
     * 新增账变记录
     *
     * @param mAccountChangeRecords 账变记录
     * @return 结果
     */
    @Override
    public int insertMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords) {
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
    public int updateMAccountChangeRecords(MAccountChangeRecords mAccountChangeRecords) {
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
    public int deleteMAccountChangeRecordsByUuids(String[] uuids) {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuids(uuids);
    }

    /**
     * 删除账变记录信息
     *
     * @param uuid 账变记录主键
     * @return 结果
     */
    @Override
    public int deleteMAccountChangeRecordsByUuid(String uuid) {
        return mAccountChangeRecordsMapper.deleteMAccountChangeRecordsByUuid(uuid);
    }
}
