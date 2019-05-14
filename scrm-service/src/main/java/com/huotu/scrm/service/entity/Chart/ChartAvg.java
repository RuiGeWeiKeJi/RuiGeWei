package com.huotu.scrm.service.entity.Chart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "rgwavg")
@Description("平均数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartAvg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "AVG001",length = 225)
    private String AVG001;

    /**
     * 平均数
     */
    @Column(name = "AVG002",length = 11)
    private Double AVG002;

    /**
     * 日期
     */
    @Column(name = "AVG003")
    private String AVG003;

    /**
     * 30/60/90天平均数
     */
    @Column(name = "AVG004",length = 11)
    private Integer AVG004;

}
