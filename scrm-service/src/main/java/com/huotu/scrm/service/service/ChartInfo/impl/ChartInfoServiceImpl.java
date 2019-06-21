package com.huotu.scrm.service.service.ChartInfo.impl;

import com.huotu.scrm.service.entity.Chart.ChartAvg;
import com.huotu.scrm.service.entity.baseset.Reachflt;
import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.model.avgbrs;
import com.huotu.scrm.service.model.everybrs;
import com.huotu.scrm.service.repository.ChartInfo.ChartAvgRepository;
import com.huotu.scrm.service.repository.ChartInfo.ChartInfoRepository;
import com.huotu.scrm.service.repository.baseset.ReachfltRepository;
import com.huotu.scrm.service.service.ChartInfo.ChartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChartInfoServiceImpl implements ChartInfoService {

    @Autowired
    ChartInfoRepository chartInfoRepository;

    @Autowired
    ChartAvgRepository chartAvgRepository;

    @Autowired
    ReachfltRepository reachfltRepository;


    /**
     * 获取系统日期
     * @return
     */
    @Override
    public Date getDate() {
        return chartInfoRepository.getDate();
    }

    /**
     * 查询业务员前两个月的所有电话数量
     * @param date
     * @return
     */
    @Override
    public List<avgbrs> getCount(Date date,String username) {
        return chartInfoRepository.getCount(date,username);
    }

    /**
     * 查询业务员前两个月打电话的天数
     * @param date
     * @return
     */
    @Override
    public Integer getCountForTwoMonthDays(Date date,String username) {
        return chartInfoRepository.getCountForTwoMonthDays(date,username);
    }

    /**
     * 查询业务员本月每天打电话的数量
     * @param date
     * @return
     */
    @Override
    public List<everybrs> getCountEveryDay(Date date,String username) {
        return chartInfoRepository.getCountEveryDay(date,username);
    }

    /**
     * 获取本期级别增长或减少数量
     * @return
     */
    @Override
    public List<Object> getincreasebrs(String username) {
        return chartInfoRepository. getincreasebrs(username);
    }

    @Override
    public List<Object> getincreasebrs() {
        return chartInfoRepository.getincreasebrs();
    }

    /**
     * 获取某人某段时间内的所有数据
     * @param username
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Override
    public List<ChartAvg> findAllByAVG001AndAVG003BetweenAAndAVG003(String username, Date dateStart, Date dateEnd,Integer days) {
        return chartAvgRepository.findAllByAVG001AndAVG003BetweenAAndAVG003(username,dateStart,dateEnd,days);
    }

    @Override
    public List<Reachflt> getAllBy() {
        return reachfltRepository.getAllBy();
    }


}
