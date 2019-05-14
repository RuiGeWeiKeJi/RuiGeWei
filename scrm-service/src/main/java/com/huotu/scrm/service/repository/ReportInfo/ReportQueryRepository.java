package com.huotu.scrm.service.repository.ReportInfo;

import com.huotu.scrm.service.entity.baseset.QueryCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportQueryRepository extends JpaRepository<QueryCondition,Long>, JpaSpecificationExecutor<QueryCondition> {


}
