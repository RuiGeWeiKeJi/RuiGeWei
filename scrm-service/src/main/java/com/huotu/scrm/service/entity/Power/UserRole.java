package com.huotu.scrm.service.entity.Power;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "rgwrol")
@Description("人员所属角色")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 人员编号
     */
    @Column(name = "ROL001")
    private String userId;

    /**
     * 人员姓名
     */
    @Column(name = "ROL002")
    private String ROL002;

    /**
     * 角色
     */
    @Column(name = "ROL003")
    private String ROL003;

    /**
     * 编号
     */
    @Column(name = "ROL004")
    private String oddNum;

}
