package com.huotu.scrm.service.repository.ChartInfo;

import com.huotu.scrm.service.entity.Chart.ChartAvg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ChartAvgRepository extends JpaRepository<ChartAvg,Long> , JpaSpecificationExecutor<ChartAvg> {


    /**
     * 获取本人一段时间内的所有平均数
     * @param username
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Query(value = "select id,AVG001,DATE_FORMAT(AVG003,'%m-%d') AVG003,AVG002,AVG004 from rgwavg where AVG001=?1 and AVG003 BETWEEN ?2 and ?3 union all select @rownum:=@rownum+1 ,?1,a.days,ifnull(b.coun,0) conn,10 from " +
            "(SELECT DISTINCT date_format(DATE_ADD(date_format(DATE_ADD(?3,INTERVAL ?4 day),'%Y-%m-%d'),INTERVAL id DAY),'%m-%d') days," +
            "date_format(DATE_ADD(date_format(DATE_ADD(?3,INTERVAL ?4 day),'%Y-%m-%d'),INTERVAL id DAY),'%Y%m%d') AS `TIME` FROM rgwcus " +
            "WHERE DATE_ADD(?3,INTERVAL id DAY) <= DATE_ADD(DATE_ADD(?3,INTERVAL 1 day),INTERVAL 3 month) ORDER BY time) a left join ( " +
            "select date_format(BRS003,'%Y%m%d') days,count(1) coun from rgwbrs a inner join rgwcus b on a.BRS002=b.CUS001 where  " +
            "date_format(BRS003,'%Y%m%d')>=date_format(DATE_ADD(?3,INTERVAL -90 day),'%Y%m%d')  and CUS012=?1 group by date_format(BRS003,'%Y%m%d') ) b " +
            "on a.TIME=b.days,(SELECT @rowNum:=0) c order by AVG003",nativeQuery = true)
    List<ChartAvg>  findAllByAVG001AndAVG003BetweenAAndAVG003(String username, Date dateStart,Date dateEnd,Integer days);


}
