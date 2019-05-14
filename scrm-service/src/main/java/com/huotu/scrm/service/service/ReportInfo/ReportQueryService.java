package com.huotu.scrm.service.service.ReportInfo;

import com.huotu.scrm.service.entity.baseset.QueryCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ReportQueryService {

    /**
     * 保存数据
     * @param queryCondition
     */
    void saveAndEdit(QueryCondition queryCondition);

    /**
     * 查询所有数据
     * @param pageable
     * @return
     */
    Page<QueryCondition> findAll(Pageable pageable);

    /**
     * 删除数据
     * @param queryCondition
     */
    void deleteQquery(QueryCondition queryCondition);

    /**
     * 是否存在
     * @param flt001
     * @param flt003
     * @return
     */
    QueryCondition getByFLT001AndFLT003(Specification specification);

}
