
package com.huotu.scrm.service.repository.custom;

import com.huotu.scrm.service.entity.custom.Custom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



/**
 * Created by montage on 2017/7/12.
 */

@Repository
public interface CustomRepository extends JpaRepository<Custom, Long>, JpaSpecificationExecutor<Custom> {

    /**
     * 分页查询客户资料
     *
     * @param pageable
     * @return
     */
    Page<Custom> findAllBy(Pageable pageable);

    /**
     *获取最大ID
     * @return
     */
    @Query(value = "select MAX(CUS001) CUS001 from rgwcus ",nativeQuery=true)
    String findOneByCode();

    /**
     * 是否存在
     * @param customName
     * @return
     */
    @Query(value = "select CUS001 from rgwcus t where t.CUS002=?1 ",nativeQuery = true)
    String findByCode(String customName);

    /**
     * 是否存在该名称的客户
     * @param customName
     * @return
     */
    @Query(value = "select count(1) from rgwcus t where t.CUS002=?1 and t.CUS001!=?2",nativeQuery = true)
    Integer findByName(String customName,String customId);

}
