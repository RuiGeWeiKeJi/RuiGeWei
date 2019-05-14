package com.huotu.scrm.service.service.ChartInfo;

import com.huotu.scrm.service.entity.Chart.ChartAvg;
import com.huotu.scrm.service.entity.baseset.Reachflt;
import com.huotu.scrm.service.model.avgbrs;
import com.huotu.scrm.service.model.everybrs;
import com.huotu.scrm.service.model.increasebrs;

import java.util.Date;
import java.util.List;

public interface ChartInfoService {

    /**
     * 获取系统日期
     * @return
     */
    Date getDate();

    /**
     * 查询业务员前两个月的所有电话数量
     * @param date
     * @return
     */
    List<avgbrs> getCount(Date date,String username);

    /**
     * 查询业务员前两个月打电话的天数
     * @param date
     * @return
     */
    Integer getCountForTwoMonthDays(Date date,String username);

    /**
     *查询业务员本月每天打电话的数量
     * @return
     */
    List<everybrs> getCountEveryDay(Date date,String username);

    /**
     * 获取本期级别增长或减少数量
     * @return
     */
    List<Object> getincreasebrs(String username);

    /**
     * 获取本期级别增长或减少数量
     * @return
     */
    List<Object> getincreasebrs();

    /**
     * 获取本人一段时间内的所有平均数
     * @param username
     * @param dateStart
     * @param dateEnd
     * @return
     */
    List<ChartAvg>  findAllByAVG001AndAVG003BetweenAAndAVG003(String username, Date dateStart, Date dateEnd,Integer days);

    List<Reachflt> getAllBy();

}
