package com.huotu.scrm.service.repository.ChartInfo;

import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.model.avgbrs;
import com.huotu.scrm.service.model.everybrs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChartInfoRepository extends JpaRepository<CustomBRS, Long>, JpaSpecificationExecutor<CustomBRS> {

    /**
     * 查询业务员前两个月的所有电话数量
     * @param date
     * @return
     */
    @Query(value = "select b.CUS012 cus,round(count(1)/c.coun) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 inner join (select CUS012,count(1) coun from (select DISTINCT " +
            "CUS012,date_format(BRS003,'%Y%m%d') from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=date_format(DATE_ADD(?1,INTERVAL -90 day),'%Y%m%d') and " +
            "date_format(BRS003,'%Y%m%d')<=date_format(DATE_ADD(?1,INTERVAL -60 day),'%Y%m%d') and CUS012=?2 ) a  GROUP BY CUS012) c on b.CUS012=c.CUS012 where date_format(BRS003,'%Y%m%d')>=" +
            "date_format(DATE_ADD(?1,INTERVAL -30  day),'%Y%m%d') and b.CUS012=?2 group by b.CUS012",
            nativeQuery = true)
    List<avgbrs> getCount(Date date,String username);

    /**
     * 查询业务员前两个月打电话的天数
     * @param date
     * @return
     */
    @Query(value = "select count(1) coun from (select distinct date_format(BRS003,'%Y%m%d') from rgwbrs  a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m')>=date_format(DATE_ADD(?1,INTERVAL -2 month),'%Y%m') and " +
            "date_format(BRS003,'%Y%m')<=date_format(DATE_ADD(?1,INTERVAL -1 month),'%Y%m') and CUS012=?2) a",nativeQuery = true)
    Integer getCountForTwoMonthDays(Date date,String username);

    /**
     *查询业务员本月每天打电话的数量
     * @return
     */
    @Query(value = "select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where date_format(BRS003,'%Y%m%d')>=date_format(DATE_ADD(?1,INTERVAL -30 day),'%Y%m%d')  and CUS012=?2" +
            " group by date_format(BRS003,'%Y%m%d')",nativeQuery = true)
    List<everybrs> getCountEveryDay(Date date,String username);

    /**
     * 获取系统日期
     * @return
     */
    @Query(value = "select NOW() as t",nativeQuery = true)
    Date getDate();

}
