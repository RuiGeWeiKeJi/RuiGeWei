package com.huotu.scrm.service.repository.ReportInfo;

import com.huotu.scrm.service.model.customtrans;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ReportInfoTransRepository  {

    //extends JpaRepository<customtrans, Long>, JpaSpecificationExecutor<customtrans>
    /**
     * 根据业务员和日期  查询该业务员本月内的联系客户
     * @return
     */
    //@Query(value = "select * from rgwTransInfo",nativeQuery = true)
    //Page<customtrans> getAllByCUS012AndBRS003(Specification specification, Pageable pageable);

}
