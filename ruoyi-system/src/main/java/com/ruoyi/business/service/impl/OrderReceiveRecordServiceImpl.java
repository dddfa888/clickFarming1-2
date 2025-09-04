package com.ruoyi.business.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.ruoyi.business.domain.MRewardRecord;
import com.ruoyi.business.domain.MUserOrderSet;
import com.ruoyi.business.domain.ProductManage;
import com.ruoyi.business.mapper.MRewardRecordMapper;
import com.ruoyi.business.mapper.MUserOrderSetMapper;
import com.ruoyi.business.mapper.ProductManageMapper;
import com.ruoyi.click.domain.vo.OrderReceiveRecordVo;
import com.ruoyi.click.service.IMAccountChangeRecordsService;
import com.ruoyi.common.core.domain.entity.MUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DecimalUtil;
import com.ruoyi.click.domain.MAccountChangeRecords;
import com.ruoyi.click.domain.UserGrade;
import com.ruoyi.click.mapper.MAccountChangeRecordsMapper;
import com.ruoyi.click.mapper.MUserMapper;
import com.ruoyi.click.mapper.UserGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.OrderReceiveRecordMapper;
import com.ruoyi.business.domain.OrderReceiveRecord;
import com.ruoyi.business.service.IOrderReceiveRecordService;

import static com.ruoyi.common.utils.SecurityUtils.getUserId;

/**
 * 订单接收记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class OrderReceiveRecordServiceImpl implements IOrderReceiveRecordService
{
    @Autowired
    private OrderReceiveRecordMapper orderReceiveRecordMapper;
    @Autowired
    private ProductManageMapper productManageMapper;
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private UserGradeMapper userGradeMapper;
    @Autowired
    private MAccountChangeRecordsMapper mAccountChangeRecordsMapper;
    @Autowired
    private MRewardRecordMapper mRewardRecordMapper;
    @Autowired
    private MUserOrderSetMapper mUserOrderSetMapper;
    @Autowired
    private IMAccountChangeRecordsService mAccountChangeRecordsService;
    // 声明为类的静态成员（在方法外）
    private static final Random RANDOM = new Random();

    /**
     * 查询订单接收记录
     *
     * @param id 订单接收记录主键
     * @return 订单接收记录
     */
    @Override
    public OrderReceiveRecord selectOrderReceiveRecordById(Long id)
    {
        return orderReceiveRecordMapper.selectOrderReceiveRecordById(id);
    }

    /**
     * 查询订单接收记录列表
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录
     */
    @Override
    public List<OrderReceiveRecord> selectOrderReceiveRecordList(OrderReceiveRecord orderReceiveRecord)
    {
        return orderReceiveRecordMapper.selectOrderListOrderDescId(orderReceiveRecord);
    }

    /**
     * 倒序查询一个用户订单接收记录列表
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 订单接收记录
     */
    @Override
    public List<OrderReceiveRecordVo> selectOrderListByUser(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setUserId(getUserId());
        return orderReceiveRecordMapper.selectListOrderDescVo(orderReceiveRecord);
    }

    /**
     * 新增订单接收记录
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    @Override
    public int insertOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setCreateTime(DateUtils.getNowDate());
        return orderReceiveRecordMapper.insertOrderReceiveRecord(orderReceiveRecord);
    }

    /**
     * 修改订单接收记录
     *
     * @param orderReceiveRecord 订单接收记录
     * @return 结果
     */
    @Override
    public int updateOrderReceiveRecord(OrderReceiveRecord orderReceiveRecord)
    {
        orderReceiveRecord.setUpdateTime(DateUtils.getNowDate());
        return orderReceiveRecordMapper.updateOrderReceiveRecord(orderReceiveRecord);
    }

    /**
     * 批量删除订单接收记录
     *
     * @param ids 需要删除的订单接收记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderReceiveRecordByIds(Long[] ids)
    {
        return orderReceiveRecordMapper.deleteOrderReceiveRecordByIds(ids);
    }

    /**
     * 删除订单接收记录信息
     *
     * @param id 订单接收记录主键
     * @return 结果
     */
    @Override
    public int deleteOrderReceiveRecordById(Long id)
    {
        return orderReceiveRecordMapper.deleteOrderReceiveRecordById(id);
    }

    /**
     * 统计一个用户当日的订单数量
     *
     * @param
     * @return 结果
     */
    @Override
    public int countNumByUserDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        Map<String, Object> param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("date1", formatter.format(today));
        param.put("date2", formatter.format(today.plusDays(1)));
        return orderReceiveRecordMapper.countNumByUserDate(param);
    }


    /**
     * 前台用户点击后添加订单
     * 为了数据入库后返回id，orderReceiveRecord由Controller传过来而不是本方法内新建
     *
     * @return 新增订单数量
     */
    @Override
    public Map<String, Object> insertOrderByUser(OrderReceiveRecord orderReceiveRecord) {
        Map<String, Object> map = new HashMap<>();
        MUser mUser = mUserMapper.selectMUserByUid(getUserId());
        OrderReceiveRecord orderParam = new OrderReceiveRecord();
        orderParam.setUserId(mUser.getUid());
        orderParam.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_WAIT);
        long unfinishedCount = orderReceiveRecordMapper.countNum(orderParam);
        if (unfinishedCount > 0) {
            throw new ServiceException("有订单未完成，请先付款");//user
        }
        OrderReceiveRecord order = orderReceiveRecordMapper.selectLastOrder(mUser.getUid());
        if(order != null) {
            Date createTime = order.getCreateTime();
            Date nowDate = DateUtils.getNowDate();
            // 计算时间差（秒）
            long diff = (nowDate.getTime() - createTime.getTime()) / 1000;

            if (diff < 8) {
                throw new ServiceException("正在分发");//user
            }
        }

        UserGrade userGrade = userGradeMapper.selectUserGradeBySortNum(mUser.getLevel());
        if (userGrade == null)
            throw new ServiceException("用户等级不存在");//user
        //若余额小于等级内设置的最低余额，则给出提示，下单失败
        if (mUser.getAccountBalance().compareTo(userGrade.getMinBalance()) < 0) {
            String nameCn = "";
            //会员等级名称越南语转为中文
            Integer gradeName = userGrade.getId().intValue();
            switch (gradeName) {
                case 1:
                    nameCn = "白银";
                    break;
                case 2:
                    nameCn = "黄金";
                    break;
                case 3:
                    nameCn = "白金";
                    break;
                case 4:
                    nameCn = "钻石";
                    break;
                default:
                    nameCn = "白银";
            }
            map.put("name", "等级配置");
            map.put("level", nameCn);
            map.put("value", userGrade.getMinBalance());
            return map;
        }

        //int todayCount = countNumByUserDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        int numTarget = userGrade.getBuyProdNum();
        String strToday = formatter.format(localDate);
        String strTomorrow = formatter.format(localDate.plusDays(1));
        Map<String, Object> param = new HashMap<>();
        param.put("userId", getUserId());
        param.put("date1", strToday);
        param.put("date2", strTomorrow);
        long finishNum = orderReceiveRecordMapper.countNumByUserDate(param);
        int todayCount = (int) finishNum;
        if (finishNum >= numTarget)
            throw new ServiceException("您已完成今天的申请");//user
        //原系统中当日下单次数达到设置值时提示如下：
        //  您已完成今天的申请
        //  You have completed your order today
        //  Bạn đã hoàn thành hết đơn hôm nay

        orderReceiveRecord.setUserId(mUser.getUid());
        orderReceiveRecord.setUserName(mUser.getLoginAccount());

        //无论是否连单，至少需保存一个订单
        setValueSaveProdList(orderReceiveRecord, mUser, userGrade, numTarget, ++todayCount);
        int saveOrderNum = 1;

        //检查用户表设置的值，判断是否连单，若multiOrderNum，说明需要生成多个订单
        Integer multiOrderNum = mUser.getMultiOrderNum();
        Long firstOrderId = null;
        if (multiOrderNum != null && multiOrderNum > 1) {
            firstOrderId = orderReceiveRecord.getId();
            for (int i = 1; i < multiOrderNum; i++) { //上面已经保存1单，所以此处i初始值为1，而不是0
                setValueSaveProdList(orderReceiveRecord, mUser, userGrade, numTarget, ++todayCount);
            }
            saveOrderNum = multiOrderNum;
            //第1个订单的id返回到前端
            orderReceiveRecord.setId(firstOrderId);
        }
        map.put("orderId", firstOrderId);
        mUserMapper.increaseBrushNumber(mUser.getUid(), saveOrderNum);
        return map;
    }

    /**
     * 设置一个订单的数据并保存入数据库
     */
    public void setValueSaveProdList(OrderReceiveRecord orderReceiveRecord, MUser mUser, UserGrade userGrade, int numTarget, int todayCount) {
        //查《订单设置》表，如果提前设置了某一单的限制，就根据限制条件查询产品，没设置就按默认情况查询
        MUserOrderSet paramOrderSet = new MUserOrderSet();
        paramOrderSet.setUserId(mUser.getUid());
        paramOrderSet.setOrderNum(todayCount);
        List<MUserOrderSet> orderSetList = mUserOrderSetMapper.selectMUserOrderSetList(paramOrderSet);

        ProductManage product = null;
        if (orderSetList != null && orderSetList.size() > 0) {
//            product = setOrderProdLimit(orderReceiveRecord, orderSetList.get(0),userGrade);
        } else {
            product = setOrderProdNormal(orderReceiveRecord, mUser ,userGrade);
        }

        orderReceiveRecord.setProductId(product.getId());
        orderReceiveRecord.setProductName(product.getProductName());
        orderReceiveRecord.setProductImageUrl(product.getImageUrl());
        orderReceiveRecord.setUnitPrice(product.getPrice());

        orderReceiveRecord.setTotalAmount(DecimalUtil.multiple(product.getPrice(), orderReceiveRecord.getNumber()));
        orderReceiveRecord.setProfit(orderReceiveRecord.getProfit());
        orderReceiveRecord.setRefundAmount(DecimalUtil.add(orderReceiveRecord.getTotalAmount(), orderReceiveRecord.getProfit()));
        orderReceiveRecord.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_WAIT);
        orderReceiveRecord.setNumTarget(numTarget);
        orderReceiveRecord.setNumSeq(todayCount);
        orderReceiveRecord.setMultiType(OrderReceiveRecord.MULTI_TYPE_NO);
        orderReceiveRecord.setFreezeStatus(OrderReceiveRecord.FREEZE_STATUS_NO);
        orderReceiveRecord.setCreateTime(DateUtils.getNowDate());
        orderReceiveRecordMapper.insertOrderReceiveRecord(orderReceiveRecord);
    }

    public ProductManage setOrderProdNormal(OrderReceiveRecord orderReceiveRecord, MUser mUser, UserGrade userGrade) {

        // 1. 按用户余额筛选可支付产品
        Map<String, Object> paramIds = new HashMap<>();
        paramIds.put("price_Le", mUser.getAccountBalance());
        List<Long> idList = productManageMapper.getIdList(paramIds);
        if (idList == null || idList.isEmpty()) {
            throw new ServiceException("未查到可支付产品");
        }

        // 随机选择产品
        int prodIndex = (int) (Math.random() * idList.size());
        ProductManage product = productManageMapper.selectProductManageById(idList.get(prodIndex));

        // 2. 计算用户等级对应的单价区间
//        BigDecimal minPrice = calculateMinPrice(userGrade);
//        BigDecimal maxPrice = calculateMaxPrice(userGrade);
        BigDecimal  minProfit = userGrade.getMinProfit();
        BigDecimal  maxProfit = userGrade.getMaxProfit();
        BigDecimal accountBalance = mUser.getAccountBalance();
        if (userGrade.getSortNum() == 1){
            if (accountBalance.compareTo(BigDecimal.valueOf(500))>=0){
                minProfit = userGrade.getMinProfit2();
                maxProfit = userGrade.getMaxProfit2();
            }
        }
        // 计算用户等级对应的单价区间
        BigDecimal minPrice = minProfit.divide(BigDecimal.valueOf(userGrade.getBuyProdNum()), 4, RoundingMode.HALF_UP);
        BigDecimal maxPrice = maxProfit.divide(BigDecimal.valueOf(userGrade.getBuyProdNum()), 4, RoundingMode.HALF_UP);


        //获取当产品价格等于用户余额时的利润比
        BigDecimal balanceRatio = maxPrice.divide(accountBalance, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        if (balanceRatio.compareTo(userGrade.getMinBonus())<0){
            balanceRatio = userGrade.getMinBonus();
        }
        if (balanceRatio.compareTo(userGrade.getMaxBonus())>=0){
            userGrade.setMaxBonus(balanceRatio.add(BigDecimal.valueOf(0.1)));
        }
        //在获取到的利润与最大利润比中取随机利润比
        double rnd = ThreadLocalRandom.current().nextDouble(
                balanceRatio.doubleValue(), userGrade.getMaxBonus().doubleValue());
        BigDecimal finalRatio = BigDecimal.valueOf(rnd).setScale(4, RoundingMode.HALF_UP);
        BigDecimal ratioDecimal = finalRatio.divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP);

        // 反推价格区间
        BigDecimal prodMinPrice = minPrice.divide(ratioDecimal, 2, RoundingMode.HALF_UP);
        BigDecimal prodMaxPrice = maxPrice.divide(ratioDecimal, 2, RoundingMode.HALF_UP);

        // 5. 随机生成单价
        double priceRnd = ThreadLocalRandom.current().nextDouble(prodMinPrice.doubleValue(), prodMaxPrice.doubleValue());
        BigDecimal finalPrice = BigDecimal.valueOf(priceRnd).setScale(2, RoundingMode.HALF_UP);
        System.out.println(" 用户利润比= " + balanceRatio);
        System.out.println(" 利润比= " + ratioDecimal);
        // 6. 计算利润
        BigDecimal profit = finalPrice.multiply(ratioDecimal).setScale(2, RoundingMode.HALF_UP);

        BigDecimal productPrice = product.getPrice();
        product.setPrice(finalPrice);

        orderReceiveRecord.setNumber(1);
        orderReceiveRecord.setProfit(profit);
        orderReceiveRecord.setTotalAmount(finalPrice);
        return product;
    }

    private int calculateValidQuantity(
            BigDecimal productPrice,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int minNum,
            int maxNum
    ) {
        Random rand = new Random();
        int quantity;

        do {
            quantity = randomMinMax(minNum, maxNum);
            BigDecimal totalPrice = productPrice.multiply(BigDecimal.valueOf(quantity));

            // 验证总价是否在区间内
            if (totalPrice.compareTo(minPrice) >= 0 &&
                    totalPrice.compareTo(maxPrice) <= 0) {
                return quantity;
            }
        } while (true);
    }


    /**
     * 计算用户等级对应的最低单价
     */
    private BigDecimal calculateMinPrice(UserGrade userGrade) {
        return userGrade.getMinProfit().divide(
                userGrade.getMaxBonus().divide(new BigDecimal("100")).multiply(BigDecimal.valueOf(userGrade.getBuyProdNum())),
                2, RoundingMode.HALF_UP
        );
    }

    /**
     * 计算用户等级对应的最高单价
     */
    private BigDecimal calculateMaxPrice(UserGrade userGrade) {
        return userGrade.getMaxProfit().divide(
                userGrade.getMinBonus().divide(new BigDecimal("100")).multiply(BigDecimal.valueOf(userGrade.getBuyProdNum())),
                2, RoundingMode.HALF_UP
        );
    }

    //    public static void setOrderProdNormal2(BigDecimal price, BigDecimal money){
//
//        int prodNum = money.divide(price, 0, RoundingMode.DOWN).intValue();
//        // 如果上面计算的prodNum是1，产品数量直接设为1。否则，假设prodNum（用户可支付范围内的最大数量）是10，生成随机数取5-10之间的整数作为本次订单实际产品数量。
//        if(prodNum>1){
//            Double min=prodNum*(0.8);
//            prodNum = randomMinMax(min.intValue(),prodNum);
//        }
//        System.out.println(prodNum*price.doubleValue());
//    }
//
//    public static void main(String[] args) {
//        setOrderProdNormal2(new BigDecimal(123),new BigDecimal(500));
//    }
    // 生成包含 min 和 max 的随机数
// 生成包含min和max的随机数
    int randomMinMax(int min, int max) {
        Random rand = new Random();
        if (min < 1) min = 1;
        if (max < 2) max = 2;
        if (min > max) return 1;
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * 从数据库中随机查询一个产品，默认只查询价格小于或等于用户余额的
     */
    public ProductManage setOrderProdLimit(OrderReceiveRecord orderReceiveRecord, MUserOrderSet orderSet,UserGrade userGrade) {
        BigDecimal minNum = orderSet.getMinNum();
        BigDecimal maxNum = orderSet.getMaxNum();
        BigDecimal maxHalf = maxNum.divide(new BigDecimal(2));
        Map<String, Object> paramIds = new HashMap<>();
        paramIds.put("min", minNum);
        paramIds.put("max", maxNum);
        paramIds.put("max_half", maxHalf);
        List<Long> idList = productManageMapper.getIdListByOrderSet(paramIds);
        if (idList == null || idList.isEmpty())
            throw new ServiceException("未查到产品信息");//user

        int prodIndex = (int) Math.floor(Math.random() * idList.size());
        ProductManage product = productManageMapper.selectProductManageById(idList.get(prodIndex));
        BigDecimal price = product.getPrice();

        // 计算合适的产品数量，使总额在min到max之间
        int prodNum = 1; //默认数量1，适合产品单价 > half的情况

        if (price.compareTo(maxHalf) <= 0) {
            long min = Math.round(Math.ceil(minNum.divide(price, 2, RoundingMode.HALF_UP).doubleValue()));
            long max = Math.round(Math.floor(maxNum.divide(price, 2, RoundingMode.HALF_UP).doubleValue()));
            prodNum = (int) (Math.round(Math.floor(Math.random() * (max - min))) + min);
        }
        orderReceiveRecord.setNumber(prodNum);
        return product;
    }

    /**
     * 从数据库中随机查询一个产品，只查询价格小于或等于用户余额的
     * @return
     *//*
    public ProductManage getProductRand(MUser mUser){
        Map<String,Object> paramIds = new HashMap<>();
        paramIds.put("price_Le", mUser.getAccountBalance());
        List<Long> idList = productManageMapper.getIdList(paramIds);
        if(idList==null || idList.isEmpty())
            throw new ServiceException("未查到产品信息");//user

        int prodIndex = (int) Math.floor(Math.random() * idList.size());
        return productManageMapper.selectProductManageById(idList.get(prodIndex));
    }*/

    /**
     * 计算利润
     *
     * @return
     */
    public BigDecimal calcProfit(UserGrade userGrade, BigDecimal totalAmount) {

        //最大值与最小值之差
        BigDecimal range = DecimalUtil.subtract(userGrade.getMaxBonus(), userGrade.getMinBonus());
        //最大值与最小值之差 * 随机数
        BigDecimal num = DecimalUtil.multiply(range, DecimalUtil.toBigDecimal(Math.random()));
        //最大值与最小值之差 * 随机数 + 最小值 = 最小值与最大值之间的随机数（利率）。原值用%表示，所以最后除以100后是实际利率值。
        BigDecimal ratio = num.add(userGrade.getMinBonus()).divide(DecimalUtil.toBigDecimal(100));
        //利率 * 订单总金额 = 利润  最终保留2位小数
        return DecimalUtil.multiply(ratio, totalAmount).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 支付订单
     *
     * @param orderId
     */
    @Override
    public int payOrder(Long orderId) {
        OrderReceiveRecord orderReceiveRecord = orderReceiveRecordMapper.selectOrderReceiveRecordById(orderId);
        if (orderReceiveRecord == null)
            throw new ServiceException("订单不存在");//user
        if (OrderReceiveRecord.PROCESS_STATUS_SUCCESS.equals(orderReceiveRecord.getProcessStatus()))
            throw new ServiceException("订单已支付，不可重复支付");//user

        MUser mUser = mUserMapper.selectMUserByUid(orderReceiveRecord.getUserId());

        //用户余额小于订单总金额时，不可支付，需要先充值。
        if (mUser.getAccountBalance().compareTo(orderReceiveRecord.getTotalAmount()) < 0)
            throw new ServiceException("您的帐户不足。请继续充值！");//user

        BigDecimal balanceBefore = mUser.getAccountBalance(); //记录变化前余额
        BigDecimal balanceChange = orderReceiveRecord.getProfit(); //新增余额
        BigDecimal balanceAfter = DecimalUtil.add(balanceBefore, balanceChange);

        Date nowDate = DateUtils.getNowDate();
        //更新用户余额
        mUser.setAccountBalance(balanceAfter);
        mUser.setUpdateTime(nowDate);
        mUserMapper.updateMUser(mUser);

        //记录余额变化详情
        MAccountChangeRecords changeRecords = new MAccountChangeRecords();
        changeRecords.setAmount(balanceChange);
        changeRecords.setType(0); //0收入 1支出
        changeRecords.setAccountForward(balanceBefore);
        changeRecords.setAccountBack(balanceAfter);
        changeRecords.setUid(String.valueOf(mUser.getUid()));
        changeRecords.setDescription(mUser.getLoginAccount() + "订单奖励");
        changeRecords.setTransactionType(3); // 3:专用于标记订单利润
        changeRecords.setCreateTime(nowDate);
        changeRecords.setRelatedId(orderId.toString());
        mAccountChangeRecordsMapper.insertMAccountChangeRecords(changeRecords);

        //添加奖励记录
        MRewardRecord mRewardRecord = new MRewardRecord();
        mRewardRecord.setUserId(mUser.getUid());
        mRewardRecord.setUserName(mUser.getLoginAccount());
        mRewardRecord.setRewardTime(nowDate);
        mRewardRecord.setRewardAmount(balanceChange);
        mRewardRecord.setBalanceBefore(balanceBefore);
        mRewardRecord.setBalanceAfter(balanceAfter);
        mRewardRecord.setDescription("订单奖励");
        mRewardRecord.setCreateTime(nowDate);
        mRewardRecordMapper.insertMRewardRecord(mRewardRecord);

        //更新值  支付状态：完成
        orderReceiveRecord.setProcessStatus(OrderReceiveRecord.PROCESS_STATUS_SUCCESS);
        orderReceiveRecord.setUpdateTime(nowDate);
        return orderReceiveRecordMapper.updateOrderReceiveRecord(orderReceiveRecord);
    }
}
