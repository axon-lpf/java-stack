package com.axon.java.stack;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author shanglu
 * @description 用来记录每周中第一天和最后一天的日期
 * @date 2021-12-09 17:52 pm
 */
@Data
public class WeekDateVO {


    /**
     * 交易金额
     */
    private BigDecimal actualAmount;

    /**
     * 开始日期
     */
    private LocalDate startDate;

    /**
     * 结束日期
     */
    private LocalDate endDate;

    public WeekDateVO() {

    }


    public WeekDateVO(List<LocalDate> localDates) {
        this.startDate = localDates.get(0);
        this.endDate = localDates.get(localDates.size() - 1);
    }

}
