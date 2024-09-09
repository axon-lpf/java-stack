package com.axon.java.stack;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

public class BigdecimaTest {

    public static void main(String[] args) {


        // 实际收款:   当天的实收 - 隔天退款
        BigDecimal totalActualAmount = NumberUtil.sub("207183.00", "8533.00");
        //实际收款金额减去商家优惠
        totalActualAmount = NumberUtil.sub(totalActualAmount, 0);
        System.out.println(totalActualAmount.multiply(new BigDecimal(100)));


    }
}
