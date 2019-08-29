package com.huotu.scrm.service.service.cutom;

import com.huotu.scrm.service.entity.custom.Custom;
import com.huotu.scrm.service.entity.custom.CustomQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CustemInfoService {

    /**
     * 根据条件查询客户资料
     * @param specification
     * @return
     */
    Page<Custom> findAllByWhere(Specification specification  , Pageable pageable) ;

    /**
     * 根据条件查询
     * @param customQuery
     * @return
     */
    List<Custom> queryAllInfo(CustomQuery customQuery);

    /**
     * 查询超期客户
     * @return
     */
    List<Integer> findBy();

    /**
     * 依据客户编号查询该客户所有联系人
     * @return
     */
    List<String> findAllBy();

    /**
     * 分页获取数据列表
     * @param strWhere
     * @param pageable
     * @return
     */
    Page<Custom> findAllByString(String strWhere, Pageable pageable);

    List<String> findByCUS001(Specification specification);

}
