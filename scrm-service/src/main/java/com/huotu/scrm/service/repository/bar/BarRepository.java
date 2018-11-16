package com.huotu.scrm.service.repository.bar;

import com.huotu.scrm.service.entity.custom.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BarRepository extends JpaRepository<Custom, Long>, JpaSpecificationExecutor<Custom> {

    /**
     * 查询所有行业
     * @return
     */
    @Query(value = "select distinct CUS010 from rgwcus",nativeQuery = true)
    List<String> findAllindustry();

    /**
     * 查询所有业务员
     * @return
     */
    @Query(value = "select distinct CUS012 from rgwcus",nativeQuery = true)
    List<String> dindAllsalesman();

}
