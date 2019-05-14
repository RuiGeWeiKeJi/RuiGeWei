package com.huotu.scrm.service.repository.baseset;

import com.huotu.scrm.service.entity.baseset.Reachflt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReachfltRepository extends JpaRepository<Reachflt,Long>, JpaSpecificationExecutor<Reachflt> {

    @Query(value = "select id,case when FLT001='级别' then 'CUS004' when FLT001='通话内容' then 'BRS006' else 'BRS0061' END FLT001,case when FLT002='等于' then '=' when FLT002='不等于' then '!=' when " +
            "FLT002='大于等于' then '>=' when FLT002='小于等于' then '<=' when FLT002='相似于' then 'like' end FLT002,FLT003,case when FLT004='或' then 'or' else 'and' end FLT004 from rgwflt order by FLT004",nativeQuery = true)
    List<Reachflt> getAllBy();

}
