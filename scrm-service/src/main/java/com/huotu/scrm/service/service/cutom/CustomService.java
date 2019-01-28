package com.huotu.scrm.service.service.cutom;

import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.custom.Custom;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.List;

public interface CustomService {
    /**
     * 查询所有客户资料
     * @param pageable
     * @return
     */
    Page<Custom> findAllBy(Pageable pageable);

    /**
     * 插入客户资料
     * @param custom
     * @return
     */
    Custom insertCustom(Custom custom);

    /**
     * 获取客户最大编号
     * @return
     */
    String findOneByCode();

    /**
     * 根据输入名称模糊查询
     * @return
     */
    List<Custom> findName(Specification specification);

    /**
     * 是否存在
     * @param customId
     * @return
     */
    boolean existsCustomInfo(String customId);

    /**
     * 是否存在同名称的客户
     * @param customName
     * @param customId
     * @return
     */
    boolean existsCustomNameAndId(String customName,String customId);

}
