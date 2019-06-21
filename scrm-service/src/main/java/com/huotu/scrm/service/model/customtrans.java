package com.huotu.scrm.service.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
//@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class customtrans implements Serializable {

    public customtrans(){

    }

    /**
     * 客户编号:程序控制生成
     */
    private String CUS001;


    /**
     * 客户名称
     */
    private String CUS002;


    /**
     * 级别
     */
    private String CUS004;

    /**
     * 行业
     */
    private String CUS010;

    /**
     * 业务员
     */
    private String CUS012;

    /**
     * 联系单编号
     */
    private String BRS001;

    /**
     * 沟通方式
     */
    private String BRS005;

    /**
     * 沟通内容
     */
    private String BRS006;

    /**
     * 联系人
     */
    private String BRS007;


    /**
     * 联系人
     */
    private DateTime BRS003;


    /**
     * 联系人
     */
    private String BRS008;

}
