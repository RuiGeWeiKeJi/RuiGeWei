package com.huotu.scrm.service.entity.baseset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "rgwbas")
@Description("基础设置")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 类别
     */
    @Column(name = "BAS001")
    private String BAS001;

    /**
     * 值
     */
    @Column(name = "BAS002")
    private String BAS002;

}
