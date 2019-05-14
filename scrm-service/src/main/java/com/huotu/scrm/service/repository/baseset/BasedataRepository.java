package com.huotu.scrm.service.repository.baseset;

import com.huotu.scrm.service.entity.baseset.BaseSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BasedataRepository extends JpaRepository<BaseSet,Long>, JpaSpecificationExecutor<BaseSet> {





}
