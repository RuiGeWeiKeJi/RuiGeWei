package com.huotu.scrm.service.repository.customSrs;

import com.huotu.scrm.service.entity.customSRS.CustomSRS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomSrsRepository extends JpaRepository<CustomSRS, Long>, JpaSpecificationExecutor<CustomSRS> {


    /**
     * 获取最大编号
     * @return
     */
    @Query(value = "select SRS001 from rgwsrs where id=(select max(id) from rgwsrs)",nativeQuery = true)
    String findByCode();

    /**
     * 分页获取数据
     * @param customId
     * @return
     */
    Page<CustomSRS> findAllByCustomId(String customId, Pageable pageable);



}
